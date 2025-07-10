package org.example.car.BookingSystem.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.car.BookingSystem.BookingRequest;
import org.example.car.BookingSystem.Service.BookingService;
import org.example.car.Car.Model.Car;
import org.example.car.Car.Repository.CarRepository;
import org.example.car.User.Model.User;
import org.example.car.User.Repository.UserRepository;

import java.io.IOException;

@WebServlet("/BookingController")
public class BookingController extends HttpServlet {
    private BookingService bookingService;

    @Override
    public void init(){
        bookingService = new BookingService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("movida");

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Authentication/jsp/login.jsp?msg=Please+login+first");
            return;
        }

        try{
            BookingRequest bookingRequest = BookingRequest.getBookingRequest(request);
            boolean booked = bookingService.bookCar(bookingRequest);

            User u = UserRepository.getUserById(bookingRequest.getUserId());
            String name = u.getFull_name();
            request.setAttribute("userName", name);
            Car c = CarRepository.getCarById(bookingRequest.getCarId());
            String carName = c.getBrand() + " " + c.getModel();

            request.setAttribute("carId", c.getId());
            request.setAttribute("carName", carName);
            request.setAttribute("carDetails", c.getDescription());
            request.setAttribute("carPic", c.getImage_url());
            request.setAttribute("startDate", bookingRequest.getStartDate());
            request.setAttribute("endDate", bookingRequest.getEndDate());

            if(booked){
                System.out.println("booked - FINAL");
                request.getRequestDispatcher("Booking/JSP/bookingSuccess.jsp").forward(request, response);
            }

            else{
                System.out.println("failed - FINAL");
                request.getRequestDispatcher("Booking/JSP/bookingFailed.jsp").forward(request, response);
            }
            
        }

        catch(Exception e){
            e.printStackTrace();
        }
    }
}
