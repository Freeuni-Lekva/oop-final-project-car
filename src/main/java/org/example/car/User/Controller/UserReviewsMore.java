package org.example.car.User.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.car.Review.ReviewDisplayForUser;
import org.example.car.Review.Service.ReviewService;
import org.example.car.User.Model.User;
import org.example.car.User.Service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/userReviewsFull")
public class UserReviewsMore extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        // Determine which user's reviews to show
        int targetUserId;
        String param = req.getParameter("userId");
        if (param != null && user.isAdmin()) {
            targetUserId = Integer.parseInt(param);
        } else {
            targetUserId = user.getId();
        }

        List<ReviewDisplayForUser> reviews;
        User targetUser = null;
        try {
            reviews = ReviewService.getReviewsByUserIdForUser(targetUserId);
            targetUser = UserService.getUserById(targetUserId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        req.setAttribute("userReviews", reviews);
        req.setAttribute("user", user);
        req.setAttribute("targetUser", targetUser);

        req.getRequestDispatcher("/UserPage/JSP/userReviewFullList.jsp").forward(req, resp);
    }
}
