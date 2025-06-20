package org.example.car.User.Model;

public class Review {
    private int id;
    private int userId;
    private int carId;
    private int rating;
    private String comment;

    public Review() {
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getCarId() {
        return carId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}