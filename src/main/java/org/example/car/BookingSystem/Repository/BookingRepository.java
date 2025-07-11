package org.example.car.BookingSystem.Repository;

import org.example.car.BookingSystem.Booking;
import org.example.car.BookingSystem.BookingRequest;
import org.example.car.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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

    public static boolean insertBooking(BookingRequest bookingRequest) {

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
  
    public static List<Booking> getBookingsByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id = ?";

        try (
            Connection conn = DBConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int car_id = rs.getInt("car_id");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");

                bookings.add(new Booking(id, userId, car_id, start_date, end_date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    public static Map<String, List<Booking>> categorizeBookings(int userId) {
        Map<String, List<Booking>> map = new HashMap<>();
        map.put("past", new ArrayList<>());
        map.put("current", new ArrayList<>());
        map.put("future", new ArrayList<>());

        String sql = "SELECT * FROM bookings WHERE user_id = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            LocalDate today = LocalDate.now();

            while (rs.next()) {
                int id = rs.getInt("id");
                int car_id = rs.getInt("car_id");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");

                Booking booking = new Booking(id, userId, car_id, start_date, end_date);

                LocalDate start = start_date.toLocalDate();
                LocalDate end = end_date.toLocalDate();

                if (end.isBefore(today)) {
                    map.get("past").add(booking);
                } else if ((start.isEqual(today) || start.isBefore(today)) && end.isAfter(today)) {
                    map.get("current").add(booking);
                } else {
                    map.get("future").add(booking);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return map;
    }

    public static List<Booking> getBookings(){
        List<Booking> bookings = new ArrayList<>();
        String sql = "select * from bookings";

        try(
                Connection conn = DBConnector.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ){
            while(rs.next()){
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int car_id = rs.getInt("car_id");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");

                Booking booking = new Booking(id, user_id, car_id, start_date, end_date);
                bookings.add(booking);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return bookings;
    }

    public static void deleteBooking(int bookindId) {
        String sql = "DELETE FROM bookings WHERE id = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookindId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Booking getBookingById(int bookingId) {
        Booking booking = null;
        String sql = "SELECT * FROM bookings WHERE id = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookingId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    int userId = rs.getInt("user_id");
                    int carId = rs.getInt("car_id");
                    Date startDate = rs.getDate("start_date");
                    Date endDate = rs.getDate("end_date");

                    booking = new Booking(id, userId, carId, startDate, endDate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booking;
    }

}
