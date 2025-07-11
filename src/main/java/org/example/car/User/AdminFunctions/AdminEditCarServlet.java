package org.example.car.User.AdminFunctions;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.car.Car.Model.Car;
import org.example.car.Car.Service.CarService;
import org.example.car.User.Model.User;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/editCar")
public class AdminEditCarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/Authentication/jsp/login.jsp?msg=Access+denied");
            return;
        }

        String carIdStr = request.getParameter("carId");

        try {
            int carId = Integer.parseInt(carIdStr);

            Car car = CarService.getCarById(carId);
            if (car == null) {
                request.setAttribute("errorMessage", "Car not found.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            request.setAttribute("car", car);
            request.getRequestDispatcher("/Admin/JSP/editCar.jsp").forward(request, response);

        } catch (NumberFormatException | SQLException e) {
            request.setAttribute("errorMessage", "Invalid car ID or error loading car.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
