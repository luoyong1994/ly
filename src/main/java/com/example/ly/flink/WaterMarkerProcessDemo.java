package com.example.ly.flink;

import com.example.ly.flink.bean.SensorLevelBean;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class WaterMarkerProcessDemo {

    public static void main(String[] args) throws Exception {

        ArrayList<SensorLevelBean> sensorLevelBeans = new ArrayList<>();
        Iterator<SensorLevelBean> iterator = sensorLevelBeans.iterator();


        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        //这里如果不设置并行度，就会以为CPU核数开始并行度，触发窗口的watermark,回去最小值为触发窗口，所以窗口要注意
        executionEnvironment.setParallelism(2);
        DataStreamSource<String> stringDataStreamSource = executionEnvironment.socketTextStream("192.168.248.127", 7777);

        stringDataStreamSource.flatMap(new FlatMapFunction<String, SensorLevelBean>() {
            @Override
            public void flatMap(String value, Collector<SensorLevelBean> out) throws Exception {
                String[] split = value.split(",");
                out.collect(new SensorLevelBean(split[0], Integer.valueOf(split[1])));
            }
        }).assignTimestampsAndWatermarks(WatermarkStrategy.<SensorLevelBean>forMonotonousTimestamps()
                .withTimestampAssigner(new SerializableTimestampAssigner<SensorLevelBean>() {
                    @Override
                    public long extractTimestamp(SensorLevelBean element, long recordTimestamp) {
                        //周期性的发送出水位线
                        //设置水位线
                        //每2ms发出水位线
                        return (element.getLevel() * 1000L);
                    }
                })).keyBy(value -> value.getName()).window(TumblingEventTimeWindows.of(Time.seconds(10))).process(new ProcessWindowFunction<SensorLevelBean, Object, String, TimeWindow>() {
            @Override
            public void process(String s, ProcessWindowFunction<SensorLevelBean, Object, String, TimeWindow>.Context context, Iterable<SensorLevelBean> elements, Collector<Object> out) throws Exception {
                String result = "";
                for (SensorLevelBean element : elements) {
                    result = result + "," + element.getName() + ":" + element.getLevel();
                }
                out.collect(result);
            }
        }).print();

        executionEnvironment.execute();

    }
}
