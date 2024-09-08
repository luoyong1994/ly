package com.example.ly.visitorpattern;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Customer implements Element {



    private String name;

    private List<Order> orders = new ArrayList<>();

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
