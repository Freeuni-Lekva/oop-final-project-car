package org.example.car.BookingSystem.Controller;

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

@WebServlet("/cancelBooking")
public class DeleteBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Authentication/jsp/login.jsp?msg=Please+login+first");
            return;
        }

        try {
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));

            if (user.is_admin()) {
                BookingService.deleteBooking(bookingId);
            } else {
                Map<String, List<BookingDisplay>> categorizedBookings =
                        BookingService.categorizeBookings(user.getId());

                List<BookingDisplay> futureBookings = categorizedBookings.get("future");
                boolean isFutureBooking = false;

                if (futureBookings != null) {
                    for (BookingDisplay booking : futureBookings) {
                        if (booking.getBooking().getId() == bookingId) {
                            isFutureBooking = true;
                            break;
                        }
                    }
                }

                if (isFutureBooking) {
                    BookingService.deleteBooking(bookingId);
                } else {
                    request.setAttribute("errorMessage", "You can only cancel future bookings.");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }
            }

            if (user.is_admin()) {
                response.sendRedirect(request.getContextPath() + "/admin/bookings");
            } else {
                response.sendRedirect(request.getContextPath() + "/userProfile");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid booking ID.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error cancelling booking: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}

