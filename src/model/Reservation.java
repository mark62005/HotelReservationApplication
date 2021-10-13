package model;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {

    private final long id;
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {

        // make sure the check-out date is after the check-in date
        if (checkInDate.after(checkOutDate) || checkInDate.equals(checkOutDate)) {
            throw new IllegalArgumentException("Invalid input! Check-out date must be after check-in date");
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.id = timestamp.getTime();

        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;

    }

    public long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public void setRoom(IRoom room) {
        this.room = room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    @Override
    public String toString() {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return "Reservation{ " +
                customer +
                ", " + room +
                ", Check-in date: " + dateFormat.format(checkInDate) +
                ", Check-out date: " + dateFormat.format(checkOutDate) +
                " }";
    }

}
