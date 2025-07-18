package org.example.car.Car.Repository;

import org.example.car.Car.Model.Car;
import org.example.car.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {

    public static List<Car> getSortedCars(String sortBy , String order){
      
        List<Car> cars = new ArrayList<Car>();

        if ("year".equalsIgnoreCase(sortBy)) {
            sortBy = "`year`";
        }

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

                System.out.println(car.toString());
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
    public static void addCar(Car car) throws SQLException {
        String query = "INSERT INTO cars (id, brand, model, `year`, price_per_day, description, image_url) VALUES (?,?,?,?,?,?,?)";
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

    public static boolean deleteCar(int carId) throws SQLException {
        String sql = "DELETE FROM cars WHERE id = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, carId);

            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }


    public static boolean updateCar(Car car) throws SQLException {
        String sql = """
            UPDATE cars
               SET brand         = ?,
                   model         = ?,
                   `year`        = ?,
                   price_per_day = ?,
                   description   = ?,
                   image_url     = ?
             WHERE id = ?""";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, car.getBrand());
            ps.setString(2, car.getModel());
            ps.setInt   (3, car.getYear());
            ps.setDouble(4, car.getPrice_per_day());
            ps.setString(5, car.getDescription());
            ps.setString(6, car.getImage_url());
            ps.setInt   (7, car.getId());

            int rows = ps.executeUpdate();
            return rows == 1;
        }
    }

    public static List<Car> getCarsFilter(double from, double to, String brand) {
        return getCarsFilter(from, to, brand, "", "");
    }

    public static List<Car> getCarsFilter(double from, double to, String brand, String startDate, String endDate) {
        List<Car> result = new ArrayList<>();
        
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM cars WHERE price_per_day >= ? AND price_per_day <= ? AND brand LIKE ?");
        List<Object> params = new ArrayList<>();
        
        params.add(from);
        params.add(to);
        params.add("%" + brand + "%");
        
        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
            queryBuilder.append(" AND id NOT IN (SELECT DISTINCT car_id FROM bookings WHERE (start_date <= ? AND end_date >= ?) OR (start_date <= ? AND end_date >= ?) OR (start_date >= ? AND end_date <= ?))");
            params.add(endDate);
            params.add(startDate);
            params.add(endDate);
            params.add(startDate);
            params.add(startDate);
            params.add(endDate);
        }

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(queryBuilder.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Car car = new Car(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("price_per_day"),
                        rs.getString("description"),
                        rs.getString("image_url")
                );
                result.add(car);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
