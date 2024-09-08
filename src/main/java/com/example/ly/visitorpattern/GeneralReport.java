package com.example.ly.visitorpattern;

public class GeneralReport implements Visitor {

    private int CustomerNum = 0;

    private int orderNum = 0;

    private int itemNum = 0;


    @Override
    public void visit(Customer customer) {
        System.out.println(customer.getName());
        this.CustomerNum++;
    }

    @Override
    public void visit(Order order) {
        System.out.println(order.getName());
        this.orderNum++;
    }

    @Override
    public void visit(Item item) {
        System.out.println(item.getName());
        this.itemNum++;
    }
}
