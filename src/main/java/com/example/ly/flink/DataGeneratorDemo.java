package com.example.ly.flink;

import com.example.ly.flink.bean.SensorLevelBean;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.connector.source.util.ratelimit.RateLimiter;
import org.apache.flink.api.connector.source.util.ratelimit.RateLimiterStrategy;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.datagen.DataGenerator;
import org.apache.flink.streaming.api.functions.source.datagen.DataGeneratorSource;
import org.apache.flink.streaming.api.functions.source.datagen.RandomGenerator;
import org.apache.flink.util.Collector;

import java.util.Random;

public class DataGeneratorDemo {


    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();

        DataGeneratorSource<Long> longDataGeneratorSource = new DataGeneratorSource<>(RandomGenerator.longGenerator(1L, 1000L), 100, null);

        DataStreamSource<Long> longDataStreamSource = executionEnvironment.addSource(longDataGeneratorSource);

        longDataStreamSource.returns(Types.LONG).flatMap(new RichFlatMapFunction<Long, SensorLevelBean>() {

            @Override
            public void flatMap(Long value, Collector<SensorLevelBean> out) throws Exception {
                RuntimeContext runtimeContext = getRuntimeContext();
                //并行度编号
                int indexOfThisSubtask = runtimeContext.getIndexOfThisSubtask();
                SensorLevelBean sensorLevelBean = new SensorLevelBean("sensor" + indexOfThisSubtask, value.intValue());
                out.collect(sensorLevelBean);
            }

        }).keyBy(new KeySelector<SensorLevelBean, String>() {
            @Override
            public String getKey(SensorLevelBean value) throws Exception {
                return value.getName();
            }
        }).print();
        executionEnvironment.execute();
    }
}
