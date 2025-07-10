package org.example.car.BookingSystem.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.car.BookingSystem.Booking;
import org.example.car.BookingSystem.Repository.BookingRepository;
import org.example.car.Car.Model.Car;
import org.example.car.Car.Repository.CarRepository;
import org.example.car.User.Model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/BookingRequestController")
public class BookingRequestController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Authentication/jsp/login.jsp?msg=Please+login+first");
        }

        else{
            int carId = Integer.parseInt(request.getParameter("carId"));

            Car c = null;
            try {
                c = CarRepository.getCarById(carId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            List<Booking> bookings = BookingRepository.getCarBookings(carId);
            List<String> bookedDates = new ArrayList<>();

            for (Booking b : bookings) {
                LocalDate start = b.getStartDate().toLocalDate();
                LocalDate end = b.getEndDate().toLocalDate();
                while (!start.isAfter(end)) {
                    bookedDates.add(start.toString());
                    start = start.plusDays(1);
                }
            }

            request.setAttribute("car", c);
            request.setAttribute("bookedDates", bookedDates);
            request.setAttribute("user", user);

            request.getRequestDispatcher("/Booking/JSP/bookingRequest.jsp").forward(request, response);
        }
    }
}
