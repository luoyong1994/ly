package com.example.ly.flink;

import com.example.ly.flink.bean.SensorLevelBean;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.util.Optional;

public class WindowApi_Demo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> stringDataStreamSource = executionEnvironment.socketTextStream("192.168.248.127", 7777);
        stringDataStreamSource.flatMap(new FlatMapFunction<String, SensorLevelBean>() {
            @Override
            public void flatMap(String value, Collector<SensorLevelBean> out) throws Exception {
                String[] split = value.split(",");
                Optional<String> s = Optional.of(split[1]);
                s.ifPresent(val -> {

                });
                String s1 = s.orElse("0");
                SensorLevelBean sensorLevelBean = new SensorLevelBean(split[0], Integer.parseInt(split[1]));
                out.collect(sensorLevelBean);
            }
        }).keyBy(new KeySelector<SensorLevelBean, String>() {

            @Override
            public String getKey(SensorLevelBean value) throws Exception {
                return value.getName();
            }
        }).window(TumblingProcessingTimeWindows.of(Time.seconds(5))).reduce(new ReduceFunction<SensorLevelBean>() {
            //5秒中的窗口，划分为一个窗口
            @Override
            public SensorLevelBean reduce(SensorLevelBean value1, SensorLevelBean value2) throws Exception {
                System.out.println("value1:" + value1 + ",value2:" + value2);
                return new SensorLevelBean(value2.getName(), (value1.getLevel() + value2.getLevel()));
            }
        }).print();

        //一定要执行，才会开启算子
        executionEnvironment.execute();

    }
}
