package menu;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class MainMenu {

    public static HotelResource hotelResource = HotelResource.getInstance();
    public static AdminResource adminResource = AdminResource.getInstance();
    public static Scanner scanner = new Scanner(System.in);
    public static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {

        boolean keepRunning = true;

        if (adminResource.getAllRooms().isEmpty()) {
            AdminMenu.addTestData();
        }

        while (keepRunning) {

            // display initial menu
            printMainMenu();

            try {

                // ask user to enter their option
                int option = Integer.parseInt(scanner.nextLine());

                // make sure user enter a number between 1 - 5
                while (option > 5 || option < 1) {
                    System.out.println("Invalid input. Please enter your option again (1 - 5): ");
                    option = Integer.parseInt(scanner.nextLine());
                }

                switch (option) {
                    case 1 -> findAndReserveARoom();
                    case 2 -> seeMyReservations();
                    case 3 -> createAnAccount();
                    case 4 -> {
                        AdminMenu.main(null);
                        // break the while loop
                        keepRunning = false;
                    }
                    case 5 -> System.exit(0);
                    default -> {}
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter your option again (1 - 5): ");
                MainMenu.main(null);
            }

        }

    }

    public static void printMainMenu() {

        String mainMenu = """
                
                Main Menu
                1. Find and reserve a room
                2. See my reservations
                3. Create an account
                4. Go to Admin Menu
                5. Exit
                
                Please enter your option (1 - 5):\040""";

        System.out.println(mainMenu);

    }

    // check if the format of email input is valid
    public static boolean isEmailFormatValid(String email) {

        String emailRegex = "^(.+)@(.+)\\.com$";
        Pattern pattern = Pattern.compile(emailRegex);

        // check if email format matches "domain@email.com"
        return pattern.matcher(email).matches();

    }

    public static String handleEmailInput(String action) {

        // ask user to enter their email
        System.out.println("Please enter your email: ");
        String emailInput = scanner.nextLine();

        // make sure the email format is valid
        while (!isEmailFormatValid(emailInput)) {
            System.out.println("Invalid format. Valid format for email: 'domain@email.com', please try again.");
            System.out.println("Please enter your email: ");
            emailInput = scanner.nextLine();
        }

        if (!action.equals("create account")) {
            // make sure the email exists in the customer map
            while (!adminResource.getAllCustomers().containsKey(emailInput)) {
                System.out.println("This email doesn't exist, please try another one.");
                System.out.println("Please enter your email: ");
                emailInput = scanner.nextLine();
            }
            return emailInput;
        }
        return emailInput;

    }

    // for option 1: Find and reserve a room
    public static void findAndReserveARoom() {

        try {

            // ask user to choose a check-in date
            System.out.println("Please enter a check-in date (dd/mm/yyyy): ");
            Date checkInDateInput = handleDateInput();

            // ask user to choose a check-out date
            System.out.println("Please enter a check-out date (dd/mm/yyyy): ");
            Date checkOutDateInput = handleDateInput();

            // to make sure check-out date is after check-in date
            while (Objects.requireNonNull(checkInDateInput).after(checkOutDateInput) || checkInDateInput.equals(checkOutDateInput)) {

                System.out.println("Invalid input! Check-out date must be after check-in date");
                System.out.println("Please enter a check-out date: (dd/mm/yyyy)");
                checkOutDateInput = handleDateInput();

            }

            System.out.printf("\nCheck-in date: %s, check-out date: %s", dateFormat.format(checkInDateInput), dateFormat.format(checkOutDateInput));
            System.out.println("\nConfirm this date range? (y/n)");
            // if user enters "n", ask them to enter the data range again
            if (AdminMenu.isDenied()) {
                findAndReserveARoom();
            }

            Date finalCheckInDate = checkInDateInput;
            Date finalCheckOutDate = checkOutDateInput;

            // if user enters "y"
            // get a list of available rooms for that date range
            List<IRoom> availableRooms = hotelResource.findAvailableRooms(checkInDateInput, checkOutDateInput);

            // if the list of available rooms is empty, recommend alternatives by adding the date range for 7
            if (availableRooms.isEmpty()) {

                System.out.println("Sorry, all the rooms are booked for this date range.");
                System.out.println("Looking for recommendations for you...");
                hotelResource.recommendAlternatives(checkInDateInput, checkOutDateInput);

                finalCheckInDate = hotelResource.getAlternativeCheckInDate();
                finalCheckOutDate = hotelResource.getAlternativeCheckOutDate();

            }

            // print the list of available rooms
            System.out.printf("\nRooms that are available for %s - %s: ",
                    dateFormat.format(finalCheckInDate), dateFormat.format(finalCheckOutDate));
            for (int i = 0; i < availableRooms.size(); i++) {
                System.out.printf("\n%d. %s", i + 1, availableRooms.get(i).getRoomNumber());
                if (i == availableRooms.size() - 1) {
                    System.out.println();
                }
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
            String emailInput = handleEmailInput("reserve a room");

            // ask user to confirm the info before making the reservation
            System.out.printf(
                    "\nCheck-in date: %s, Check-out date: %s, Room number: %s, Email: %s",
                    dateFormat.format(finalCheckInDate), dateFormat.format(finalCheckOutDate),
                    roomNumberInput, emailInput
            );
            System.out.println("\nConfirm the above information? (y/n)");
            // if user enters "n", ask them to enter the information again
            if (AdminMenu.isDenied()) {
                findAndReserveARoom();
            } else {

                // if user enters "y", reserve the room and print the reservation
                Customer customer = hotelResource.getCustomer(emailInput);
                Reservation reservation = hotelResource.reserveARoom(customer, roomToReserve, finalCheckInDate, finalCheckOutDate);
                System.out.println(reservation);

            }

        } catch (NullPointerException e) {
            System.out.println(e.getLocalizedMessage());
            MainMenu.main(null);
        }

    }

    // for option 2: See my reservations
    public static void seeMyReservations() {

        while (true) {
            try {

                System.out.println("Have you created an account yet? (y/n)");
                // if user enters "n", ask if they want to create an account
                if (AdminMenu.isDenied()) {

                    System.out.println("Would you like to create an account first? (y/n)");
                    // if user enters "n", back to main menu and break the loop
                    if (AdminMenu.isDenied()) {
                        MainMenu.main(null);
                        break;
                    }
                    // if user enters "y", go to create an account
                    createAnAccount();

                }

                // if user enters "y", ask user to enter their email
                String emailInput = handleEmailInput("see my reservations");

                Map<Long, Reservation> myReservations = hotelResource.getReservationsOfACustomer(emailInput);

                if (myReservations.isEmpty()) {
                    System.out.println("You haven't made any reservation yet.");
                } else {

                    int i = 1;
                    // print out the reservation list of that customer
                    System.out.print("\nYour reservations: ");
                    for (Reservation reservation : myReservations.values()) {
                        System.out.printf("\n%d. %s", i, reservation);
                        i++;

                        if (i == myReservations.size() + 1) {
                            System.out.println();
                        }
                    }
                    System.out.println();

                }

                System.out.println("Back to Main Menu? (y/n)");
                // if user enters "n", call this function again
                if (AdminMenu.isDenied()) {
                    seeMyReservations();
                } else {
                    // if user enters "y", return to main menu and break the loop
                    MainMenu.main(null);
                    break;
                }

            } catch (NullPointerException e) {

                System.out.println("You haven't made any reservation yet.");
                System.out.println("Back to Main Menu? (y/n)");
                // if user enters "n", call this function again
                if (AdminMenu.isDenied()) {
                    seeMyReservations();
                } else {
                    // if user enters "y", return to main menu and break the loop
                    MainMenu.main(null);
                    break;
                }

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
        String emailInput = handleEmailInput("create account");

        // ask user to confirm their inputs
        System.out.printf("\nFirst name: %s, Last name: %s, Email: %s", firstNameInput, lastNameInput, emailInput);
        System.out.println("\nConfirm? (y/n)");
        // if user enters "n", ask them to enter the inputs again
        if (AdminMenu.isDenied()) {
            createAnAccount();
        }
        // if user enters "y", create an account for them
        hotelResource.addCustomer(firstNameInput, lastNameInput, emailInput);
        System.out.println("Account created successfully.");

    }

    // source: https://stackoverflow.com/questions/226910/how-to-sanity-check-a-date-in-java
    public static boolean isDateValid(String date) {

        try {
            dateFormat.setLenient(false);
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }

    }

    public static Date handleDateInput() {

        try {

            String dateInput = scanner.nextLine();

            // make sure the date input matches the pattern required, before returning the value
            while (!isDateValid(dateInput)) {
                System.out.println("Invalid format for date. Please use the format (dd/mm/yyyy): ");
                dateInput = scanner.nextLine();
            }
            return dateFormat.parse(dateInput);

        } catch (ParseException e) {
            System.out.println("Invalid format for date. Please use the format (dd/mm/yyyy): ");
            handleDateInput();
        }
        return null;

    }


}
