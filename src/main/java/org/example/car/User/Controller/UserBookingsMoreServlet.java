package org.example.car.User.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.car.BookingSystem.BookingDisplay;
import org.example.car.BookingSystem.Service.BookingService;
import org.example.car.User.Model.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/userBookingsFull")
public class UserBookingsMoreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("1");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        String type = req.getParameter("type"); // past current future

        try {
            Map<String, List<BookingDisplay>> categorized = BookingService.categorizeBookings(user.getId());
            List<BookingDisplay> list = categorized.get(type);

            req.setAttribute("bookings", list);
            req.setAttribute("type", type);
            req.setAttribute("user", user);
            req.getRequestDispatcher("/userBookingList.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500);
        }
    }


}
