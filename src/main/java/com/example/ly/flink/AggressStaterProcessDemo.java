package com.example.ly.flink;

import com.example.ly.flink.bean.SensorLevelBean;
import com.mysql.cj.xdevapi.Type;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.state.AggregatingState;
import org.apache.flink.api.common.state.AggregatingStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.windowing.AggregateApplyWindowFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class AggressStaterProcessDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        //这里如果不设置并行度，就会以为CPU核数开始并行度，触发窗口的watermark,回去最小值为触发窗口，所以窗口要注意
        executionEnvironment.setParallelism(1);
        DataStreamSource<String> stringDataStreamSource = executionEnvironment.socketTextStream("192.168.248.127", 7777);

        stringDataStreamSource.flatMap(new FlatMapFunction<String, SensorLevelBean>() {
            @Override
            public void flatMap(String value, Collector<SensorLevelBean> out) throws Exception {
                String[] split = value.split(",");
                out.collect(new SensorLevelBean(split[0], Integer.valueOf(split[1])));
            }
        }).assignTimestampsAndWatermarks(WatermarkStrategy.<SensorLevelBean>forMonotonousTimestamps().withTimestampAssigner(new SerializableTimestampAssigner<SensorLevelBean>() {
            //该窗口并没生效
            @Override
            public long extractTimestamp(SensorLevelBean element, long recordTimestamp) {
                //周期性的发送出水位线
                //设置水位线
                //每2ms发出水位线
                return (element.getLevel() * 1000L);
            }
        })).keyBy(value -> value.getName()).process(new KeyedProcessFunction<String, SensorLevelBean, String>() {

            //值聚合，并按照key进了区分
            AggregatingState<Integer, Double> aggregatingState;

            @Override
            public void open(Configuration parameters) throws Exception {
                super.open(parameters);
                aggregatingState = getRuntimeContext().getAggregatingState(new AggregatingStateDescriptor<Integer, Tuple2<Integer, Integer>, Double>("", new AggregateFunction<Integer, Tuple2<Integer, Integer>, Double>() {
                    @Override
                    public Tuple2<Integer, Integer> createAccumulator() {
                        return Tuple2.of(0, 0);
                    }

                    @Override
                    public Tuple2<Integer, Integer> add(Integer value, Tuple2<Integer, Integer> accumulator) {

                        return Tuple2.of(value + accumulator.f0, accumulator.f1 + 1);
                    }

                    @Override
                    public Double getResult(Tuple2<Integer, Integer> accumulator) {
                        return Double.valueOf(accumulator.f0) / Double.valueOf(accumulator.f1);
                    }

                    @Override
                    public Tuple2<Integer, Integer> merge(Tuple2<Integer, Integer> a, Tuple2<Integer, Integer> b) {
                        return null;
                    }
                }, Types.TUPLE(Types.INT, Types.INT)));
            }

            @Override
            public void processElement(SensorLevelBean value, KeyedProcessFunction<String, SensorLevelBean, String>.Context ctx, Collector<String> out) throws Exception {
                Integer level = value.getLevel();
                aggregatingState.add(level);
                Double v = aggregatingState.get();
                out.collect(value.getName() + ":平均值=" + v);
            }
        }).print();

        executionEnvironment.execute();

    }
}
