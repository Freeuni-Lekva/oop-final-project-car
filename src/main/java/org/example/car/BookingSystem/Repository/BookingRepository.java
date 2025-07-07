package org.example.car.BookingSystem.Repository;

import org.example.car.Booking;
import org.example.car.BookingRequest;
import org.example.car.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository {
    public boolean isCarAvaliable(BookingRequest bookingRequest) {

        int carId = bookingRequest.getCarId();
        Date startDate = bookingRequest.getStartDate();
        Date endDate = bookingRequest.getEndDate();

        String sql = "select count(*) from bookings " +
                "where car_id = ? and not (end_date < ? or start_date > ?)";


        try(
                Connection conn = DBConnector.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {

            ps.setInt(1, carId);
            ps.setDate(2, startDate);
            ps.setDate(3, endDate);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                if(rs.getInt(1) == 0)
                    return true;
            }
        }

        catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean insertBooking(BookingRequest bookingRequest) {

        int userId = bookingRequest.getUserId();
        int carId = bookingRequest.getCarId();
        Date startDate = bookingRequest.getStartDate();
        Date endDate = bookingRequest.getEndDate();

        String sql = "insert into bookings (user_id, car_id, start_date, end_date) values (?, ?, ?, ?)";

        try(
                Connection conn = DBConnector.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {

            ps.setInt(1, userId);
            ps.setInt(2, carId);
            ps.setDate(3, startDate);
            ps.setDate(4, endDate);

            ps.executeUpdate();
            return true;

        }

        catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public static List<Booking> getCarBookings(int carId){
        List<Booking> bookings = new ArrayList<>();
        String sql = "select * from bookings where car_id = ?;";

        try(
                Connection conn = DBConnector.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setInt(1, carId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int user_id = rs.getInt("user_id");
                    int car_id = rs.getInt("car_id");
                    Date start_date = rs.getDate("start_date");
                    Date end_date = rs.getDate("end_date");

                    Booking booking = new Booking(id, user_id, car_id, start_date, end_date);
                    bookings.add(booking);
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return bookings;
    }

}
