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
import java.util.Objects;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullName = req.getParameter("full_name");
        String password = req.getParameter("password");
        String prevPage = req.getParameter("prevPage");

        if (fullName == null || fullName.isEmpty() || password == null || password.isEmpty()) {
            String encodedPrevPage = prevPage != null ? URLEncoder.encode(prevPage, "UTF-8") : "";
            resp.sendRedirect("Authentication/jsp/login.jsp?msg=All+fields+are+required.&prevPage=" + encodedPrevPage);
            return;
        }

        User user = null;
        try {
            user = UserService.authenticate(fullName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            if(Objects.equals(prevPage, "home") || prevPage == null)
                resp.sendRedirect("HPcontroller");
            else{
                resp.sendRedirect("index.jsp");
            }
        } else {
            String encodedPrevPage = prevPage != null ? URLEncoder.encode(prevPage, "UTF-8") : "";
            resp.sendRedirect("Authentication/jsp/login.jsp?msg=Invalid+credentials.&prevPage=" + encodedPrevPage);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("Authentication/jsp/login.jsp");
    }

} 