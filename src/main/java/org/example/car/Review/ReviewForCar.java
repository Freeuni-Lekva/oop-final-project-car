package org.example.car.Review;

import org.example.car.Car.Model.Car;

public class ReviewForCar {
    private Review review;
    private String userName;

    public ReviewForCar(Review review, String userName) {
        this.review = review;
        this.userName = userName;
    }

    public Review getReview() {
        return review;
    }

    public String getUserName() {
        return userName;
    }
}
