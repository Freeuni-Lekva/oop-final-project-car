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

    public int getUserId() {
        return user_id;
    }

    public int getCarId() {
        return car_id;
    }

    public Date getStartDate() {
        return start_date;
    }

    public Date getEndDate() {
        return end_date;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", car_id=" + car_id +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                '}';
    }
}
