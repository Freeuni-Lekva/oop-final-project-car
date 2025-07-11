package org.example.car.User.AdminFunctions;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.car.Car.Model.Car;
import org.example.car.Car.Service.CarService;
import org.example.car.User.Model.User;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/updateCar")
public class AdminUpdateCar extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/Authentication/jsp/login.jsp?msg=Please+login+first");
            return;
        }
        if (!currentUser.is_admin()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Not authorised");
            return;
        }

        try {
            int carId        = Integer.parseInt(request.getParameter("carId"));
            String brand     = request.getParameter("brand");
            String model     = request.getParameter("model");
            int year         = Integer.parseInt(request.getParameter("year"));
            double priceDay  = Double.parseDouble(request.getParameter("price_per_day"));
            String desc      = request.getParameter("description");
            String imageUrl  = request.getParameter("image_url");

            // 3️⃣ Build updated Car object
            Car updated = new Car(
                    carId,
                    brand,
                    model,
                    year,
                    priceDay,
                    desc,
                    imageUrl
            );


            CarService.updateCar(updated);


            response.sendRedirect(request.getContextPath() + "/admin/car-details?carId=" + carId + "&userId=" + currentUser.getId());

        } catch (NumberFormatException nfe) {
            request.setAttribute("errorMessage", "Invalid numeric field: " + nfe.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);

        } catch (SQLException sqlEx) {
            request.setAttribute("errorMessage", "Database error: " + sqlEx.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
