package org.example.car.User.Controller;

import org.example.car.User.Service.UserService;
import org.example.car.Constants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/admin-register")
public class AdminRegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullName = req.getParameter("full_name");
        String password = req.getParameter("password");
        String invitationCode = req.getParameter("invitation_code");
        final String REQUIRED_CODE = Constants.ADMIN_INVITATION_CODE;

        boolean isAdmin = true;

        if (fullName == null || fullName.isEmpty() || password == null || password.isEmpty() || invitationCode == null || invitationCode.isEmpty()) {
            String msg = URLEncoder.encode("All fields are required.", "UTF-8");
            resp.sendRedirect("Authentication/jsp/adminRegister.jsp?msg=" + msg);
            return;
        }
        if (!REQUIRED_CODE.equals(invitationCode)) {
            String msg = URLEncoder.encode("Invalid invitation code.", "UTF-8");
            resp.sendRedirect("Authentication/jsp/adminRegister.jsp?msg=" + msg);
            return;
        }

        boolean success = UserService.save(fullName, password, isAdmin);
        if (success) {
            String msg = URLEncoder.encode("Admin registration successful. Please log in.", "UTF-8");
            resp.sendRedirect("Authentication/jsp/adminLogin.jsp?msg=" + msg + "&success=true");
        } else {
            String msg = URLEncoder.encode("Registration failed. User with this full name already exists.", "UTF-8");
            resp.sendRedirect("Authentication/jsp/adminRegister.jsp?msg=" + msg + "&success=false");
        }
    }
}
