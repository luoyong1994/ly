package com.example.ly.visitorpattern;

import java.util.ArrayList;
import java.util.List;

public class Order implements Element {

    private String name;

    private List<Item> items = new ArrayList<>();

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
