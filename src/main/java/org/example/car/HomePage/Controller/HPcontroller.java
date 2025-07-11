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


        String fromStr = request.getParameter("priceFrom");
        String toStr = request.getParameter("priceTo");

            double from = 0;
            if(fromStr != null && !fromStr.isEmpty()){
                try {
                    from = Double.parseDouble(fromStr);
                }
                catch(NumberFormatException e){
                    from = 0;
                }
            }

            double to = Double.MAX_VALUE;
            if(toStr != null && !toStr.isEmpty()){
                try {
                    to = Double.parseDouble(toStr);
                }
                catch(NumberFormatException e){
                    to = Double.MAX_VALUE;
                }
            }

        List<Car> allCars = HPservice.getCarsFilter(from, to);
        request.setAttribute("cars", allCars);
        request.getRequestDispatcher("HomePage/JSP/homePage.jsp").forward(request, response);
    }
}
