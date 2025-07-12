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

@WebServlet("/addCar")
@MultipartConfig(
        maxFileSize      = 10 * 1024 * 1024,
        maxRequestSize   = 12 * 1024 * 1024
)
public class AdminAddCarServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "images/cars";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


        User u = (User) req.getSession().getAttribute("user");
        if (u == null || !u.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/Authentication/jsp/login.jsp?msg=Access+denied");
            return;
        }

        req.getRequestDispatcher("/Admin/JSP/addCarForm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User u = (User) req.getSession().getAttribute("user");
        if (u == null || !u.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/Authentication/jsp/login.jsp?msg=Access+denied");
            return;
        }

        try {
            String brand       = req.getParameter("brand");
            String model       = req.getParameter("model");
            int    year        = Integer.parseInt(req.getParameter("year"));
            double priceDay    = Double.parseDouble(req.getParameter("price_per_day"));
            String description = req.getParameter("description");


            Part filePart = req.getPart("image");
            String imageUrl;

            if (filePart != null && filePart.getSize() > 0) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                Path uploadPath = Paths.get(getServletContext().getRealPath("/") + UPLOAD_DIR);
                Files.createDirectories(uploadPath);
                Path filePath = uploadPath.resolve(fileName);
                filePart.write(filePath.toString());


                imageUrl = req.getContextPath() + "/" + UPLOAD_DIR + "/" + fileName;
            } else {
                imageUrl = req.getParameter("image_url_fallback");
            }

            Car newCar = new Car(0, brand, model, year, priceDay, description, imageUrl);
            CarService.addCar(newCar);

            resp.sendRedirect(req.getContextPath() + "/HPcontroller?msg=car+added");
        } catch (Exception ex) {
            req.setAttribute("errorMessage", "Error adding car: " + ex.getMessage());
            req.getRequestDispatcher("/Admin/JSP/addCarForm.jsp").forward(req, resp);
        }
    }
}
