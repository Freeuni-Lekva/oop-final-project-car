package org.example.car.Review;

public class Review {
    private int id;
    private int user_id;
    private int car_id;
    private int rating;
    private String comment;

    public Review(int id, int user_id, int car_id, int rating, String text) {
        this.id = id;
        this.user_id = user_id;
        this.car_id = car_id;
        this.rating = rating;
        this.comment = text;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getCarId() {
        return car_id;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

}
