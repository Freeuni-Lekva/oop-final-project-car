package org.example.car.User.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.car.BookingSystem.Booking;
import org.example.car.BookingSystem.Repository.BookingRepository;
import org.example.car.Review.Review;
import org.example.car.User.Model.User;
import org.example.car.Review.Repository.ReviewRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/userProfile")
public class UserProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        BookingRepository bookingRepo = new BookingRepository();
        ReviewRepository reviewRepo = new ReviewRepository();

        Map<String, List<Booking>> categorized = bookingRepo.categorizeBookings(user.getId());

        List<Review> userReviews = reviewRepo.getReviewsByUserId(user.getId());

        req.setAttribute("pastBookings", categorized.get("past"));
        req.setAttribute("currentBookings", categorized.get("current"));
        req.setAttribute("futureBookings", categorized.get("future"));
        req.setAttribute("userReviews", userReviews);

        req.getRequestDispatcher("/userPage.jsp").forward(req, resp);
    }



}
