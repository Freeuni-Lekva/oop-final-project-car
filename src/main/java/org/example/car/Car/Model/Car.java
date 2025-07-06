package org.example.car.Car.Model;

public class Car {
    private int id;
    private String brand;
    private String model;
    private int year;
    private double price_per_day;
    private String description;
    private String image_url;

    public Car(int id, String brand, String model, int year, double price_per_day, String description, String image_url) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price_per_day = price_per_day;
        this.description = description;
        this.image_url = image_url;
    }

    public Car() {
        this.id = 0;
        this.brand = null;
        this.model = null;
        this.year = 0;
        this.price_per_day = 0;
        this.description = null;
        this.image_url = null;
    }

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

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price_per_day=" + price_per_day +
                ", description='" + description + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}

