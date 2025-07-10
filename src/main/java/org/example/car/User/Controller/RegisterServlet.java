package org.example.car.User.Controller;

import org.example.car.User.Model.User;
import org.example.car.User.Service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullName = req.getParameter("full_name");
        String password = req.getParameter("password");

        if (fullName == null || fullName.isEmpty() ||
            password == null || password.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("All fields are required.");
            return;
        }


        boolean success = UserService.save(fullName, password, false);
        if (success) {
            String msg = URLEncoder.encode("Registration successful. Please log in.", "UTF-8");
            resp.sendRedirect("Authentication/jsp/login.jsp?msg=" + msg + "&success=true");
        } else {
            String msg = URLEncoder.encode("Registration failed. User with this full name already exists.", "UTF-8");
            resp.sendRedirect("Authentication/jsp/register.jsp?msg=" + msg + "&success=false");
        }
    }
} 