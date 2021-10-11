package menu;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    public static HotelResource hotelResource = HotelResource.getInstance();
    public static AdminResource adminResource = AdminResource.getInstance();
    public static Scanner scanner = new Scanner(System.in);
    public static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {

        // display initial menu
//        printMainMenu();

        enterCheckInDate();

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
            System.out.println("Please enter your email: ");
            String emailInput = scanner.nextLine();

            // make sure the email exists in the customer map
            while (!adminResource.getAllCustomers().containsKey(emailInput)) {
                System.out.println("This email doesn't exist, please try another one.");
                emailInput = scanner.nextLine();
            }

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

}
