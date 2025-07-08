package org.example.car.Review.Service;

import org.example.car.Review.Repository.ReviewRepository;
import org.example.car.Review.Review;

import java.util.List;

public class ReviewService {


    private final ReviewRepository reviewRepository = new ReviewRepository();

    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    public List<Review> getReviewsByCarId(int carId) {
        return reviewRepository.getReviewsByCarId(carId);
    }
    public void deleteReview(int reviewId) {
        reviewRepository.deleteReview(reviewId);
    }

    public List<Review> getReviewsByUserId(int userId){return reviewRepository.getReviewsByUserId(userId);}

}