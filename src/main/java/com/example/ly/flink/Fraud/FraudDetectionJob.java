package com.example.ly.flink.Fraud;

import com.example.ly.flink.Fraud.entity.Transaction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

/**
 * @description: 欺诈job类
 * @author: ly
 * @create: 2024-07-04 22:12
 **/
public class FraudDetectionJob {

    public static void main(String[] args) {
        FraudDetector fraudDetector = new FraudDetector();
        String ip = "192.168.248.127";
        int port = 9999;
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> transactionDataStreamSource = executionEnvironment.socketTextStream(ip, port);
        transactionDataStreamSource.flatMap((String value, Collector<Transaction> collection) -> {
            String[] split = value.split(",");
            String accountId = split[0];
            String money = split[1];
            Transaction transaction = new Transaction();
            transaction.setMoney(Long.valueOf(money));
            transaction.setAccountId(accountId);
            collection.collect(transaction);
        }).keyBy(new KeySelector<Transaction, String>() {

            @Override
            public String getKey(Transaction transaction) throws Exception {
                return transaction.getAccountId();
            }
        });

    }
}
