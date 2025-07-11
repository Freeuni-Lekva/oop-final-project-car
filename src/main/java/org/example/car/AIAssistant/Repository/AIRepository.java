package org.example.car.AIAssistant.Repository;

import org.example.car.BookingSystem.Booking;
import org.example.car.BookingSystem.Repository.BookingRepository;
import org.example.car.Car.Model.Car;
import org.example.car.Car.Repository.CarRepository;
import org.example.car.Review.Repository.ReviewRepository;
import org.example.car.Review.Review;

import java.util.List;

public class AIRepository {
    public StringBuilder getCarsAsMessage(){
        StringBuilder sb = new StringBuilder();

        List<Car> cars = CarRepository.getAllCars();

        sb.append("\n=== Cars ===\n");
        for(Car car: cars) {
            String s = String.format(" Brand - %s; Model - %s; Year of release - %d; Price per day - %.2f; Description - %s", car.getBrand(), car.getModel(), car.getYear(), car.getPrice_per_day(), car.getDescription());
            sb.append(s).append("\n");
        }

        return sb;
    }

    public StringBuilder getBookingsAsMessage() {
        StringBuilder sb = new StringBuilder();

        List<Booking> bookings = BookingRepository.getBookings();
        sb.append("\n=== Bookings ===\n");
        for (Booking b : bookings) {
            String s = String.format("Car with ID: %d is booked from %s to %s", b.getCarId(), b.getStartDate(), b.getEndDate());
            sb.append(s).append("\n");
        }

        return sb;
    }

    public StringBuilder getReviewsAsMessage() {
        StringBuilder sb = new StringBuilder();

        List<Review> reviews = ReviewRepository.getReviews();
        sb.append("\n=== Reviews ===\n");
        for(Review r: reviews){
            String s = String.format("Car with ID: %d is rated %d/5", r.getCarId(), r.getRating());
            sb.append(s).append("\n");
        }

        return sb;
    }

}
