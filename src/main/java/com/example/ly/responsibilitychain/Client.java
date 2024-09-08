package com.example.ly.responsibilitychain;

public class Client {

    public static void main(String[] args) {
        ConcreteHandler1 concreteHandler1 = new ConcreteHandler1(null);
        ConcreteHandler2 concreteHandler2 = new ConcreteHandler2(concreteHandler1);

        //来一个请求request1
        Request request1 = new Request("request1", RequestEnum.TYPE1);
        //处理请求1
//        concreteHandler2.handleRequest(request1);

        //来一个请求request2
        //处理请求2

        Request request2 = new Request("request2", RequestEnum.TYPE2);
//        concreteHandler2.handleRequest(request2);


        //来一个请求request3
        //处理请求3

        Request request3 = new Request("request3", RequestEnum.TYPE3);
        concreteHandler2.handleRequest(request3);


    }
}
