package org.example.car.User.AdminFunctions;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.car.User.Model.User;
import org.example.car.User.Service.UserService;

import java.io.IOException;

@WebServlet("/admin-user-search")
public class AdminUserSearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdParam = req.getParameter("userId");
        User searchedUser = null;
        String searchError = null;
        try {
            int userId = Integer.parseInt(userIdParam);
            searchedUser = UserService.getUserById(userId);
            if (searchedUser == null) {
                searchError = "No user found with ID: " + userId;
            }
        } catch (Exception e) {
            searchError = "Invalid user ID.";
        }
        req.setAttribute("searchedUser", searchedUser);
        req.setAttribute("searchError", searchError);
        req.getRequestDispatcher("/Admin/JSP/admin-dashboard.jsp").forward(req, resp);
    }
}