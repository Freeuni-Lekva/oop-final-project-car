package org.example.tests;

import org.example.car.DBConnector;
import org.example.car.Review;
import org.example.car.ReviewRepository;
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

        int startSize = reviews.size();
        int reviewId = reviews.get(0).getId();

        repo.deleteReview(reviewId);

        List<Review> afterDelete = repo.getReviewsByCarId(20);
        assertEquals(startSize-1, afterDelete.size());
    }


    @Test
    void testGetReviewsByUserId(){
        Review r1 = new Review(1,3,20,1,"Terrible");
        Review r2 = new Review(1,3,15,5,"Great");
        Review r3 = new Review(1,3,2,4,"good");
        Review r4 = new Review(1,4,2,4,"good");

        repo.save(r1);
        repo.save(r2);
        repo.save(r3);
        repo.save(r4);

        List<Review> reviews = repo.getReviewsByUserId(3);
        for(int i=0; i<reviews.size(); i++){
            assertEquals(3, reviews.get(i).getUser_id());
        }
    }
}
