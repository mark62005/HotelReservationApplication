package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModelTester {
    public static void main(String[] args) throws ParseException {

        Customer customerSample = new Customer("John", "Doe", "john@email.com");
        IRoom roomSample = new Room("101", 55.0, RoomType.SINGLE);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date checkInDateSample = sdf.parse("01/10/2021");
        Date checkOutDateSample = sdf.parse("09/10/2021");

        Reservation reservationSample = new Reservation(customerSample, roomSample, checkInDateSample, checkOutDateSample);

        System.out.println(customerSample);
        System.out.println(roomSample);
        System.out.println(reservationSample);

    }
}
