package org.example.car.AIAssistant;

import org.example.car.Booking;
import org.example.car.Car.Model.Car;
import org.example.car.DBConnector;
import org.example.car.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetDatabase {
    public List<Car> getCars(){
        List<Car> cars = new ArrayList<>();
        String sql = "select * from cars";

        try(
                Connection conn = DBConnector.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ){
            while(rs.next()){
                int id = rs.getInt("id");
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                int year = rs.getInt("year");
                Double price = rs.getDouble("price_per_day");
                String desc = rs.getString("description");
                String image_url = rs.getString("image_url");

                Car car = new Car(id, brand, model, year, price, desc, image_url);
                cars.add(car);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return cars;
    }

    public List<Booking> getBookings(){
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

    public List<Review> getReviews(){
        List<Review> reviews = new ArrayList<>();
        String sql = "select * from reviews";

        try(
                Connection conn = DBConnector.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ){
            while(rs.next()){
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int car_id = rs.getInt("car_id");
                int rating = rs.getInt("rating");
                String comment = rs.getString("comment");

                Review review = new Review(id, user_id, car_id, rating, comment);
                reviews.add(review);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return reviews;
    }

    public static void main(String[] args) {
        GetDatabase gd = new GetDatabase();
        List<Car> cars = gd.getCars();
        for(Car car: cars){
            System.out.println(car.toString());
        }

        List<Booking> bookings = gd.getBookings();
        for(Booking bok: bookings){
            System.out.println(bok.toString());
        }

        List<Review> reviews = gd.getReviews();
        for(Review rev: reviews){
            System.out.println(rev.toString());
        }

    }
}
