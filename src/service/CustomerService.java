package service;

import model.Customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomerService {

    private final Map<String, Customer> customers = new HashMap<>();

    // Provide static reference for CustomerService
    private static CustomerService instance;

    private CustomerService(){}

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

    public void removeCustomer(String email) {
        customers.remove(email);
    }

    public void clearCustomerSamples() {

        // find the list of emails that contain "test."
        List<String> emails = customers.values().stream()
                .map(Customer::getEmail)
                .filter(email -> email.contains("test."))
                .collect(Collectors.toList());
        // remove them from the customer list
        for (String email : emails) {
            removeCustomer(email);
        }

    }

}
