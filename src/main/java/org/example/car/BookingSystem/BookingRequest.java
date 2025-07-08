package org.example.car.BookingSystem;

import jakarta.servlet.http.HttpServletRequest;

import java.sql.Date;

public class BookingRequest {
    private int userId;
    private int car;
    private Date startDate;
    private Date EndDate;

    public BookingRequest(int userId, int bookingId, Date startDate, Date endDate) {
        this.userId = userId;
        this.car = bookingId;
        this.startDate = startDate;
        this.EndDate = endDate;
    }

    public int getUserId() {
        return userId;
    }

    public int getCarId() {
        return car;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public static BookingRequest getBookingRequest(HttpServletRequest request) {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            int carId = Integer.parseInt(request.getParameter("carId"));
            Date startDate = Date.valueOf(request.getParameter("startDate"));
            Date endDate = Date.valueOf(request.getParameter("endDate"));

            return new BookingRequest(userId, carId, startDate, endDate);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
