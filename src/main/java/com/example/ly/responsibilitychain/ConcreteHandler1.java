package com.example.ly.responsibilitychain;

public class ConcreteHandler1 extends Handler {

    public ConcreteHandler1(Handler successor) {
        super(successor);
    }

    @Override
    public void handleRequest(Request request) {
        if (request.getType() == RequestEnum.TYPE1) {
            System.out.println("deal request:" + request.getName());
        }
        if (successor != null) {
            successor.handleRequest(request);
        }
    }

}
