package org.example.car.User.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.car.Booking;
import org.example.car.BookingSystem.Repository.BookingRepository;
import org.example.car.BookingSystem.Service.BookingService;
import org.example.car.Review;
import org.example.car.User.Model.User;
import org.example.car.ReviewRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/userProfile")
public class UserProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }



}
