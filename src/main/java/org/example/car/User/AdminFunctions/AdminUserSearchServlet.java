package org.example.car.User.AdminFunctions;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.car.User.Model.User;
import org.example.car.User.Service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin-user-search")
public class AdminUserSearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userNameParam = req.getParameter("userName");
        List<User> searchedUsers = null;
        String searchError = null;
        try {
            searchedUsers = UserService.searchUsersByName(userNameParam);
            if (searchedUsers == null || searchedUsers.isEmpty()) {
                searchError = "No users found with name: " + userNameParam;
            }
        } catch (Exception e) {
            searchError = "Invalid search input.";
        }
        req.setAttribute("searchedUsers", searchedUsers);
        req.setAttribute("searchError", searchError);
        req.getRequestDispatcher("/Admin/JSP/admin-dashboard.jsp").forward(req, resp);
    }
}