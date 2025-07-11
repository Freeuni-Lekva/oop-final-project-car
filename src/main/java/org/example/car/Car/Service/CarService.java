package org.example.car.Car.Service;
import org.example.car.Car.Model.Car;
import org.example.car.Car.Repository.CarDetailsRepository;
import org.example.car.Car.Repository.CarRepository;

import java.sql.SQLException;
import java.util.List;


public class CarService {

    public static List<Car> getAllCars() {
        return CarRepository.getAllCars();
    }
    public static void addCar(Car car) throws SQLException {
        CarRepository.addCar(car);
    }
    public static Car getCar(int id) throws SQLException {
        return CarRepository.getCarById(id);
    }
    public static void deleteCar(int id) throws SQLException {
        CarRepository.deleteCar(id);
    }

    public static Car getCarById(int id) throws SQLException {
       return CarDetailsRepository.getCarById(id);
    }

    public static List<Car> getCarsFilter(double from, double to, String brand){
        return CarRepository.getCarsFilter(from, to, brand);
    }

    public static void updateCar(Car car) throws SQLException {
        if (car.getPrice_per_day() < 0) {
            throw new IllegalArgumentException("Price per day cannot be negative");
        }

        boolean ok = CarRepository.updateCar(car);
        if (!ok) {
            throw new SQLException("Car update failed or car not found (id=" + car.getId() + ")");
        }
    }
}
