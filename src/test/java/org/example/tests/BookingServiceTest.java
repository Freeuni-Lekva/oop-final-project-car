package org.example.tests;

import org.example.car.BookingSystem.Booking;
import org.example.car.BookingSystem.BookingDisplay;
import org.example.car.BookingSystem.BookingRequest;
import org.example.car.BookingSystem.Service.BookingService;
import org.example.car.Car.Model.Car;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        bookingService = new BookingService();
    }

    @Test
    void testBookCar_CarAvailable() {
        BookingRequest request = new BookingRequest(1, 2, Date.valueOf("2024-06-01"), Date.valueOf("2024-06-05"));
        BookingService service = Mockito.spy(new BookingService());
        BookingService.BookingRepositoryMock repo = mock(BookingService.BookingRepositoryMock.class);
        // Simulate available car
        when(repo.isCarAvaliable(request)).thenReturn(true);
        when(repo.insertBooking(request)).thenReturn(true);
        service.setBookingRepository(repo);
        assertTrue(service.bookCar(request));
    }

    @Test
    void testBookCar_CarNotAvailable() {
        BookingRequest request = new BookingRequest(1, 2, Date.valueOf("2024-06-01"), Date.valueOf("2024-06-05"));
        BookingService service = Mockito.spy(new BookingService());
        BookingService.BookingRepositoryMock repo = mock(BookingService.BookingRepositoryMock.class);
        // Simulate unavailable car
        when(repo.isCarAvaliable(request)).thenReturn(false);
        service.setBookingRepository(repo);
        assertFalse(service.bookCar(request));
    }

    @Test
    void testCategorizeBookings() throws SQLException {
        int userId = 1;
        Map<String, List<Booking>> fakeMap = new HashMap<>();
        Booking booking = new Booking(1, userId, 2, Date.valueOf("2024-06-01"), Date.valueOf("2024-06-05"));
        fakeMap.put("future", Arrays.asList(booking));
        fakeMap.put("past", new ArrayList<>());
        fakeMap.put("current", new ArrayList<>());
        Car car = new Car(2, "Brand", "Model", 2020, 100.0, "desc", "img.png");
        try (MockedStatic<org.example.car.BookingSystem.Repository.BookingRepository> repoMock = mockStatic(org.example.car.BookingSystem.Repository.BookingRepository.class);
             MockedStatic<org.example.car.Car.Service.CarService> carServiceMock = mockStatic(org.example.car.Car.Service.CarService.class)) {
            repoMock.when(() -> org.example.car.BookingSystem.Repository.BookingRepository.categorizeBookings(userId)).thenReturn(fakeMap);
            carServiceMock.when(() -> org.example.car.Car.Service.CarService.getCar(2)).thenReturn(car);
            Map<String, List<BookingDisplay>> result = BookingService.categorizeBookings(userId);
            assertEquals(1, result.get("future").size());
            assertEquals(booking, result.get("future").get(0).getBooking());
            assertEquals(car, result.get("future").get(0).getCar());
        }
    }

    @Test
    void testDeleteBooking() {
        try (MockedStatic<org.example.car.BookingSystem.Repository.BookingRepository> repoMock = mockStatic(org.example.car.BookingSystem.Repository.BookingRepository.class)) {
            BookingService.deleteBooking(5);
            repoMock.verify(() -> org.example.car.BookingSystem.Repository.BookingRepository.deleteBooking(5));
        }
    }

    @Test
    void testGetBookingById() {
        Booking booking = new Booking(1, 2, 3, Date.valueOf("2024-06-01"), Date.valueOf("2024-06-05"));
        try (MockedStatic<org.example.car.BookingSystem.Repository.BookingRepository> repoMock = mockStatic(org.example.car.BookingSystem.Repository.BookingRepository.class)) {
            repoMock.when(() -> org.example.car.BookingSystem.Repository.BookingRepository.getBookingById(1)).thenReturn(booking);
            Booking result = BookingService.getBookingById(1);
            assertEquals(booking, result);
        }
    }
} 