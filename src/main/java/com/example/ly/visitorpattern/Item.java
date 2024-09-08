package com.example.ly.visitorpattern;

/**
 * 清单定义
 */
public class Item implements Element {

    private String name;

    @Override
    public void accept(Visitor visitor) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
