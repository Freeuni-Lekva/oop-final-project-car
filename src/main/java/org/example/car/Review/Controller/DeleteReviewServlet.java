package org.example.car.Review.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.car.Review.Repository.ReviewRepository;
import org.example.car.Review.Review;
import org.example.car.Review.Service.ReviewService;
import org.example.car.User.Model.User;

import java.io.IOException;
@WebServlet("/user/reviews/delete")
public class DeleteReviewServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/Authentication/jsp/login.jsp?msg=Please+login+first");
            return;
        }

        try {
            int reviewId = Integer.parseInt(req.getParameter("reviewId"));
            Review review = ReviewService.getReviewById(reviewId);

            if (review != null && review.getUser_id() == user.getId()) {
                ReviewService.deleteReview(reviewId);
            }
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Review not found.");
            req.getRequestDispatcher("/UserPage.jsp").forward(req, resp);
        }

        resp.sendRedirect("/UserPage");
    }
}

