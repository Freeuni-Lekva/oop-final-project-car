package org.example.car;

import java.sql.Date;

public class Booking {
    private int id;
    private int user_id;
    private int car_id;
    private Date start_date;
    private Date end_date;

    public Booking(int id, int user_id, int car_id, Date start_date, Date end_date) {
        this.id = id;
        this.user_id = user_id;
        this.car_id = car_id;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getCar_id() {
        return car_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }


}
