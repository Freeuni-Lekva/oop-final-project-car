package org.example.car.Car.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.car.Car.Service.CarService;
import org.example.car.User.Model.User;

import java.io.IOException;

@WebServlet("/deleteCar")
public class DeleteCarServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Authentication/jsp/login.jsp?msg=Please+login+first");
            return;
        }

        if (!user.isAdmin()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Not authorised");
            return;
        }

        String carIdParam = request.getParameter("carId");
        if (carIdParam == null || carIdParam.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing carId");
            return;
        }

        try {
            int carId = Integer.parseInt(carIdParam);

            CarService.deleteCar(carId);


            response.sendRedirect(request.getContextPath() + "/HPcontroller");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid carId");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error deleting car: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
