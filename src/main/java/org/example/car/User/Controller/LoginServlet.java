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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullName = req.getParameter("full_name");
        String password = req.getParameter("password");

        if (fullName == null || fullName.isEmpty() || password == null || password.isEmpty()) {
            resp.sendRedirect("Authentication/jsp/login.jsp?msg=All+fields+are+required.");
            return;
        }

        User user = UserService.authenticate(fullName, password);
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect("index.jsp");
        } else {
            resp.sendRedirect("Authentication/jsp/login.jsp?msg=Invalid+credentials.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("Authentication/jsp/login.jsp");
    }

} 