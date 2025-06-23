package org.example.car.Car.Model;

public class Car {
    private int id;
    private String brand;
    private String model;
    private int year;
    private double price_per_day;
    private String description;
    private String image_url;

    public int getId() {
        return id;
    }
    public void setId(int id) {
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
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public double getPrice_per_day() {
        return price_per_day;
    }
    public void setPrice_per_day(double price_per_day) {
        this.price_per_day = price_per_day;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage_url() {
        return image_url;
    }
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}

