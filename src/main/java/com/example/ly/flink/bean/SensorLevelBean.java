package com.example.ly.flink.bean;

import java.util.Date;

public class SensorLevelBean {

    private String name;
    private Integer level;

    private Date date = new Date();

    public SensorLevelBean(String name, Integer level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SensorLevelBean{" +
                "name='" + name + '\'' +
                ", level=" + level +
                '}';
    }
}
