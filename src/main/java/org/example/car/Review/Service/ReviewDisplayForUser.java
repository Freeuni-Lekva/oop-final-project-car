package org.example.car.Review.Service;

import org.example.car.BookingSystem.Booking;
import org.example.car.Car.Model.Car;
import org.example.car.Review.Review;

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
