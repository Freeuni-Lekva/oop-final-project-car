package org.example.car.BookingSystem.Service;

import org.example.car.BookingSystem.Booking;
import org.example.car.BookingSystem.BookingDisplay;
import org.example.car.BookingSystem.BookingRequest;
import org.example.car.BookingSystem.Repository.BookingRepository;
import org.example.car.Car.Model.Car;
import org.example.car.Car.Repository.CarRepository;
import org.example.car.Car.Service.CarService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(){
        this.bookingRepository = new BookingRepository();
    }

    public boolean bookCar(BookingRequest bookingRequest){


        if(!bookingRepository.isCarAvaliable(bookingRequest)){
            System.out.println("car not avaliable");
        }

        else{

            if(bookingRepository.insertBooking(bookingRequest)){
                System.out.println("car booked");
                return true;
            }

            else{
                System.out.println("failed booking");
            }
        }

        return false;
    }

    public static Map<String, List<BookingDisplay>> categorizeBookings(int userId) throws SQLException {
        Map<String, List<Booking>> m = BookingRepository.categorizeBookings(userId);
        Map<String, List<BookingDisplay>> result = new HashMap<>();
        List<Booking> future = m.get("future");
        List<Booking> past = m.get("past");
        List<Booking> current = m.get("current");

        List<BookingDisplay> newFuture = new ArrayList<>();
        List<BookingDisplay> newPast = new ArrayList<>();
        List<BookingDisplay> newCurrent = new ArrayList<>();

        for (Booking b : future) {
            Car car = CarService.getCar(b.getCarId());
            newFuture.add(new BookingDisplay(b, car));
        }
        for (Booking b : past) {
            Car car = CarService.getCar(b.getCarId());
            newPast.add(new BookingDisplay(b, car));
        }
        for (Booking b : current) {
            Car car = CarService.getCar(b.getCarId());
            newCurrent.add(new BookingDisplay(b, car));
        }
        result.put("past", newPast);
        result.put("current", newCurrent);
        result.put("future", newFuture);

        return result;

    }
}
