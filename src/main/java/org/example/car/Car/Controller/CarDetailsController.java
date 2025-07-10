package org.example.car.Car.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import org.example.car.Car.Model.Car;
import org.example.car.Car.Repository.CarDetailsRepository;
import org.example.car.Car.Service.CarService;
import org.example.car.Review.Review;
import org.example.car.Review.ReviewForCar;
import org.example.car.Review.Service.ReviewService;
import org.example.car.User.Service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet("/car-details")
public class CarDetailsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String carIdStr = req.getParameter("car");
       // String userIdStr = req.getParameter("userId");

        if (carIdStr != null) {
            try {

//                if(userIdStr==null){
//                    System.out.println("modis");
//                    req.setAttribute("userId", "");
//                }else {
//                    int userId = Integer.parseInt(userIdStr);
//                    req.setAttribute("userId", userId);
//                }

                int carId = Integer.parseInt(carIdStr);

                Car car = CarService.getCarById(carId);

                if (car != null) {
                    req.setAttribute("car", car);
                }



                List<ReviewForCar> reviews = ReviewService.getReviewsToDisplayCar(carId);
                req.setAttribute("reviews", reviews);
                req.getRequestDispatcher("CarDetailsPage/jsp/carDetails.jsp").forward(req, resp);

            } catch (NumberFormatException | SQLException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid car ID.");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing car ID.");
        }
    }
}
