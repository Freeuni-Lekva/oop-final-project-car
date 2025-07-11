package org.example.tests;

import org.example.car.Car.Model.Car;
import org.example.car.Car.Repository.CarRepository;
import org.example.car.DBConnector;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarRepositoryTest {
    private static int testCarId = 99999;

    @BeforeAll
    static void setup() throws SQLException {
        try (Connection conn = DBConnector.getConnection()) {
            Car car = new Car(testCarId, "RepoBrand", "RepoModel", 2022, 100.0, "Repo test car", "repo.jpg");
            CarRepository.addCar(car);
        }
    }

    @AfterAll
    static void cleanup() throws SQLException {
        try (Connection conn = DBConnector.getConnection()) {
            CarRepository.deleteCar(testCarId);
        }
    }

    @Test
    @Order(1)
    void testGetCarById() throws SQLException {
        Car car = CarRepository.getCarById(testCarId);
        assertNotNull(car);
        assertEquals("RepoBrand", car.getBrand());
    }

    @Test
    @Order(2)
    void testGetAllCars() {
        List<Car> cars = CarRepository.getAllCars();
        assertTrue(cars.stream().anyMatch(c -> c.getId() == testCarId));
    }

    @Test
    @Order(3)
    void testUpdateCar() throws SQLException {
        Car car = new Car(testCarId, "UpdatedBrand", "UpdatedModel", 2023, 150.0, "Updated desc", "updated.jpg");
        boolean updated = CarRepository.updateCar(car);
        assertTrue(updated);
        Car updatedCar = CarRepository.getCarById(testCarId);
        assertEquals("UpdatedBrand", updatedCar.getBrand());
        assertEquals(150.0, updatedCar.getPrice_per_day());
    }

    @Test
    @Order(4)
    void testGetSortedCars() {
        List<Car> cars = CarRepository.getSortedCars("price_per_day", "desc");
        assertNotNull(cars);
        assertTrue(cars.size() > 0);
    }

    @Test
    @Order(5)
    void testDeleteCar() throws SQLException {
        boolean deleted = CarRepository.deleteCar(testCarId);
        assertTrue(deleted);
        Car car = CarRepository.getCarById(testCarId);
        assertNull(car);
        CarRepository.addCar(new Car(testCarId, "RepoBrand", "RepoModel", 2022, 100.0, "Repo test car", "repo.jpg"));
    }
} 