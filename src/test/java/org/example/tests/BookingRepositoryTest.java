package org.example.tests;

import org.example.car.BookingSystem.Booking;
import org.example.car.BookingSystem.BookingRequest;
import org.example.car.BookingSystem.Repository.BookingRepository;
import org.example.car.DBConnector;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingRepositoryTest {

    private static int testUserId;
    private static int testCarId;
    private static int insertedBookingId;

    @BeforeAll
    static void setup() throws SQLException {
        try (Connection conn = DBConnector.getConnection()) {
            PreparedStatement userStmt = conn.prepareStatement(
                    "INSERT INTO users (full_name, password_hash, is_admin) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            userStmt.setString(1, "Test User");
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
            carStmt.setString(1, "TestBrand");
            carStmt.setString(2, "TestModel");
            carStmt.setInt(3, 2024);
            carStmt.setBigDecimal(4, new java.math.BigDecimal("99.99"));
            carStmt.setString(5, "Test description");
            carStmt.setString(6, "test.jpg");
            carStmt.executeUpdate();
            ResultSet carKeys = carStmt.getGeneratedKeys();
            if (carKeys.next()) testCarId = carKeys.getInt(1);
        }
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
    void testInsertBooking() {
        BookingRequest request = new BookingRequest(
            testUserId,
            testCarId,
            java.sql.Date.valueOf("2025-08-01"),
            java.sql.Date.valueOf("2025-08-05")
        );
        boolean success = BookingRepository.insertBooking(request);
        assertTrue(success);
    }

    @Test
    @Order(2)
    void testGetCarBookings() {
        List<Booking> bookings = BookingRepository.getCarBookings(testCarId);
        assertFalse(bookings.isEmpty());
        insertedBookingId = bookings.get(0).getId();
    }

    @Test
    @Order(3)
    void testGetBookingById() {
        Booking booking = BookingRepository.getBookingById(insertedBookingId);
        assertNotNull(booking);
        assertEquals(testUserId, booking.getUserId());
        assertEquals(testCarId, booking.getCarId());
    }

    @Test
    @Order(4)
    void testDeleteBooking() {
        BookingRepository.deleteBooking(insertedBookingId);
        Booking deleted = BookingRepository.getBookingById(insertedBookingId);
        assertNull(deleted);
    }
}
