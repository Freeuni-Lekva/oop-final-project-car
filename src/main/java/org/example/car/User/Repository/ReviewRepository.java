package org.example.car.User.Repository;

import org.example.car.User.Model.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository {

    private String jdbcUrl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public ReviewRepository(String jdbcUrl, String dbUser, String dbPassword) {
        this.jdbcUrl = jdbcUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public void save(Review review) {
        String sql = "INSERT INTO reviews (user_id, car_id, rating, comment) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, review.getUserId());
            stmt.setInt(2, review.getCarId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getComment());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Review> getReviewsByCarId(int carId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE car_id = ?";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, carId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Review r = new Review();
                r.setId(rs.getInt("id"));
                r.setUserId(rs.getInt("user_id"));
                r.setCarId(rs.getInt("car_id"));
                r.setRating(rs.getInt("rating"));
                r.setComment(rs.getString("comment"));
                reviews.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews;
    }
}