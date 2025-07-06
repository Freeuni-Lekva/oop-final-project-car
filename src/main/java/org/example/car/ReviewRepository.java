package org.example.car;

//import org.example.tests.DBConnectorForTests;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository {


    public void save(Review review) {
        String sql = "INSERT INTO reviews (user_id, car_id, rating, comment) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, review.getUser_id());
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

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, carId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Review r = new Review(rs.getInt("id"), rs.getInt("user_id"),
                        rs.getInt("car_id"), rs.getInt("rating"), rs.getString("comment"));
                reviews.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews;
    }

    public void deleteReview(int reviewId) {
        String sql = "DELETE FROM reviews WHERE id = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reviewId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Review> getReviewsByUserId(int userId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE user_id = ?";

        try (
            Connection conn =DBConnector.getConnection();
            PreparedStatement stmt= conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Review r =new Review(rs.getInt("id"),userId, rs.getInt("car_id"),
                        rs.getInt("rating"), rs.getString("comment"));
                reviews.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews;
    }



}