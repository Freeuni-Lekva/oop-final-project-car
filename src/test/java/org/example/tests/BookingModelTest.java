package org.example.tests;

import org.example.car.BookingSystem.Booking;
import org.example.car.BookingSystem.BookingDisplay;
import org.example.car.BookingSystem.BookingRequest;
import org.example.car.Car.Model.Car;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class BookingModelTest {
    @Test
    void testBookingConstructorAndGetters() {
        Date start = Date.valueOf("2024-06-01");
        Date end = Date.valueOf("2024-06-10");
        Booking booking = new Booking(1, 2, 3, start, end);
        assertEquals(1, booking.getId());
        assertEquals(2, booking.getUserId());
        assertEquals(3, booking.getCarId());
        assertEquals(start, booking.getStartDate());
        assertEquals(end, booking.getEndDate());
    }

    @Test
    void testBookingDisplayConstructorAndGetters() {
        Booking booking = new Booking(5, 6, 7, Date.valueOf("2024-08-01"), Date.valueOf("2024-08-10"));
        Car car = new Car(10, "TestCar", "Brand", 2020, 100.0, "desc", "img.png");
        BookingDisplay display = new BookingDisplay(booking, car);
        assertEquals(booking, display.getBooking());
        assertEquals(car, display.getCar());
    }

    @Test
    void testBookingRequestConstructorAndGetters() {
        Date start = Date.valueOf("2024-07-01");
        Date end = Date.valueOf("2024-07-05");
        BookingRequest req = new BookingRequest(10, 20, start, end);
        assertEquals(10, req.getUserId());
        assertEquals(20, req.getCarId());
        assertEquals(start, req.getStartDate());
        assertEquals(end, req.getEndDate());
    }
} 