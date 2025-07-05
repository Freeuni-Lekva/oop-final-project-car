package org.example.car;

public class Review {
    private int id;
    private int uder_id;
    private int car_id;
    private int rating;
    private String text;

    public Review(int id, int uder_id, int car_id, int rating, String text) {
        this.id = id;
        this.uder_id = uder_id;
        this.car_id = car_id;
        this.rating = rating;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public int getUder_id() {
        return uder_id;
    }

    public int getCar_id() {
        return car_id;
    }

    public int getRating() {
        return rating;
    }

    public String getText() {
        return text;
    }
}
