package com.example.ly.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;


/**
 * flink中dataSet的Api使用
 */
public class WordCount_DataSet {


    public static void main(String[] args) throws Exception {
        ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
        DataSource<String> xDataSource = executionEnvironment.readTextFile("D:\\qixin\\code\\ly\\src\\main\\resources\\wordcount.txt");
        FlatMapOperator<String, Tuple2<String, Integer>> wordAndOne = xDataSource.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
                //Tuple 元组
                String[] split = value.split(" ");
                for (String s : split) {
                    Tuple2<String, Integer> stringIntegerTuple2 = Tuple2.of(s, 1);
                    out.collect(stringIntegerTuple2);
                }
            }
        });
        //将key分组
        UnsortedGrouping<Tuple2<String, Integer>> wordGroup = wordAndOne.groupBy(0);

        //聚合数值 按照字段索引值
        AggregateOperator<Tuple2<String, Integer>> sum = wordGroup.sum(1);
        sum.print();

    }
}
