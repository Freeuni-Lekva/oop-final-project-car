package org.example.tests;

import org.example.car.BookingSystem.Booking;
import org.example.car.BookingSystem.BookingRequest;
import org.example.car.BookingSystem.Repository.BookingRepository;
import org.example.car.DBConnector;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingRepositoryTest {
    @BeforeAll
    static void setupDatabase() throws SQLException {
        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS bookings (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT,
                    car_id INT,
                    start_date DATE,
                    end_date DATE
                );
            """);
        }
    }

    @AfterEach
    void clearBookings() throws SQLException {
        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM bookings");
        }
    }

    @Test
    void testInsertBookingAndRetrieve() {
        BookingRequest request = new BookingRequest(1, 1, Date.valueOf("2025-07-01"), Date.valueOf("2025-07-05"));
        assertTrue(BookingRepository.insertBooking(request));

        List<Booking> bookings = BookingRepository.getBookings();
        assertEquals(1, bookings.size());
        assertEquals(1, bookings.get(0).getCarId());
    }

    @Test
    void testIsCarAvailable() {
        BookingRequest first = new BookingRequest(1, 2, Date.valueOf("2025-07-10"), Date.valueOf("2025-07-15"));
        BookingRepository.insertBooking(first);

        BookingRequest overlapping = new BookingRequest(2, 2, Date.valueOf("2025-07-14"), Date.valueOf("2025-07-20"));
        BookingRequest nonOverlapping = new BookingRequest(3, 2, Date.valueOf("2025-07-16"), Date.valueOf("2025-07-18"));

        assertFalse(new BookingRepository().isCarAvaliable(overlapping));
        assertTrue(new BookingRepository().isCarAvaliable(nonOverlapping));
    }

    @Test
    void testGetCarBookings() {
        BookingRequest req = new BookingRequest(1, 5, Date.valueOf("2025-08-01"), Date.valueOf("2025-08-03"));
        BookingRepository.insertBooking(req);

        List<Booking> bookings = BookingRepository.getCarBookings(5);
        assertEquals(1, bookings.size());
    }

    @Test
    void testGetBookingsByUserId() {
        BookingRepository.insertBooking(new BookingRequest(10, 4, Date.valueOf("2025-09-01"), Date.valueOf("2025-09-05")));
        BookingRepository.insertBooking(new BookingRequest(10, 5, Date.valueOf("2025-09-06"), Date.valueOf("2025-09-10")));

        List<Booking> userBookings = BookingRepository.getBookingsByUserId(10);
        assertEquals(2, userBookings.size());
    }

    @Test
    void testCategorizeBookings() {
        LocalDate today = LocalDate.now();

        BookingRepository.insertBooking(new BookingRequest(20, 1,
                Date.valueOf(today.minusDays(5)), Date.valueOf(today.minusDays(1)))); // past
        BookingRepository.insertBooking(new BookingRequest(20, 2,
                Date.valueOf(today.minusDays(1)), Date.valueOf(today.plusDays(1)))); // current
        BookingRepository.insertBooking(new BookingRequest(20, 3,
                Date.valueOf(today.plusDays(1)), Date.valueOf(today.plusDays(3)))); // future

        Map<String, List<Booking>> map = BookingRepository.categorizeBookings(20);
        assertEquals(1, map.get("past").size());
        assertEquals(1, map.get("current").size());
        assertEquals(1, map.get("future").size());
    }

    @Test
    void testDeleteBooking() {
        BookingRequest request = new BookingRequest(1, 6, Date.valueOf("2025-10-01"), Date.valueOf("2025-10-03"));
        BookingRepository.insertBooking(request);
        int id = BookingRepository.getBookings().get(0).getId();

        BookingRepository.deleteBooking(id);
        assertTrue(BookingRepository.getBookings().isEmpty());
    }

    @Test
    void testGetBookingById() {
        BookingRepository.insertBooking(new BookingRequest(5, 5, Date.valueOf("2025-11-01"), Date.valueOf("2025-11-04")));
        int id = BookingRepository.getBookings().get(0).getId();

        Booking booking = BookingRepository.getBookingById(id);
        assertNotNull(booking);
        assertEquals(5, booking.getUserId());
    }
}
