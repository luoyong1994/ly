package com.example.ly.statepattern;

public class TelaRechargeState implements TelaState {

    TelaCar telaCar;

    public TelaRechargeState(TelaCar telaCar) {
        this.telaCar = telaCar;
    }

    @Override
    public void stop() {
        System.out.println("you are stopped and doing rechage");
    }

    @Override
    public void running() {
        System.out.println("over recharge and running tela care");
        telaCar.setState(telaCar.getRunning());
    }

    @Override
    public void recharge() {
        System.out.println("you are recharging now");
    }
}
