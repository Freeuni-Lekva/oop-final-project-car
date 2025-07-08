package org.example.car.BookingSystem.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.car.BookingSystem.BookingRequest;
import org.example.car.BookingSystem.Service.BookingService;

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
        try{
            BookingRequest bookingRequest = BookingRequest.getBookingRequest(request);

            boolean booked = bookingService.bookCar(bookingRequest);

            if(booked){
                System.out.println("booked - FINAL");
            }

            else{
                System.out.println("failed - FINAL");
            }


        }

        catch(Exception e){
            e.printStackTrace();
        }
    }
}
