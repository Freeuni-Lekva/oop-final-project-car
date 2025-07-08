package org.example.car.Car.Repository;

import org.example.car.Car.Model.Car;
import org.example.car.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {



    public List<Car> getSortedCars(String sortBy , String order){
        List<Car> cars = new ArrayList<Car>();

        String sql = "select * from cars order by " + sortBy+" "+order;
        try{
            Connection connection = DBConnector.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                Car car = new Car();
                car.setId(resultSet.getInt("id"));
                car.setBrand(resultSet.getString("brand"));
                car.setModel(resultSet.getString("model"));
                car.setYear(resultSet.getInt("year"));
                car.setPrice_per_day(resultSet.getInt("price_per_day"));
                car.setDescription(resultSet.getString("description"));
                car.setImage_url(resultSet.getString("image_url"));
                cars.add(car);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cars;
    }


    public static List<Car> getAllCars() {
        List<Car> cars = new ArrayList<Car>();
        try{
            Connection connection = DBConnector.getConnection();
            String query = "SELECT * FROM cars";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Car car = new Car();
                car.setId(resultSet.getInt("id"));
                car.setBrand(resultSet.getString("brand"));
                car.setModel(resultSet.getString("model"));
                car.setYear(resultSet.getInt("year"));
                car.setPrice_per_day(resultSet.getInt("price_per_day"));
                car.setDescription(resultSet.getString("description"));
                car.setImage_url(resultSet.getString("image_url"));
                cars.add(car);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cars;
    }

    public static Car getCarById(int id) throws SQLException {
        Car car = null;
        String query = "SELECT * FROM cars WHERE id = " + id;
        Connection connection = DBConnector.getConnection();
        ResultSet rs = connection.prepareStatement(query).executeQuery();
        if(rs.next()) {
            car = new Car();
            car.setId(rs.getInt("id"));
            car.setBrand(rs.getString("brand"));
            car.setModel(rs.getString("model"));
            car.setYear(rs.getInt("year"));
            car.setPrice_per_day(rs.getInt("price_per_day"));
            car.setDescription(rs.getString("description"));
            car.setImage_url(rs.getString("image_url"));
        }
        return car;
    }
    public void addCar(Car car) throws SQLException {
        String query = "INSERT INTO cars (id, brand, model, year, price_per_day, description, image_url) VALUES (?,?,?,?,?,?,?)";
        Connection connection = DBConnector.getConnection();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, car.getId());
        ps.setString(2, car.getBrand());
        ps.setString(3, car.getModel());
        ps.setInt(4, car.getYear());
        ps.setDouble(5, car.getPrice_per_day());
        ps.setString(6, car.getDescription());
        ps.setString(7, car.getImage_url());
        ps.executeUpdate();
    }

    public void deleteCar(int id) throws SQLException {
        String query = "DELETE FROM cars WHERE id = " + id;
        Connection connection = DBConnector.getConnection();
        connection.prepareStatement(query).executeQuery();
    }
}
