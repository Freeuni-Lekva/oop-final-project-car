package org.example.car.BookingSystem;

import org.example.car.Car.Model.Car;

public class BookingDisplay {
    private Booking booking;
    private Car car;

    public BookingDisplay(Booking booking, Car car) {
        this.booking = booking;
        this.car = car;
    }

    public Booking getBooking() {
        return booking;
    }

    public Car getCar() {
        return car;
    }
}
