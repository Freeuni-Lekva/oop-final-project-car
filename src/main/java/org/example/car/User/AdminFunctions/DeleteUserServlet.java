package org.example.car.User.AdminFunctions;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.car.User.Model.User;
import org.example.car.User.Service.UserService;

import java.io.IOException;

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User currentUser = (User) request.getSession().getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/Authentication/jsp/login.jsp?msg=Please+login+first");
            return;
        }

        if (!currentUser.is_admin()) {
            request.setAttribute("errorMessage", "Only admins are allowed to delete users.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        String userIdStr = request.getParameter("userId");

        try {
            int userId = Integer.parseInt(userIdStr);

            boolean deleted = UserService.deleteUser(userId);

            if (deleted) {
                response.sendRedirect(request.getContextPath() + "/admin-dashboard/user-list");
            } else {
                request.setAttribute("errorMessage", "Failed to delete user.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid user ID format.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error deleting user: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
