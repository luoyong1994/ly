package com.example.ly.flink.Fraud.entity;

import java.math.BigDecimal;

/**
 * @description: 交易实体类
 * @author: ly
 * @create: 2024-07-04 17:01
 **/
public class Transaction {


    private String accountId;

    private long money;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }
}
