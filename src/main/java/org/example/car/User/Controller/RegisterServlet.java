package org.example.car.User.Controller;

import org.example.car.User.Model.User;
import org.example.car.User.Service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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

        User user = new User(fullName, password, false);

        boolean success = UserService.save(user);
        if (success) {
            resp.getWriter().write("Registration successful.");
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Registration failed. User may already exist.");
        }
    }
} 