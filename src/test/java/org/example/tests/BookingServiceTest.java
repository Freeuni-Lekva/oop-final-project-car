package org.example.tests;

import org.example.car.BookingSystem.Booking;
import org.example.car.BookingSystem.BookingDisplay;
import org.example.car.BookingSystem.BookingRequest;
import org.example.car.BookingSystem.Service.BookingService;
import org.example.car.Car.Model.Car;
import org.example.car.Car.Repository.CarRepository;
import org.example.car.DBConnector;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingServiceTest {
    @BeforeAll
    void setupDatabase() throws SQLException {
        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS cars (
                    id INT PRIMARY KEY,
                    brand VARCHAR(255),
                    model VARCHAR(255),
                    year INT,
                    price_per_day DOUBLE,
                    description VARCHAR(255),
                    image_url VARCHAR(255)
                );
            """);
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS bookings (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT NOT NULL,
                    car_id INT NOT NULL,
                    start_date DATE NOT NULL,
                    end_date DATE NOT NULL
                );
            """);
        }
    }

    @AfterEach
    void clearTables() throws SQLException {
        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM bookings");
            stmt.execute("DELETE FROM cars");
        }
    }

    void insertTestCar(int id) throws SQLException {
        Car car = new Car(id, "TestBrand", "TestModel", 2020, 50.0, "desc", "img.png");
        CarRepository.addCar(car);
    }

    @Test
    void testBookCar_Success() throws SQLException {
        insertTestCar(201);
        BookingService service = new BookingService();
        BookingRequest req = new BookingRequest(10, 201, Date.valueOf("2024-09-01"), Date.valueOf("2024-09-05"));
        assertTrue(service.bookCar(req));
    }

    @Test
    void testBookCar_FailIfUnavailable() throws SQLException {
        insertTestCar(202);
        BookingService service = new BookingService();
        BookingRequest req1 = new BookingRequest(11, 202, Date.valueOf("2024-09-10"), Date.valueOf("2024-09-15"));
        BookingRequest req2 = new BookingRequest(12, 202, Date.valueOf("2024-09-12"), Date.valueOf("2024-09-18"));
        assertTrue(service.bookCar(req1));
        assertFalse(service.bookCar(req2));
    }

    @Test
    void testCategorizeBookings() throws SQLException {
        insertTestCar(203);
        BookingService service = new BookingService();
        BookingRequest past = new BookingRequest(13, 203, Date.valueOf("2023-01-01"), Date.valueOf("2023-01-05"));
        BookingRequest future = new BookingRequest(13, 203, Date.valueOf("2099-01-01"), Date.valueOf("2099-01-05"));
        service.bookCar(past);
        service.bookCar(future);
        Map<String, List<BookingDisplay>> map = BookingService.categorizeBookings(13);
        assertEquals(1, map.get("past").size());
        assertEquals(1, map.get("future").size());
    }

    @Test
    void testDeleteBooking() throws SQLException {
        insertTestCar(204);
        BookingService service = new BookingService();
        BookingRequest req = new BookingRequest(14, 204, Date.valueOf("2024-10-01"), Date.valueOf("2024-10-03"));
        service.bookCar(req);
        Booking b = BookingService.getBookingById(BookingService.getBookingById(1).getId());
        BookingService.deleteBooking(b.getId());
        assertNull(BookingService.getBookingById(b.getId()));
    }

    @Test
    void testGetBookingById() throws SQLException {
        insertTestCar(205);
        BookingService service = new BookingService();
        BookingRequest req = new BookingRequest(15, 205, Date.valueOf("2024-11-01"), Date.valueOf("2024-11-05"));
        service.bookCar(req);
        Booking b = BookingService.getBookingById(1);
        assertNotNull(b);
        assertEquals(15, b.getUserId());
        assertEquals(205, b.getCarId());
    }
} 