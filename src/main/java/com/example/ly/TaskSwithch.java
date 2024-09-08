package com.example.ly;

public class TaskSwithch {

    private volatile boolean state = true;


    public boolean getState() {
        return state;
    }

    public void open() {
        this.state = true;
    }


    public void close() {
        this.state = false;
    }

}
