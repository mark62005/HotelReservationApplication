package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class HotelResource {

    private static HotelResource instance;
    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();

    // provide static reference
    private HotelResource(){}

    public static HotelResource getInstance() {
        if (instance == null) {
            instance = new HotelResource();
        }
        return instance;
    }

    public void addCustomer(String firstName, String lastName, String email) {
        customerService.addCustomer(firstName, lastName, email);
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getRoom(roomNumber);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    public List<IRoom> findAvailableRooms(Date checkInDate, Date checkOutDate) {
        return reservationService.findAvailableRooms(checkInDate, checkOutDate);
    }

    public void recommendAlternatives(Date checkInDate, Date checkOutDate) {
        reservationService.recommendAlternatives(checkInDate, checkOutDate);
    }

    public Map<Long, Reservation> getReservationsOfACustomer(String email) {
        return reservationService.getReservationsOfACustomer(email);
    }

}
