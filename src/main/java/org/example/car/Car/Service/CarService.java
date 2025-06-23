package org.example.car.Car.Service;
import org.example.car.Car.Model.Car;
import org.example.car.Car.Repository.CarRepository;

import java.sql.SQLException;
import java.util.List;


public class CarService {
    private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.getAllCars();
    }
    public void addCar(Car car) throws SQLException {
        carRepository.addCar(car);
    }
    public Car getCar(int id) throws SQLException {
        return carRepository.getCarById(id);
    }
    public void deleteCar(int id) throws SQLException {
        carRepository.deleteCar(id);
    }

}
