package org.example.car.User.Service;

import org.example.car.User.Model.Review;
import org.example.car.User.Repository.ReviewRepository;

import java.util.List;

public class ReviewService {

    private String jdbcUrl = "jdbc:mysql://localhost:3307/oopFinal";
    private String dbUser = "root";
    private String dbPassword = "Lisemeitner1878$";

    private final ReviewRepository reviewRepository = new ReviewRepository(jdbcUrl, dbUser, dbPassword);

    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    public List<Review> getReviewsByCarId(int carId) {
        return reviewRepository.getReviewsByCarId(carId);
    }
    public void deleteReview(int reviewId) {
        reviewRepository.deleteReview(reviewId);
    }

}