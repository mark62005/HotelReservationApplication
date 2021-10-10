package service;

import model.Customer;

import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private Map<String, Customer> customers = new HashMap<>();

    public void addCustomer(String firstName, String lastName, String email) {

        Customer customer = new Customer(firstName, lastName, email);

        if (customers.containsKey(email)) {
            throw new IllegalArgumentException("Error! This email is taken, please try another one.");
        }
        customers.put(email, customer);

    }

    public Customer getCustomer(String email) {

        return customers.getOrDefault(email, null);

    }

}
