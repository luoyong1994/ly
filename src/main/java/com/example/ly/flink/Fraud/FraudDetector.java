package com.example.ly.flink.Fraud;

import com.example.ly.flink.Fraud.entity.Alert;
import com.example.ly.flink.Fraud.entity.Transaction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

import java.math.BigDecimal;

/**
 * @description: 基于flink的欺诈检测类
 * @author: ly
 * @create: 2024-07-04 16:52
 **/
public class FraudDetector extends KeyedProcessFunction<Long, Transaction, Alert> {


    private static final long serialVersionUID = 1L;

    private static final long MINSTARAND = 1l;
    private static final long MAXSTARAND = 500l;

    private transient ValueState<Boolean> flagState;


    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        //ValueStateDescriptor 包含了 Flink 如何管理变量的一些元数据信息
        ValueStateDescriptor<Boolean> flag = new ValueStateDescriptor<>("flag", Types.BOOLEAN);
        //注册状态信息,需要使用open方法
        getRuntimeContext().getState(flag);
    }

    @Override
    public void processElement(Transaction transaction, KeyedProcessFunction<Long, Transaction, Alert>.Context context, Collector<Alert> collector) throws Exception {
        String accountId = transaction.getAccountId();
        long money = transaction.getMoney();
        if (flagState.value() != null) {
            if (transaction.getMoney() > MAXSTARAND) {
                Alert alert = new Alert();
                alert.setAccountId(transaction.getAccountId());
                alert.setDesc("存在欺诈嫌疑，请注意");
                collector.collect(alert);
            }
        }
        if (money < MINSTARAND) {
            flagState.update(true);
        }
    }
}
