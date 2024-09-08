package com.example.ly.statepattern;

public class TelaStopSate implements TelaState {

    private TelaCar telaCar;

    public TelaStopSate(TelaCar telaCar) {
        this.telaCar = telaCar;
    }

    @Override
    public void stop() {
        System.out.println("you have stop state");
    }

    @Override
    public void running() {
        System.out.println("check energy....,we can go");
        System.out.println("chanage running");
        telaCar.setState(telaCar.getRunning());
    }

    @Override
    public void recharge() {
        System.out.println("change recharge");
        telaCar.setState(telaCar.getCharge());
    }
}
