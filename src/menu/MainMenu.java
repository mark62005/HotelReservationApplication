package menu;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainMenu {

    public static HotelResource hotelResource = HotelResource.getInstance();
    public static AdminResource adminResource = AdminResource.getInstance();
    public static Scanner scanner = new Scanner(System.in);
    public static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {

        // display initial menu
        printMainMenu();

        handleMainOptions();

    }

    public static void printMainMenu() {

        String mainMenu = """
                
                Main Menu
                1. Find and reserve a room
                2. See my reservations
                3. Create an account
                4. Go to Admin Menu
                5. Exit
                Please enter your option (1 - 5):
                """;

        System.out.println(mainMenu);

    }

    public static void handleMainOptions() {

        try {

            // ask user to enter their option
            int option = Integer.parseInt(scanner.nextLine());

            // make sure user enter a number between 1 - 5
            while (option > 5 || option < 1) {
                System.out.println("Invalid input. Please enter your option again (1 - 5): ");
                option = Integer.parseInt(scanner.nextLine());
            }

            switch (option) {
                case 1 -> enterCheckInDate();
                case 2 -> seeMyReservations();
                case 3 -> createAnAccount();
                case 4 -> AdminMenu.main(null);
                case 5 -> System.exit(0);
                default -> {}
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter your option again (1 - 5): ");
            handleMainOptions();
        }

    }

    // check if the format of email input is valid
    public static boolean isEmailFormatValid(String email) {

        String emailRegex = "^(.+)@(.+)\\.com$";
        Pattern pattern = Pattern.compile(emailRegex);

        // check if email format matches "domain@email.com"
        return pattern.matcher(email).matches();

    }

    public static String handleEmailInput() {

        // ask user to enter their email
        System.out.println("Please enter your email: ");
        String emailInput = scanner.nextLine();

        // make sure the email format is valid
        while (!isEmailFormatValid(emailInput)) {
            System.out.println("Invalid format. Valid format for email: 'domain@email.com', please try again.");
            emailInput = scanner.nextLine();
        }

        // make sure the email exists in the customer map
        while (!adminResource.getAllCustomers().containsKey(emailInput)) {
            System.out.println("This email doesn't exist, please try another one.");
            emailInput = scanner.nextLine();
        }

        return emailInput;

    }

    // for option 1: Find and reserve a room
    public static void enterCheckInDate() {

        try {

            // ask user to choose a check-in date
            System.out.println("Please enter a check-in date (dd/mm/yyyy): ");
            Date checkInDateInput = dateFormat.parse(scanner.nextLine());
            System.out.println(checkInDateInput);

            findAndReserveARoom(checkInDateInput);

        // if the format of date input is invalid, start the function again
        } catch (ParseException e) {
            System.out.println("Invalid format for date. Please use the format (dd/mm/yyyy).");
            enterCheckInDate();
        }

    }

    public static void findAndReserveARoom(Date checkInDateInput) {

        try {

            // ask user to choose a check-out date
            System.out.println("Please enter a check-out date (dd/mm/yyyy): ");
            Date checkOutDateInput = dateFormat.parse(scanner.nextLine());
            System.out.println(checkOutDateInput);

            // to make sure check-out date is after check-in date
            while (checkInDateInput.after(checkOutDateInput) || checkInDateInput.equals(checkOutDateInput)) {

                System.out.println("Invalid input! Check-out date must be after check-in date");
                System.out.println("Please enter a check-out date: (dd/mm/yyyy)");
                checkOutDateInput = dateFormat.parse(scanner.nextLine());
                System.out.println(checkOutDateInput);

            }

            // get a list of available rooms for that date range
            List<IRoom> availableRooms = hotelResource.findAvailableRooms(checkInDateInput, checkOutDateInput);

            // if the list of available rooms is empty, recommend alternatives by adding the date range for 7
            if (availableRooms.isEmpty()) {
                hotelResource.recommendAlternatives(checkInDateInput, checkOutDateInput);
            }

            // print the list of available rooms
            System.out.println("Rooms that are available: ");
            for (int i = 0; i < availableRooms.size(); i++) {
                System.out.printf("\n%d\\. %s", i, availableRooms.get(i).getRoomNumber());
            }

            // ask user to choose a room from the list
            System.out.println("Please choose a room number from the above list: ");
            String roomNumberInput = scanner.nextLine();

            IRoom roomToReserve = hotelResource.getRoom(roomNumberInput);

            // make sure the room number is from the list
            while (!availableRooms.contains(roomToReserve)) {
                System.out.println("Invalid room number. Please choose a room number from the above list: ");
                roomNumberInput = scanner.nextLine();
                roomToReserve = hotelResource.getRoom(roomNumberInput);
            }

            // ask user to enter their email
            String emailInput = handleEmailInput();

            Customer customer = hotelResource.getCustomer(emailInput);

            // reserve the room and print the reservation
            Reservation reservation = hotelResource.reserveARoom(customer, roomToReserve, checkInDateInput, checkOutDateInput);
            System.out.println(reservation);

        } catch (ParseException e) {
            System.out.println("Invalid format for date. Please use the format (dd/mm/yyyy).");
            findAndReserveARoom(checkInDateInput);
        } catch (NullPointerException e) {
            System.out.println(e.getLocalizedMessage());
        }

    }

    // for option 2: See my reservations
    public static void seeMyReservations() {

        // ask user to enter their email
        String emailInput = handleEmailInput();

        List<Reservation> myReservations = hotelResource.getReservationsOfACustomer(emailInput);

        if (myReservations.isEmpty()) {
            System.out.println("You haven't made any reservation yet.");
        } else {

            // print out the reservation list of that customer
            System.out.println("Your reservations: ");
            for (int i = 0; i < myReservations.size(); i++) {
                System.out.println(i + ". " + myReservations.get(i));
            }

        }

    }

    // handle option 3: Create an account
    public static void createAnAccount() {

        // ask user to enter their first name
        System.out.println("Please enter your first name: ");
        String firstNameInput = scanner.nextLine().trim();

        // ask user to enter their last name
        System.out.println("Please enter your last name: ");
        String lastNameInput = scanner.nextLine().trim();

        // ask user to enter their email
        String emailInput = handleEmailInput();

        hotelResource.addCustomer(firstNameInput, lastNameInput, emailInput);
        System.out.println("Account created successfully.");

    }

}
