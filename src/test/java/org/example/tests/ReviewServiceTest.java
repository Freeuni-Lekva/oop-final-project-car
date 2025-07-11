package org.example.tests;


import org.example.car.DBConnector;
import org.example.car.Review.Review;
import org.example.car.Review.Repository.ReviewRepository;
import org.example.car.Review.ReviewForCar;
import org.example.car.Review.Service.ReviewService;
import org.example.car.Review.ReviewDisplayForUser;
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
                    `year` INT,
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

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INT PRIMARY KEY,
                    full_name VARCHAR(255),
                    password_hash VARCHAR(255),
                    is_admin BOOLEAN
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
                     "INSERT INTO cars (id, brand, model, `year`, price_per_day, description, image_url)\n" +
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

    @Test
    void testSaveAndGetReviewById() throws SQLException {
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO cars (id, brand, model, `year`, price_per_day, description, image_url) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            ps.setInt(1, 200);
            ps.setString(2, "Ford");
            ps.setString(3, "Focus");
            ps.setInt(4, 2018);
            ps.setDouble(5, 40.0);
            ps.setString(6, "Economic");
            ps.setString(7, "ford.jpg");
            ps.executeUpdate();
        }

        Review review = new Review(0, 10, 200, 5, "Excellent");
        ReviewService.saveReview(review);

        List<Review> allReviews = ReviewRepository.getReviewsByUserId(10);
        assertEquals(1, allReviews.size());

        int reviewId = allReviews.get(0).getId();
        Review fetched = ReviewService.getReviewById(reviewId);

        assertNotNull(fetched);
        assertEquals("Excellent", fetched.getComment());
        assertEquals(200, fetched.getCarId());
        assertEquals(5, fetched.getRating());
    }

    @Test
    void testDeleteReview() throws SQLException {
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO cars (id, brand, model, `year`, price_per_day, description, image_url) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            ps.setInt(1, 300);
            ps.setString(2, "Mazda");
            ps.setString(3, "3");
            ps.setInt(4, 2017);
            ps.setDouble(5, 38.0);
            ps.setString(6, "Smooth ride");
            ps.setString(7, "mazda.jpg");
            ps.executeUpdate();
        }

        Review review = new Review(0, 20, 300, 2, "Not good");
        ReviewService.saveReview(review);

        List<Review> all = ReviewRepository.getReviewsByUserId(20);
        assertEquals(1, all.size());

        int reviewId = all.get(0).getId();

        ReviewService.deleteReview(reviewId);

        Review afterDelete = ReviewRepository.getReviewByID(reviewId);
        assertNull(afterDelete);
    }

    @Test
    void testGetReviewsToDisplayCar() throws SQLException {
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement("INSERT INTO users (id, full_name, password_hash, is_admin) VALUES (?,?,?,?)")) {

            ps.setInt(1, 50);
            ps.setString(2, "Alice Smith");
            ps.setString(3, "alice123");
            ps.setBoolean(4, false);
            ps.executeUpdate();

            ps.setInt(1, 51);
            ps.setString(2, "Bob Jones");
            ps.setString(3, "bob123");
            ps.setBoolean(4, false);
            ps.executeUpdate();
        }

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO cars (id, brand, model, `year`, price_per_day, description, image_url) VALUES (?,?,?,?,?,?,?)")) {

            ps.setInt(1, 400);
            ps.setString(2, "BMW");
            ps.setString(3, "X3");
            ps.setInt(4, 2021);
            ps.setDouble(5, 90.0);
            ps.setString(6, "Luxury SUV");
            ps.setString(7, "bmw.jpg");
            ps.executeUpdate();
        }

        Review r1 = new Review(0, 50, 400, 5, "Loved it!");
        Review r2 = new Review(0, 51, 400, 3, "Average ride");
        ReviewRepository.save(r1);
        ReviewRepository.save(r2);

        List<ReviewForCar> reviews = ReviewService.getReviewsToDisplayCar(400);

        assertEquals(2, reviews.size());

        assertTrue(reviews.stream().anyMatch(rc ->
                rc.getReview().getComment().equals("Loved it!") &&
                        rc.getUserName().equals("Alice Smith")));

        assertTrue(reviews.stream().anyMatch(rc ->
                rc.getReview().getComment().equals("Average ride") &&
                        rc.getUserName().equals("Bob Jones")));
    }



}