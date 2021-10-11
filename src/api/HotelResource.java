package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

public class HotelResource {

    private static HotelResource instance;
    private CustomerService customerService;
    private ReservationService reservationService;

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

}
