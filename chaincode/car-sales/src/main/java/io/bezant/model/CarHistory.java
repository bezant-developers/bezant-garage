package io.bezant.model;

public class CarHistory {
    private String txId;
    private Car car;

    public CarHistory(String txId, Car car) {
        this.txId = txId;
        this.car = car;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car user) {
        this.car = user;
    }

}