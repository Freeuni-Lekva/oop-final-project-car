package org.example.tests;

import org.example.car.Car.Model.Car;
import org.example.car.Car.Repository.CarDetailsRepository;
import org.example.car.Car.Repository.CarRepository;
import org.example.car.DBConnector;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarRepositoryTest {

    @BeforeAll
    static void setup() throws SQLException {
        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
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

        }
    }


    @AfterEach
    void clearTables() throws SQLException {
        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM cars");
        }
    }

    @Test
    void testAddAndGetCarById() throws SQLException {
        Car car = new Car(1, "Tesla", "Model 3", 2022, 120.0,
                "Electric sedan", "tesla.jpg");
        CarRepository.addCar(car);

        Car fetched = CarRepository.getCarById(1);
        assertNotNull(fetched);
        assertEquals("Tesla", fetched.getBrand());
        assertEquals(120.0, fetched.getPrice_per_day());
    }

    @Test
    void testGetAllCars() throws SQLException {
        CarRepository.addCar(new Car(1, "BMW", "X5", 2020, 95.0, "SUV", "x5.jpg"));
        CarRepository.addCar(new Car(2, "Audi", "A4", 2019, 80.0, "Sedan", "a4.jpg"));

        assertEquals(2, CarRepository.getAllCars().size());
    }

    @Test
    void testGetSortedCarsAscending() throws SQLException {
        CarRepository.addCar(new Car(1, "Ford", "Focus", 2018, 40.0, "Compact", "f.jpg"));
        CarRepository.addCar(new Car(2, "Ford", "Mustang", 2021, 150.0, "Sport", "m.jpg"));

        List<Car> sorted = CarRepository.getSortedCars("year", "ASC");
        assertEquals(2018, sorted.get(0).getYear());
        assertEquals(2021, sorted.get(1).getYear());
    }

    @Test
    void testGetCarsFilter() throws SQLException {
        CarRepository.addCar(new Car(1, "Kia", "Rio", 2017, 30.0, "Cheap", "k.jpg"));
        CarRepository.addCar(new Car(2, "Mercedes", "E‑Class", 2022, 130.0, "Luxury", "e.jpg"));

        List<Car> midRange = CarRepository.getCarsFilter(25.0, 50.0, "");
        assertEquals(1, midRange.size());
        assertEquals("Rio", midRange.get(0).getModel());
    }
    @Test
    void testUpdateCar() throws SQLException {
        CarRepository.addCar(new Car(1, "VW", "Golf", 2019, 55.0, "Hatch", "g.jpg"));

        Car updated = new Car(1, "VW", "Golf", 2019, 60.0, "Updated desc", "g2.jpg");
        boolean ok = CarRepository.updateCar(updated);
        assertTrue(ok);

        Car after = CarRepository.getCarById(1);
        assertEquals(60.0, after.getPrice_per_day());
        assertEquals("Updated desc", after.getDescription());
    }


    @Test
    void testDeleteCar() throws SQLException {
        CarRepository.addCar(new Car(1, "Honda", "Civic", 2020, 45.0, "Reliable", "c.jpg"));
        assertTrue(CarRepository.deleteCar(1));
        assertNull(CarRepository.getCarById(1));
    }

    @Test
    void testCarDetailsRepositoryGetCarById() throws SQLException {
        Car sample = new Car(
                99,
                "Lexus",
                "RX",
                2023,
                110.0,
                "Luxury SUV",
                "lexus-rx.jpg");

        CarRepository.addCar(sample);

        Car fetched = CarDetailsRepository.getCarById(99);

        assertNotNull(fetched);
        assertEquals("Lexus",  fetched.getBrand());
        assertEquals("RX",     fetched.getModel());
        assertEquals(2023,     fetched.getYear());
        assertEquals(110.0,    fetched.getPrice_per_day());
        assertEquals("Luxury SUV", fetched.getDescription());
        assertEquals("lexus-rx.jpg",   fetched.getImage_url());
    }


} 