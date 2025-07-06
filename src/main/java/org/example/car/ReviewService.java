package org.example.car;

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