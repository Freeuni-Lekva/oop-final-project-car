package org.example.car.User.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.car.BookingSystem.BookingDisplay;
import org.example.car.BookingSystem.Service.BookingService;
import org.example.car.Review.ReviewDisplayForUser;
import org.example.car.Review.Service.ReviewService;
import org.example.car.User.Model.User;
import org.example.car.User.Service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/userProfile")
public class UserProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");


        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/Authentication/jsp/login.jsp?msg=Access+denied");
            return;
        }

        int targetUserId;

        String param = req.getParameter("userId");
        if (param != null && user.isAdmin()) {
            targetUserId = Integer.parseInt(param);
        } else {
            targetUserId = user.getId();
        }

        User targetUser = UserService.getUserById(targetUserId);


        Map<String, List<BookingDisplay>> categorized = null;
        try {
            categorized = BookingService.categorizeBookings(targetUserId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<ReviewDisplayForUser> userReviews = null;
        try {
            userReviews = ReviewService.getReviewsByUserIdForUser(targetUserId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<BookingDisplay> past = categorized.get("past");

        int carsRented = past.size() ;
        req.setAttribute("pastBookings",past);
        req.setAttribute("currentBookings", categorized.get("current"));
        req.setAttribute("futureBookings", categorized.get("future"));
        req.setAttribute("userReviews", userReviews);
        req.setAttribute("user", targetUser);
        req.setAttribute("rentedCount", carsRented);

        req.getRequestDispatcher("/UserPage.jsp").forward(req, resp);
    }



}
