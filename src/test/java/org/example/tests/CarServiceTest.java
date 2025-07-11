package org.example.tests;

import org.example.car.Car.Model.Car;
import org.example.car.Car.Service.CarService;
import org.example.car.DBConnector;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarServiceTest {
    private static int testCarId = 88888;

    @BeforeAll
    static void setup() throws SQLException {
        try (Connection conn = DBConnector.getConnection()) {
            CarService.addCar(new Car(testCarId, "ServiceBrand", "ServiceModel", 2021, 80.0, "Service test car", "service.jpg"));
        }
    }

    @AfterAll
    static void cleanup() throws SQLException {
        try (Connection conn = DBConnector.getConnection()) {
            CarService.deleteCar(testCarId);
        }
    }

    @Test
    @Order(1)
    void testGetCar() throws SQLException {
        Car car = CarService.getCar(testCarId);
        assertNotNull(car);
        assertEquals("ServiceBrand", car.getBrand());
    }

    @Test
    @Order(2)
    void testGetAllCars() {
        List<Car> cars = CarService.getAllCars();
        assertTrue(cars.stream().anyMatch(c -> c.getId() == testCarId));
    }

    @Test
    @Order(3)
    void testUpdateCar() throws SQLException {
        Car updated = new Car(testCarId, "ServiceBrand2", "ServiceModel2", 2022, 120.0, "Updated service car", "service2.jpg");
        CarService.updateCar(updated);
        Car car = CarService.getCar(testCarId);
        assertEquals("ServiceBrand2", car.getBrand());
        assertEquals(120.0, car.getPrice_per_day());
    }

    @Test
    @Order(4)
    void testDeleteCar() throws SQLException {
        CarService.deleteCar(testCarId);
        Car car = CarService.getCar(testCarId);
        assertNull(car);
        CarService.addCar(new Car(testCarId, "ServiceBrand", "ServiceModel", 2021, 80.0, "Service test car", "service.jpg"));
    }
} 