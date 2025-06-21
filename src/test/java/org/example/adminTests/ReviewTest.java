package org.example.adminTests;

import org.example.car.User.Model.Review;
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
        String jdbcUrl = "jdbc:h2:mem:car_rental;DB_CLOSE_DELAY=-1";
        String dbUser = "sa";
        String dbPassword = "";

        // Create table
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
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

        repo = new ReviewRepository(jdbcUrl, dbUser, dbPassword);
    }

    @Test
    void testSaveAndFetchReviews() {
        Review review1 = new Review();
        review1.setUserId(1);
        review1.setCarId(10);
        review1.setRating(5);
        review1.setComment("Great car");

        Review review2 = new Review();
        review2.setUserId(2);
        review2.setCarId(10);
        review2.setRating(3);
        review2.setComment("Okay car");

        repo.save(review1);
        repo.save(review2);

        List<Review> reviews = repo.getReviewsByCarId(10);

        assertEquals(2, reviews.size());
        assertTrue(reviews.stream().anyMatch(r -> r.getComment().equals("Great car")));
        assertTrue(reviews.stream().anyMatch(r -> r.getComment().equals("Okay car")));
    }

    @Test
    void testDeleteReview() {
        Review review = new Review();
        review.setUserId(3);
        review.setCarId(20);
        review.setRating(1);
        review.setComment("Terrible");

        repo.save(review);

        List<Review> reviews = repo.getReviewsByCarId(20);
        assertEquals(1, reviews.size());
        int reviewId = reviews.get(0).getId();

        repo.deleteReview(reviewId);

        List<Review> afterDelete = repo.getReviewsByCarId(20);
        assertEquals(0, afterDelete.size());
    }
}
