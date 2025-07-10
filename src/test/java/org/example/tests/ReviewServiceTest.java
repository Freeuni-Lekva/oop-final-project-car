package org.example.tests;


import org.example.car.Car.Model.Car;
import org.example.car.Car.Service.CarService;
import org.example.car.DBConnector;
import org.example.car.Review.Review;
import org.example.car.Review.Repository.ReviewRepository;
import org.example.car.Review.Service.ReviewService;
import org.example.car.Review.Service.ReviewDisplayForUser;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReviewServiceTest {

    @BeforeAll
    void setup() throws SQLException {
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


            stmt.execute("""
                CREATE TABLE IF NOT EXISTS reviews (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT NOT NULL,
                    car_id INT NOT NULL,
                    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
                    comment TEXT
                );
            """);
        }
    }

    @AfterEach
    void clearTables() throws SQLException {
        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM reviews");
            stmt.execute("DELETE FROM cars");
        }
    }

    @Test
    void testGetReviewsByUserIdForUser() throws SQLException {
        // Insert cars
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO cars (id, brand, model, \"year\", price_per_day, description, image_url)\n" +
                             "VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            ps.setInt(1, 100);
            ps.setString(2, "Toyota");
            ps.setString(3, "Corolla");
            ps.setInt(4, 2020);
            ps.setDouble(5, 50.0);
            ps.setString(6, "Reliable car");
            ps.setString(7, "toyota.jpg");
            ps.executeUpdate();

            ps.setInt(1, 101);
            ps.setString(2, "Honda");
            ps.setString(3, "Civic");
            ps.setInt(4, 2019);
            ps.setDouble(5, 45.0);
            ps.setString(6, "Compact and efficient");
            ps.setString(7, "honda.jpg");
            ps.executeUpdate();
        }

        Review r1 = new Review(0, 5, 100, 4, "Very nice car");
        Review r2 = new Review(0, 5, 101, 3, "Okay experience");

        ReviewRepository.save(r1);
        ReviewRepository.save(r2);

        List<ReviewDisplayForUser> result = ReviewService.getReviewsByUserIdForUser(5);

        assertEquals(2, result.size());


        assertTrue(result.stream().anyMatch(d ->
                d.getReview().getComment().equals("Very nice car") &&
                        d.getCar().getModel().equals("Corolla") &&
                        d.getCar().getYear() == 2020
        ));

        assertTrue(result.stream().anyMatch(d ->
                d.getReview().getComment().equals("Okay experience") &&
                        d.getCar().getBrand().equals("Honda") &&
                        d.getCar().getYear() == 2019
        ));
    }
}