package service;

import model.Customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
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

        String emailRegex = "^(.+)@(.+)\\.com$";
        Pattern pattern = Pattern.compile(emailRegex);

        // check if email format matches "domain@email.com"
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid format. Valid format for email: 'domain@email.com', please try again.");
        }

        // make sure all te emails in customer map are unique
        if (customers.containsKey(email)) {
            throw new IllegalArgumentException("Error! This email is taken, please try another one.");
        }

        Customer customer = new Customer(firstName, lastName, email);
        customers.put(email, customer);

    }

    public Customer getCustomer(String email) {

        return customers.getOrDefault(email, null);

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
