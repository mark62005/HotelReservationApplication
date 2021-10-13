package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {

    private final String firstName;
    private final String lastName;
    private final String email;

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

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(firstName, customer.firstName) &&
                Objects.equals(lastName, customer.lastName) &&
                Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }

    @Override
    public String toString() {
        return "Customer{ " +
                "First name: '" + firstName + '\'' +
                ", Last name: '" + lastName + '\'' +
                ", Email: '" + email + '\'' +
                " }";
    }

}
