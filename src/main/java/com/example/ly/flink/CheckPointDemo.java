package com.example.ly.flink;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * flink容错机制
 * checkpoint
 */
public class CheckPointDemo {


    public static void main(String[] args) {
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        //这里如果不设置并行度，就会以为CPU核数开始并行度，触发窗口的watermark,回去最小值为触发窗口，所以窗口要注意
        executionEnvironment.setParallelism(1);
        DataStreamSource<String> stringDataStreamSource = executionEnvironment.socketTextStream("192.168.248.127", 7777);



    }
}
