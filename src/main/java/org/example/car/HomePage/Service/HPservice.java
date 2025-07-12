package org.example.car.HomePage.Service;

import org.example.car.Car.Model.Car;
import org.example.car.Car.Repository.CarRepository;
import org.example.car.Car.Service.CarService;

import java.util.List;

public class HPservice {
    CarRepository carRepo;

    public HPservice(){
        carRepo = new CarRepository();
    }

    public List<Car> getAllCars(){
        return carRepo.getAllCars();
    }

    public static List<Car> getCarsFilter(double from, double to, String brand){
        return CarService.getCarsFilter(from, to, brand);
    }

}
