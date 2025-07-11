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
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.example.car.BookingSystem.Repository.BookingRepository;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingServiceTest {
    BookingService bookingService;

    @BeforeEach
     void setUp() throws SQLException {
        try (Connection c = DBConnector.getConnection();
             Statement s = c.createStatement()) {
            s.execute("""
                CREATE TABLE IF NOT EXISTS cars (
                    id INT PRIMARY KEY,
                    brand VARCHAR(255),
                    model VARCHAR(255),
                    "year" INT,
                    price_per_day DECIMAL(10,2),
                    description TEXT,
                    image_url VARCHAR(255)
                );
            """);

            s.execute("""
                CREATE TABLE IF NOT EXISTS bookings (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT,
                    car_id INT,
                    start_date DATE,
                    end_date DATE
                );
            """);
        }
        bookingService = new BookingService();
    }

    @AfterEach
    void cleanup() throws SQLException {
        try (Connection c = DBConnector.getConnection();
             Statement s = c.createStatement()) {
            s.execute("DELETE FROM bookings");
            s.execute("DELETE FROM cars");
        }
    }


    @Test
    void testBookCarSuccess() throws SQLException {
        CarRepository.addCar(new Car(1, "Ford", "Fiesta", 2020, 35.0, "Eco", "f.jpg"));

        BookingRequest req = new BookingRequest(1, 1,
                Date.valueOf("2025-06-01"), Date.valueOf("2025-06-03"));

        assertTrue(bookingService.bookCar(req));

        List<Booking> userBookings = BookingRepository.getBookingsByUserId(1);
        assertFalse(userBookings.isEmpty());
        assertEquals(1, userBookings.get(0).getCarId());
    }

    @Test
    void testBookCarFailsWhenUnavailable() throws SQLException {
        CarRepository.addCar(new Car(2, "Audi", "A3", 2021, 80.0, "Hatch", "a.jpg"));

        BookingRequest initial = new BookingRequest(5, 2,
                Date.valueOf("2025-07-10"), Date.valueOf("2025-07-15"));
        bookingService.bookCar(initial);

        BookingRequest overlap = new BookingRequest(6, 2,
                Date.valueOf("2025-07-14"), Date.valueOf("2025-07-16"));
        assertFalse(bookingService.bookCar(overlap));

        List<Booking> carBookings = BookingRepository.getCarBookings(2);
        assertEquals(1, carBookings.size());
        assertEquals(2, carBookings.get(0).getCarId());
    }



    @Test
    void testCategorizeBookingsAddsCarInfo() throws SQLException {
        CarRepository.addCar(new Car(3, "Tesla", "Model Y", 2023, 120.0, "EV", "y.jpg"));
        CarRepository.addCar(new Car(4, "BMW", "M4", 2024, 200.0, "Sport", "m4.jpg"));
        int userId = 11;
        LocalDate today = LocalDate.now();

        // past
        bookingService.bookCar(new BookingRequest(userId, 3,
                Date.valueOf(today.minusDays(10)), Date.valueOf(today.minusDays(5))));
        // future
        bookingService.bookCar(new BookingRequest(userId, 4,
                Date.valueOf(today.plusDays(5)), Date.valueOf(today.plusDays(7))));

        Map<String, List<BookingDisplay>> map = BookingService.categorizeBookings(userId);
        assertEquals(1, map.get("past").size());
        assertEquals("Tesla", map.get("past").get(0).getCar().getBrand());

        assertEquals(1, map.get("future").size());
        assertEquals("BMW", map.get("future").get(0).getCar().getBrand());

        assertTrue(map.get("current").isEmpty());
    }


    @Test
    void testDeleteBookingViaService() throws SQLException {
        CarRepository.addCar(new Car(8, "Kia", "Soul", 2022, 45.0, "Box", "s.jpg"));
        bookingService.bookCar(new BookingRequest(22, 8,
                Date.valueOf("2025-08-01"), Date.valueOf("2025-08-03")));
        int id = BookingService.getBookingById(1).getId();

        BookingService.deleteBooking(id);
        assertNull(BookingService.getBookingById(id));
    }
}
