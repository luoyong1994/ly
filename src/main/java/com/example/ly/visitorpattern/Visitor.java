package com.example.ly.visitorpattern;

public interface Visitor {

    void visit(Customer customer);

    void visit(Order order);

    void visit(Item item);

}
