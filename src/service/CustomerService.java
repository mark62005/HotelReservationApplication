package service;

import model.Customer;

import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private final Map<String, Customer> customers = new HashMap<>();

    // Provide static reference for CustomerService
    private static CustomerService instance;

    private CustomerService(){};

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    public void addCustomer(String firstName, String lastName, String email) {

        Customer customer = new Customer(firstName, lastName, email);

        // make sure all te emails in customer map are unique
        if (getAllCustomers().containsKey(email)) {
            throw new IllegalArgumentException("Error! This email is taken, please try another one.");
        }
        getAllCustomers().put(email, customer);

    }

    public Customer getCustomer(String email) {

        return getAllCustomers().getOrDefault(email, null);

    }

    public Map<String, Customer> getAllCustomers() {
        return customers;
    }

}
