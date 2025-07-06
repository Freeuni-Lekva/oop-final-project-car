package org.example.car.Car.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import org.example.car.Car.Model.Car;
import org.example.car.Car.Repository.CarRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/cars")
public class CarController extends HttpServlet {
    private CarRepository carRepository;

    @Override
    public void init() throws ServletException {
        try {
            carRepository = new CarRepository();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        if(id != null) {
            HttpSession session = req.getSession();
            if(session != null && session.getAttribute("user") != null ) {
                try {
                    Car car = carRepository.getCarById(Integer.parseInt(id));

                    if (car != null) {
                        session.setAttribute("car", car);
                        req.getRequestDispatcher("carDetails.jsp").forward(req, resp);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


            }else{
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
            }
        }else{
            List<Car> cars = carRepository.getAllCars();
            req.setAttribute("cars", cars);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        int year = Integer.parseInt(req.getParameter("year"));
        double pricePerDay = Double.parseDouble(req.getParameter("price_per_day"));
        String description = req.getParameter("description");
        String imageUrl = req.getParameter("image_url");

        Car car = new Car();
        car.setBrand(brand);
        car.setModel(model);
        car.setYear(year);
        car.setPrice_per_day(pricePerDay);
        car.setDescription(description);
        car.setImage_url(imageUrl);

        try {
            carRepository.addCar(car);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        resp.sendRedirect(req.getContextPath() + "/cars");
    }
}
