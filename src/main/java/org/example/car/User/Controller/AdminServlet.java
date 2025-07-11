package org.example.car.User.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.car.User.Model.User;
import org.example.car.User.Repository.UserRepository;
import org.example.car.User.Service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin-dashboard")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/Authentication/jsp/login.jsp?msg=Unauthorized+access");
            return;
        }

        List<User> allUsers = UserService.getAllUsers();
        req.setAttribute("admin", user);
        req.setAttribute("users", allUsers);

        req.getRequestDispatcher("/Admin/JSP/admin-dashboard.jsp").forward(req, resp);
    }
}
