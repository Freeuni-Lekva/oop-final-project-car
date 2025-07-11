package org.example.tests;

import org.example.car.BookingSystem.Booking;
import org.example.car.BookingSystem.BookingDisplay;
import org.example.car.BookingSystem.BookingRequest;
import org.example.car.BookingSystem.Service.BookingService;
import org.example.car.DBConnector;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingServiceTest {

    private static int testUserId;
    private static int testCarId;
    private static int insertedBookingId;

    private static BookingService bookingService;

    @BeforeAll
    static void setup() throws SQLException {
        try (Connection conn = DBConnector.getConnection()) {
            PreparedStatement userStmt = conn.prepareStatement(
                "INSERT INTO users (full_name, password_hash, is_admin) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            userStmt.setString(1, "ServiceTestUser");
            userStmt.setString(2, "test123");
            userStmt.setBoolean(3, false);
            userStmt.executeUpdate();
            ResultSet userKeys = userStmt.getGeneratedKeys();
            if (userKeys.next()) testUserId = userKeys.getInt(1);

            PreparedStatement carStmt = conn.prepareStatement(
                "INSERT INTO cars (brand, model, year, price_per_day, description, image_url) " +
                "VALUES (?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            carStmt.setString(1, "BrandS");
            carStmt.setString(2, "ModelS");
            carStmt.setInt(3, 2024);
            carStmt.setBigDecimal(4, new java.math.BigDecimal("49.99"));
            carStmt.setString(5, "Service test car");
            carStmt.setString(6, "image.jpg");
            carStmt.executeUpdate();
            ResultSet carKeys = carStmt.getGeneratedKeys();
            if (carKeys.next()) testCarId = carKeys.getInt(1);
        }

        bookingService = new BookingService();
    }

    @AfterAll
    static void cleanup() throws SQLException {
        try (Connection conn = DBConnector.getConnection()) {
            conn.prepareStatement("DELETE FROM bookings WHERE user_id = " + testUserId).executeUpdate();
            conn.prepareStatement("DELETE FROM users WHERE id = " + testUserId).executeUpdate();
            conn.prepareStatement("DELETE FROM cars WHERE id = " + testCarId).executeUpdate();
        }
    }

    @Test
    @Order(1)
    void testBookCarSuccess() {
        BookingRequest request = new BookingRequest(
            testUserId,
            testCarId,
            java.sql.Date.valueOf("2025-08-01"),
            java.sql.Date.valueOf("2025-08-05")
        );
        boolean booked = bookingService.bookCar(request);
        assertTrue(booked);
    }

    @Test
    @Order(2)
    void testCategorizeBookings() throws SQLException {
        Map<String, List<BookingDisplay>> map = BookingService.categorizeBookings(testUserId);
        assertNotNull(map.get("future"));
        assertFalse(map.get("future").isEmpty());

        BookingDisplay bd = map.get("future").get(0);
        assertEquals(testCarId, bd.getCar().getId());
        insertedBookingId = bd.getBooking().getId();
    }

    @Test
    @Order(3)
    void testGetBookingById() {
        Booking booking = BookingService.getBookingById(insertedBookingId);
        assertNotNull(booking);
        assertEquals(testUserId, booking.getUserId());
    }

    @Test
    @Order(4)
    void testDeleteBooking() {
        BookingService.deleteBooking(insertedBookingId);
        Booking deleted = BookingService.getBookingById(insertedBookingId);
        assertNull(deleted);
    }
}
