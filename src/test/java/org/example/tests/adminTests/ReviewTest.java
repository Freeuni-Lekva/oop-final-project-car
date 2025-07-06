package org.example.tests.adminTests;

import org.example.car.DBConnector;
import org.example.car.Review;
import org.example.car.User.Repository.ReviewRepository;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReviewTest {

    private ReviewRepository repo;

    @BeforeAll
    void setupDatabase() throws SQLException {

        // Create table
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                CREATE TABLE reviews (
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

        repo = new ReviewRepository();
    }

    @Test
    void testSaveAndFetchReviews() {
        Review review1 = new Review(1, 1,10,5, "Great car");

        Review review2 = new Review(2,2,10,3,"Okay car");

        repo.save(review1);
        repo.save(review2);

        List<Review> reviews = repo.getReviewsByCarId(10);

        assertEquals(2, reviews.size());
        assertTrue(reviews.stream().anyMatch(r -> r.getComment().equals("Great car")));
        assertTrue(reviews.stream().anyMatch(r -> r.getComment().equals("Okay car")));
    }

    @Test
    void testDeleteReview() {
        Review review = new Review(1,3,20,1,"Terrible");

        repo.save(review);

        List<Review> reviews = repo.getReviewsByCarId(20);
        assertEquals(1, reviews.size());
        int reviewId = reviews.get(0).getId();

        repo.deleteReview(reviewId);

        List<Review> afterDelete = repo.getReviewsByCarId(20);
        assertEquals(0, afterDelete.size());
    }
}
