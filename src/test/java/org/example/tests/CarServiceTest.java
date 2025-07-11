package org.example.tests;

import org.example.car.Car.Model.Car;
import org.example.car.Car.Repository.CarRepository;
import org.example.car.Car.Service.CarService;
import org.example.car.DBConnector;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarServiceTest {

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
    void testAddCarAndGetById() throws SQLException {
        Car car = new Car(10, "Toyota", "Yaris", 2021, 40.0, "Economy", "y.jpg");
        CarService.addCar(car);

        Car fetched = CarService.getCar(10);
        assertNotNull(fetched);
        assertEquals("Yaris", fetched.getModel());
    }

    @Test
    void testGetAllCarsViaService() throws SQLException {
        CarService.addCar(new Car(1, "BMW", "X1", 2020, 80.0, "SUV", "x1.jpg"));
        CarService.addCar(new Car(2, "Audi", "Q2", 2019, 75.0, "SUV", "q2.jpg"));

        List<Car> all = CarService.getAllCars();
        assertEquals(2, all.size());
    }

    @Test
    void testDeleteCarViaService() throws SQLException {
        CarService.addCar(new Car(3, "Honda", "Jazz", 2018, 35.0, "Compact", "j.jpg"));
        CarService.deleteCar(3);

        assertNull(CarService.getCarById(3));
    }

    @Test
    void testGetCarsFilterViaService() throws SQLException {
        CarService.addCar(new Car(4, "Kia", "Picanto", 2017, 25.0, "Cheap", "p.jpg"));
        CarService.addCar(new Car(5, "Mercedes", "GLE", 2022, 150.0, "Luxury", "gle.jpg"));

        List<Car> cheap = CarService.getCarsFilter(20.0, 30.0, "");
        assertEquals(1, cheap.size());
        assertEquals("Picanto", cheap.get(0).getModel());
    }

    @Test
    void testUpdateCarSuccess() throws SQLException {
        CarService.addCar(new Car(6, "VW", "Polo", 2019, 50.0, "Hatch", "po.jpg"));

        Car updated = new Car(6, "VW", "Polo", 2019, 55.0, "New desc", "po2.jpg");
        CarService.updateCar(updated);

        Car after = CarRepository.getCarById(6);
        assertEquals(55.0, after.getPrice_per_day());
        assertEquals("New desc", after.getDescription());
    }

    @Test
    void testUpdateCarNegativePriceThrows() throws SQLException {
        CarService.addCar(new Car(7, "Fiat", "500", 2020, 45.0, "City car", "f.jpg"));

        Car bad = new Car(7, "Fiat", "500", 2020, -10.0, "Invalid", "f.jpg");
        assertThrows(IllegalArgumentException.class, () -> CarService.updateCar(bad));
    }

    @Test
    void testUpdateCarNotFoundThrows() {
        Car ghost = new Car(999, "Ghost", "Phantom", 1900, 0.0, "none", "g.jpg");
        assertThrows(SQLException.class, () -> CarService.updateCar(ghost));
    }


} 