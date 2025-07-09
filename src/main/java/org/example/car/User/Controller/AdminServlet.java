package org.example.car.User.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.car.BookingSystem.Repository.BookingRepository;
import org.example.car.Car.Repository.CarRepository;
import org.example.car.Review.Repository.ReviewRepository;
import org.example.car.User.Model.User;
import org.example.car.User.Repository.UserRepository;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private UserRepository userRepo = new UserRepository();
    private ReviewRepository reviewRepo = new ReviewRepository();
    private BookingRepository bookingRepo = new BookingRepository();
    private CarRepository carRepo;

    {
        carRepo = new CarRepository();
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null) {
            resp.sendRedirect("/login.jsp");
            return;
        }
        User u = (User) session.getAttribute("user");
        if (u ==null || !u.is_admin()) {
            resp.sendRedirect("/access-denied.jsp");
            return;
        }

        req.setAttribute("users", userRepo.getAllUsers());
        req.setAttribute("reviews", reviewRepo.getReviews());
        req.setAttribute("bookings", bookingRepo.getBookings());
        req.setAttribute("cars", carRepo.getAllCars());
        req.getRequestDispatcher("/admin-dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        if (session == null) {
            resp.sendRedirect("/login.jsp");
            return;
        }

        User u = (User) session.getAttribute("user");
        if (u==null || !u.is_admin()) {
            resp.sendRedirect("/access-denied.jsp");
            return;
        }

        String action = req.getParameter("action");
        int id = Integer.parseInt(req.getParameter("id"));

        switch (action) {
            case "deleteUser":
                userRepo.deleteUser(id);
                break;
            case "deleteReview":
                reviewRepo.deleteReview(id);
                break;
            case "deleteBooking":
                bookingRepo.deleteBooking(id);
                break;
        }

        resp.sendRedirect("/admin");
    }


}
