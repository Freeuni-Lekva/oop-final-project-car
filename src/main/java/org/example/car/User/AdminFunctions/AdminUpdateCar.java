package org.example.car.User.AdminFunctions;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.car.Car.Model.Car;
import org.example.car.Car.Service.CarService;
import org.example.car.User.Model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

@WebServlet("/updateCar")
@MultipartConfig(
        maxFileSize      = 10 * 1024 * 1024,
        maxRequestSize   = 12 * 1024 * 1024
)
public class AdminUpdateCar extends HttpServlet {

    private static final String UPLOAD_DIR = "images/cars";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath()
                    + "/Authentication/jsp/login.jsp?msg=Please+login+first");
            return;
        }
        if (!currentUser.isAdmin()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Not authorised");
            return;
        }

        try {
            int    carId     = Integer.parseInt(request.getParameter("carId"));
            String brand     = request.getParameter("brand");
            String model     = request.getParameter("model");
            int    year      = Integer.parseInt(request.getParameter("year"));
            double priceDay  = Double.parseDouble(request.getParameter("price_per_day"));
            String desc      = request.getParameter("description");


            Car existing = CarService.getCarById(carId);
            if (existing == null) {
                throw new SQLException("Car not found id=" + carId);
            }
            String finalImageUrl = existing.getImage_url();


            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = Paths.get(filePart.getSubmittedFileName())
                        .getFileName().toString();

                fileName = System.currentTimeMillis() + "-" + fileName;

                Path uploadPath = Paths.get(
                        getServletContext().getRealPath("/") + UPLOAD_DIR);
                Files.createDirectories(uploadPath);
                Path filePath = uploadPath.resolve(fileName);
                filePart.write(filePath.toString());

                finalImageUrl = request.getContextPath() + "/" + UPLOAD_DIR + "/" + fileName;
            } else {

                String fallback = request.getParameter("image_url_fallback");
                if (fallback != null && !fallback.isBlank()) {
                    finalImageUrl = fallback.trim();
                }

            }


            Car updated = new Car(
                    carId,
                    brand,
                    model,
                    year,
                    priceDay,
                    desc,
                    finalImageUrl
            );

            CarService.updateCar(updated);


            response.sendRedirect(request.getContextPath() + "/car-details?car=" + carId);

        } catch (NumberFormatException nfe) {
            request.setAttribute("errorMessage",
                    "Invalid numeric field: " + nfe.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);

        } catch (SQLException sqlEx) {
            request.setAttribute("errorMessage",
                    "Database error: " + sqlEx.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
