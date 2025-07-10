package org.example.car.User.Controller;

import org.example.car.User.Model.User;
import org.example.car.User.Service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/admin-login")
public class AdminLoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullName = req.getParameter("full_name");
        String password = req.getParameter("password");

        if (fullName == null || fullName.isEmpty() || password == null || password.isEmpty()) {
            String msg = URLEncoder.encode("All fields are required.", "UTF-8");
            resp.sendRedirect("Authentication/jsp/adminLogin.jsp?msg=" + msg);
            return;
        }

        User user = UserService.authenticate(fullName, password);
        if (user != null && user.is_admin()) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect("admin-dashboard.jsp");
        } else {
            String msg = URLEncoder.encode("Invalid admin credentials.", "UTF-8");
            resp.sendRedirect("Authentication/jsp/adminLogin.jsp?msg=" + msg);
        }
    }
} 