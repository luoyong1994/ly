package com.example.ly.flink.Fraud.entity;

/**
 * @description: 预警类
 * @author: ly
 * @create: 2024-07-04 16:56
 **/
public class Alert {

    private String accountId;

    private String desc;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
