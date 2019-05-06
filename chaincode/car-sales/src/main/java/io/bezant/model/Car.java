package io.bezant.model;

public class Car {
    private String id;
    private String brand;
    private String model;
    private String owner;

    public Car(String id, String brand, String model, String owner) {
        this.id = id;
        this.brand= brand;
        this.model = model;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}