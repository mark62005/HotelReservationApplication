package api;

import model.Customer;
import service.CustomerService;

public class AdminResource {

    private static AdminResource instance;
    private CustomerService customerService;

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

}
