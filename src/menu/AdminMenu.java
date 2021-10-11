package menu;

import api.AdminResource;
import model.*;

import java.util.*;

public class AdminMenu {

    public static Scanner scanner = new Scanner(System.in);
    public static AdminResource adminResource = AdminResource.getInstance();

    public static void main(String[] args) {

        // display initial admin menu
        printAdminMenu();

        handleAdminOptions();

    }

    public static void printAdminMenu() {
        String adminMenu = """
                
                Admin Menu
                1. See all Customers
                2. See all Rooms
                3. See all Reservations
                4. Add a Room
                5. Add test data
                6. Clear test data
                7. Back to Main Menu
                
                Please enter your option (1 - 5):\040""";

        System.out.println(adminMenu);
    }

    public static void handleAdminOptions() {

        boolean keepRunning = true;

        while (keepRunning) {

            try {

                // ask user to enter their option
                int option = Integer.parseInt(scanner.nextLine());

                // make sure user enter a number between 1 - 5
                while (option > 7 || option < 1) {
                    System.out.println("Invalid input. Please enter your option again (1 - 5): ");
                    option = Integer.parseInt(scanner.nextLine());
                }

                switch (option) {
                    case 1 -> seeAllCustomers();
                    case 2 -> seeAllRooms();
                    case 3 -> seeAllReservations();
                    case 4 -> addARoom();
                    case 5 -> {
                        MainMenu.main(null);
                        keepRunning = false;
                    }
                    default -> {}
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter your option again (1 - 5): ");
                handleAdminOptions();
            }

        }

    }

    // handle option 1: See all Customers
    public static void seeAllCustomers() {

        try {

            Map<String, Customer> customers = adminResource.getAllCustomers();
            int i = 1;

            // print the customer list
            System.out.println("Customer List: ");
            for (Customer customer : customers.values()) {
                System.out.printf("\n%d\\. %s", i, customer);
                i++;
            }

        } catch (NullPointerException e) {
            System.out.println("Sorry, the customer list is empty.");
        }

    }

    // handle option 2: See all Rooms
    public static void seeAllRooms() {

        try {

            Map<String, IRoom> rooms = adminResource.getAllRooms();
            int i = 1;

            // print the room list
            System.out.println("Room List: ");
            for (IRoom room : rooms.values()) {
                System.out.printf("\n%d\\. %s", i, room);
                i++;
            }

        } catch (NullPointerException e) {
            System.out.println("Sorry, the room list is empty.");
        }

    }

    // handle option 3: See all Reservations
    public static void seeAllReservations() {

        try {

            List<Reservation> reservations = adminResource.getAllReservations();
            int i = 1;

            // print the reservation list
            System.out.println("Reservation List: ");
            for (Reservation reservation : reservations) {
                System.out.printf("\n%d\\. %s", i, reservation);
                i++;
            }

        } catch (NullPointerException e) {
            System.out.println("Sorry, the reservation list is empty.");
        }

    }

    // handle option 4: Add a Room
    public static void addARoom() {

        // ask admin to enter a room number
        System.out.println("Please enter a room number: ");
        String roomNumberInput = scanner.nextLine().trim();

        // make sure all the room numbers in the room list are unique
        while (adminResource.getAllRooms().containsKey(roomNumberInput)) {
            System.out.println("Sorry, this room number is already taken, please try another one: ");
            roomNumberInput = scanner.nextLine().trim();
        }

        // ask admin to enter a room price per night
        System.out.println("Please enter a room price per night: ");
        double roomPriceInput = Double.parseDouble(scanner.nextLine().trim());

        // make sure room price >= 0
        while (roomPriceInput < 0) {
            System.out.println("Sorry, the room price per night cannot be less than $0.");
            roomPriceInput = Double.parseDouble(scanner.nextLine().trim());
        }

        // ask admin to enter a room type
        System.out.println("Please choose a room type, single room or double room? (s/d)");
        String roomTypeInput = scanner.nextLine().trim().toLowerCase(Locale.ROOT);

        // make sure room type input only matches "s" or "d"
        List<String> conditions = Arrays.asList("s", "d");
        while (!conditions.contains(roomTypeInput)) {
            System.out.println("Please choose a room type, single room or double room? (s/d)");
            roomTypeInput = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
        }

        RoomType roomType = null;
        if (roomTypeInput.equals("s")) {
            roomType = RoomType.SINGLE;
        } else if (roomTypeInput.equals("d")) {
            roomType = RoomType.DOUBLE;
        }

        IRoom roomToAdd = new Room(roomNumberInput, roomPriceInput, roomType);
        adminResource.addRoom(roomToAdd);
        System.out.println("Room added successfully.");

    }

    // handle option 5: Add test data
    pu

}
