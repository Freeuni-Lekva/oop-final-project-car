package org.example.car.Review.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.car.Review.Review;
import org.example.car.Review.Service.ReviewService;
import org.example.car.User.Model.User;

import java.io.IOException;

@WebServlet("/submitReview")
public class WriteReviewServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String comment = request.getParameter("comment");
        String ratingStr = request.getParameter("rating");
        String carIdStr = request.getParameter("carId");


        if(user==null){
            response.sendRedirect(request.getContextPath() + "/login" );

        }
        else {

            if (carIdStr == null || ratingStr == null || ratingStr.isEmpty()) {
                response.getWriter().println("Missing required parameters.");
                return;
            }

            try {
                int userId = user.getId();
                int carId = Integer.parseInt(carIdStr);
                int rating = Integer.parseInt(ratingStr);

                Review review = new Review(0, userId, carId, rating, comment);

                ReviewService.saveReview(review);

                //response.getWriter().println("Review submitted!");
                response.sendRedirect(request.getContextPath() + "/car-details?car=" + carId + "&userId=" + userId);

            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid number format: " + e.getMessage());
            }
        }
    }
}
