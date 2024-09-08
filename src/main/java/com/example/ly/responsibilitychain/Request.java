package com.example.ly.responsibilitychain;

public class Request {

    private String name;

    private RequestEnum type;

    public Request(String name, RequestEnum type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public RequestEnum getType() {
        return type;
    }

}
