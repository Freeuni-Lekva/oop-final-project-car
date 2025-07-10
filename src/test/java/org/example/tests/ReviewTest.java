package org.example.tests;

import org.example.car.DBConnector;
import org.example.car.Review.Review;
import org.example.car.Review.Repository.ReviewRepository;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReviewTest {

    @BeforeAll
    void setupDatabase() throws SQLException {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                CREATE TABLE IF NOT EXISTS reviews (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT NOT NULL,
                    car_id INT NOT NULL,
                    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
                    comment TEXT
                );
                """;
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            }
        }
    }

    @AfterEach
    void clearTable() throws SQLException {
        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM reviews");
        }
    }

    @Test
    void testSaveAndFetchReviews() {
        Review r1 = new Review(0, 1, 10, 5, "Great car");
        Review r2 = new Review(0, 2, 10, 3, "Okay car");

        ReviewRepository.save(r1);
        ReviewRepository.save(r2);

        List<Review> reviews = ReviewRepository.getReviewsByCarId(10);
        assertEquals(2, reviews.size());
        assertTrue(reviews.stream().anyMatch(r -> r.getComment().equals("Great car")));
        assertTrue(reviews.stream().anyMatch(r -> r.getComment().equals("Okay car")));
    }

    @Test
    void testDeleteReview() {
        Review review = new Review(0, 3, 20, 1, "Terrible");
        int id = ReviewRepository.save(review);

        assertNotNull(ReviewRepository.getReviewByID(id));
        ReviewRepository.deleteReview(id);
        assertNull(ReviewRepository.getReviewByID(id));
    }

    @Test
    void testGetReviewsByUserId() {
        Review r1 = new Review(0, 3, 20, 1, "Terrible");
        Review r2 = new Review(0, 3, 15, 5, "Great");
        Review r3 = new Review(0, 3, 2, 4, "Good");
        Review r4 = new Review(0, 4, 2, 4, "Also good");

        ReviewRepository.save(r1);
        ReviewRepository.save(r2);
        ReviewRepository.save(r3);
        ReviewRepository.save(r4);

        List<Review> reviews = ReviewRepository.getReviewsByUserId(3);
        assertEquals(3, reviews.size());
        for (Review r : reviews) {
            assertEquals(3, r.getUser_id());
        }
    }

    @Test
    void testGetReviewById() {
        Review review = new Review(0, 6, 33, 4, "Good review");
        int id = ReviewRepository.save(review);

        Review fetched = ReviewRepository.getReviewByID(id);
        assertNotNull(fetched);
        assertEquals("Good review", fetched.getComment());
    }

    @Test
    void testGetAllReviews() {
        Review r1 = new Review(0, 7, 101, 5, "Excellent");
        Review r2 = new Review(0, 8, 102, 2, "Bad");

        ReviewRepository.save(r1);
        ReviewRepository.save(r2);

        List<Review> allReviews = ReviewRepository.getReviews();
        assertTrue(allReviews.size() >= 2);
    }
}
