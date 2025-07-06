package org.example.car.User.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.car.Review;
import org.example.car.User.Service.ReviewService;

import java.io.IOException;

@WebServlet("/submitReview")
public class ReviewServlet extends HttpServlet {

    private final ReviewService reviewService = new ReviewService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String reviewIdStr = request.getParameter("reviewId");
        String comment = request.getParameter("comment");
        String ratingStr = request.getParameter("rating");
        String userIdStr = request.getParameter("userId");
        String carIdStr = request.getParameter("carId");

        System.out.println("DEBUG - userId: " + userIdStr);
        System.out.println("DEBUG - carId: " + carIdStr);
        System.out.println("DEBUG - rating: " + ratingStr);
        System.out.println("DEBUG - comment: " + comment);

        if (userIdStr == null || carIdStr == null || ratingStr == null || ratingStr.isEmpty()) {
            response.getWriter().println("Missing required parameters.");
            return;
        }

        try {
            int reviewId = Integer.parseInt(reviewIdStr);
            int userId = Integer.parseInt(userIdStr);
            int carId = Integer.parseInt(carIdStr);
            int rating = Integer.parseInt(ratingStr);

            Review review = new Review(reviewId, userId, carId, rating, comment);

            reviewService.saveReview(review);

            response.getWriter().println("Review submitted!");
            // response.sendRedirect("carDetails.jsp?carId=" + carId);
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid number format: " + e.getMessage());
        }
    }
}
