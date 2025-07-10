package org.example.car.Car.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import org.example.car.Car.Model.Car;
import org.example.car.Car.Repository.CarDetailsRepository;

import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/car-details")
public class CarDetailsController extends HttpServlet {

    private CarDetailsRepository CarDetailsRepository;

    @Override
    public void init() throws ServletException {
        CarDetailsRepository = new CarDetailsRepository(); // or inject if needed
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String carIdStr = req.getParameter("car");
        String userIdStr = req.getParameter("user");

        if (carIdStr != null) {
            try {
                int carId = Integer.parseInt(carIdStr);

                Car car = CarDetailsRepository.getCarById(carId);

                if (car != null) {
                    req.setAttribute("car", car);
                }
                if (userIdStr != null) {
                    int userId = Integer.parseInt(userIdStr);
                    req.setAttribute("user", userId);
                }
                req.getRequestDispatcher("/carDetails.jsp").forward(req, resp);

            } catch (NumberFormatException | SQLException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid car ID."); //HELPED
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing car ID."); //HELPED
        }
    }
}
