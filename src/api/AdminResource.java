package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

public class AdminResource {

    private static AdminResource instance;
    private CustomerService customerService;
    private ReservationService reservationService;

    // provide static reference
    private AdminResource(){}

    public static AdminResource getInstance() {
        if (instance == null) {
            instance = new AdminResource();
        }
        return instance;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(IRoom room) {
        reservationService.addRoom(room);
    }

}
