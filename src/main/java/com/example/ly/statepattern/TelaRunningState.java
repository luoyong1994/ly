package com.example.ly.statepattern;

public class TelaRunningState implements TelaState {

    TelaCar telaCar;

    public TelaRunningState(TelaCar telaCar) {
        this.telaCar = telaCar;
    }

    @Override
    public void stop() {
        System.out.println("brake car... change to stop state");
        telaCar.setState(telaCar.getStop());
    }

    @Override
    public void running() {
        System.out.println("you have stop state");
    }

    @Override
    public void recharge() {
        System.out.println("you can not change to recharge when you are in running state,now stop and then chage");
        telaCar.setState(telaCar.getStop());
        telaCar.setState(telaCar.getCharge());

    }
}
