package org.example.car.Review.Repository;

//import org.example.tests.DBConnectorForTests;

import org.example.car.DBConnector;
import org.example.car.Review.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository {


    public static int save(Review review) {
        String sql = "INSERT INTO reviews (user_id, car_id, rating, comment) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, review.getUser_id());
            stmt.setInt(2, review.getCarId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getComment());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public static List<Review> getReviewsByCarId(int carId) {
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

    public static void deleteReview(int reviewId) {
        String sql = "DELETE FROM reviews WHERE id = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reviewId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Review getReviewByID(int reviewId) {
        String sql = "SELECT * FROM reviews WHERE id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reviewId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Review(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("car_id"),
                        rs.getInt("rating"),
                        rs.getString("comment")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static List<Review> getReviewsByUserId(int userId) {
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

    public static List<Review> getReviews(){
        List<Review> reviews = new ArrayList<>();
        String sql = "select * from reviews";

        try(
                Connection conn = DBConnector.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ){
            while(rs.next()){
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int car_id = rs.getInt("car_id");
                int rating = rs.getInt("rating");
                String comment = rs.getString("comment");

                Review review = new Review(id, user_id, car_id, rating, comment);
                reviews.add(review);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return reviews;
    }


}