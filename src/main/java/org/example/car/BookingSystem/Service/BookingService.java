package org.example.car.BookingSystem.Service;

import org.example.car.BookingRequest;
import org.example.car.BookingSystem.Repository.BookingRepository;
import org.example.car.BookingSystem.Repository.DBConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
