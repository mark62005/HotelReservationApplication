package model;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Reservation {

    private final long id;
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

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

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id &&
                Objects.equals(customer, that.customer) &&
                Objects.equals(room, that.room) &&
                Objects.equals(checkInDate, that.checkInDate) &&
                Objects.equals(checkOutDate, that.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, room, checkInDate, checkOutDate);
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
