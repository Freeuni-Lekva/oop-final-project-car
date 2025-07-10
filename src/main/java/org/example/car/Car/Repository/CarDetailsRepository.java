package org.example.car.Car.Repository;

import org.example.car.Car.Model.Car;
import org.example.car.DBConnector;

import java.sql.*;


public class CarDetailsRepository {

    public static Car getCarById(int id) throws SQLException {

        Car car = null;
        String sql = "SELECT * FROM cars WHERE id = " + id;

//            System.out.println("database check");
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

//                System.out.println("database check passed");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                car = new Car();
                car.setId(rs.getInt("id"));
                car.setBrand(rs.getString("brand"));
                car.setModel(rs.getString("model"));
                car.setYear(rs.getInt("year"));
                car.setPrice_per_day(rs.getDouble("price_per_day"));
                car.setDescription(rs.getString("description"));
                car.setImage_url(rs.getString("image_url"));
            }
        }
//            System.out.println("getCarByIdCheck2");
        return car;
    }
}


