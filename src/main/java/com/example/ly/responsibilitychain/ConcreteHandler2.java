package com.example.ly.responsibilitychain;

public class ConcreteHandler2 extends Handler {

    public ConcreteHandler2(Handler successor) {
        super(successor);
    }

    @Override
    public void handleRequest(Request request) {
        if (request.getType() == RequestEnum.TYPE2) {
            System.out.println("deal reuqest:" + request.getName());
        }
        if (successor != null) {
            successor.handleRequest(request);
        }
    }


}
