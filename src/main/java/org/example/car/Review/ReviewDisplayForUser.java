package org.example.car.Review;

import org.example.car.Car.Model.Car;

public class ReviewDisplayForUser {
    private Review review;
    private Car car;

    public ReviewDisplayForUser(Review review, Car car) {
        this.review = review;
        this.car = car;
    }

    public Review getReview() {
        return review;
    }

    public Car getCar() {
        return car;
    }
}
