package org.example.car.Review.Service;

import org.example.car.Car.Model.Car;
import org.example.car.Car.Service.CarService;
import org.example.car.Review.Repository.ReviewRepository;
import org.example.car.Review.Review;
import org.example.car.Review.ReviewDisplayForUser;
import org.example.car.Review.ReviewForCar;
import org.example.car.User.Model.User;
import org.example.car.User.Repository.UserRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewService {


    public static void saveReview(Review review) {
        ReviewRepository.save(review);
    }

//    public static List<Review> getReviewsByCarId(int carId) {
//        return ReviewRepository.getReviewsByCarId(carId);
//    }
    public static void deleteReview(int reviewId) {
        ReviewRepository.deleteReview(reviewId);
    }
    public static Review getReviewById(int reviewId){return ReviewRepository.getReviewByID(reviewId);}

    //public static List<Review> getReviewsByUserId(int userId){return ReviewRepository.getReviewsByUserId(userId);}

    //public static List<Review> getAllReviews(){return ReviewRepository.getReviews();}

    public static List<ReviewDisplayForUser> getReviewsByUserIdForUser(int userId) throws SQLException {
        List<Review> list = ReviewRepository.getReviewsByUserId(userId);
        List<ReviewDisplayForUser> res = new ArrayList<>();
        for( Review r: list){
            Car car = CarService.getCar(r.getCarId());
            res.add(new ReviewDisplayForUser(r, car));
        }
        return  res;
    }

    public static List<ReviewForCar> getReviewsToDisplayCar(int carId) throws SQLException {
        List<Review> list = ReviewRepository.getReviewsByCarId(carId);
        List<ReviewForCar> res = new ArrayList<>();
        for( Review r: list){
            int userId = r.getUser_id();
            User u = UserRepository.getUserById(userId);
            assert u != null;
            res.add(new ReviewForCar(r, u.getFull_name()));
        }
        return  res;
    }
}