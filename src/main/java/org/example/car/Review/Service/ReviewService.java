package org.example.car.Review.Service;

import org.example.car.Review.Repository.ReviewRepository;
import org.example.car.Review.Review;

import java.util.List;

public class ReviewService {


    public static void saveReview(Review review) {
        ReviewRepository.save(review);
    }

    public static List<Review> getReviewsByCarId(int carId) {
        return ReviewRepository.getReviewsByCarId(carId);
    }
    public static void deleteReview(int reviewId) {
        ReviewRepository.deleteReview(reviewId);
    }
    public static Review getReviewById(int reviewId){return ReviewRepository.getReviewByID(reviewId);}

    public static List<Review> getReviewsByUserId(int userId){return ReviewRepository.getReviewsByUserId(userId);}

    public static List<Review> getAllReviews(){return ReviewRepository.getReviews();}

}