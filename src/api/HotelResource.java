package api;

import model.Customer;
import service.CustomerService;
import service.ReservationService;

public class HotelResource {

    private static HotelResource instance;
    private CustomerService customerService;

    // provide static reference
    private HotelResource(){}

    public static HotelResource getInstance() {
        if (instance == null) {
            instance = new HotelResource();
        }
        return instance;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

}
