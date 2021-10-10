package model;

import java.util.regex.Pattern;

public class Customer {

    private String firstName;
    private String lastName;
    private String email;

    public Customer(String firstName, String lastName, String email) {

        String emailRegex = "^(.+)@(.+)\\.com$";
        Pattern pattern = Pattern.compile(emailRegex);

        // check if email format matches "domain@email.com"
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid format. Valid format for email: 'domain@email.com', please try again.");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

    }

}
