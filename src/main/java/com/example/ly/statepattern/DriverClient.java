package com.example.ly.statepattern;

public class DriverClient {

    public static void main(String[] args) {
        TelaCar telaCar = new TelaCar();
        telaCar.running();
        telaCar.recharge();
        telaCar.running();
        telaCar.stop();
    }
}
