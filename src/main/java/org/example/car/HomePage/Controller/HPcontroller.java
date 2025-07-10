package org.example.car.HomePage.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.car.Car.Model.Car;
import org.example.car.HomePage.Service.HPservice;
import org.example.car.User.Model.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/HPcontroller")
public class HPcontroller extends HttpServlet {
    HPservice service;

    @Override
    public void init() {
        service = new HPservice();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
       // User user = (User) session.getAttribute("user");


        //request.setAttribute("user", userId);

        List<Car> allCars = service.getAllCars();
        request.setAttribute("cars", allCars);
        request.getRequestDispatcher("HomePage/JSP/homePage.jsp").forward(request, response);
    }
}
