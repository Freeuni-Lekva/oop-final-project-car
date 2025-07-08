package org.example.car.Review.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.car.Review.Repository.ReviewRepository;
import org.example.car.Review.Review;
import org.example.car.User.Model.User;

import java.io.IOException;
@WebServlet("/user/reviews/delete")
public class DeleteReviewServlet extends HttpServlet {

    private final ReviewRepository reviewRepo = new ReviewRepository();

}

