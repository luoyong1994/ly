package com.example.ly.flink;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * socket 无界限流
 * 通过监听socket字节流实现无界流程
 */
public class wordCount_SocketUnboundStrem {

    public static void main(String[] args) throws Exception {
        //如果算子不设置并行度，那么将采用机器的总线程数，作为默认的并行度
        //opreaterchain 算子链的禁用，可以用于调试算子的时，单独观察该算子
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        executionEnvironment.setRuntimeMode(RuntimeExecutionMode.STREAMING);
        DataStreamSource<String> stringDataStreamSource = executionEnvironment.socketTextStream("192.168.248.127", 7777);
        stringDataStreamSource.flatMap((String value, Collector<Tuple2<String, Integer>> out) -> {
            String[] split = value.split(" ");
            for (String s : split) {
                out.collect(Tuple2.of(s, 1));
            }
        }).setParallelism(2).returns(Types.TUPLE(Types.STRING, Types.INT)).keyBy((Tuple2<String, Integer> value) -> {
            return value.f0;
        }).sum(1).print();
        executionEnvironment.execute();

    }
}
