package com.example.ly.statepattern;


/**
 * 状态模式是一种非常好的模式，对于多行为状态的对象，具有极强的分类，思考能力
 *
 * 体会到了之后，真感觉到其是那么的巧妙
 *
 */
public class TelaCar {

    //静至状态，默认的状态
    private TelaState stop;
    //开启状态
    private TelaState running;
    //充电状态
    private TelaState charge;

    private TelaState state;

    public TelaCar() {
        //创建一个无状态的特斯拉
        stop = new TelaStopSate(this);
        running = new TelaRunningState(this);
        charge = new TelaRechargeState(this);
        state = this.stop;
    }


    public void running() {
        TelaState current = getState();
        current.running();
    }

    public void stop() {
        TelaState current = getState();
        current.stop();
    }

    public void recharge() {
        TelaState current = getState();
        current.recharge();
    }


    public TelaState getStop() {
        return stop;
    }

    public void setStop(TelaState stop) {
        this.stop = stop;
    }

    public TelaState getRunning() {
        return running;
    }

    public void setRunning(TelaState running) {
        this.running = running;
    }

    public TelaState getCharge() {
        return charge;
    }

    public void setCharge(TelaState charge) {
        this.charge = charge;
    }

    public TelaState getState() {
        return state;
    }

    public void setState(TelaState state) {
        this.state = state;
    }
}
