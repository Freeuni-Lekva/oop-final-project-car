package org.example.adminTests;

import org.example.car.User.Model.Review;
import org.example.car.User.Repository.ReviewRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ReviewTest {

    public static void main(String[] args) {
        try {
            String jdbcUrl = "jdbc:h2:mem:car_rental;DB_CLOSE_DELAY=-1";
            String dbUser = "sa";
            String dbPassword = "";


            Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);


            createReviewsTable(conn);
            System.out.println("Table created.");


            ReviewRepository repo = new ReviewRepository(jdbcUrl, dbUser, dbPassword);


            Review review = new Review();
            review.setUserId(1);
            review.setCarId(2);
            review.setRating(4);
            review.setComment("Dzalian kargi iyo");
            repo.save(review);
            System.out.println("Review inserted.");


            Review review2 = new Review();
            review2.setUserId(4);
            review2.setCarId(2);
            review2.setRating(2);
            review2.setComment("interieri ar uvarga");
            repo.save(review2);
            System.out.println("Review inserted.");


            List<Review> reviews = repo.getReviewsByCarId(2);
            for (Review r : reviews) {
                System.out.println(r.getRating() + " ★ - " + r.getComment());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createReviewsTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE reviews (
                id INT AUTO_INCREMENT PRIMARY KEY,
                user_id INT NOT NULL,
                car_id INT NOT NULL,
                rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
                comment TEXT
            );
            """;
        try (var stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
}
