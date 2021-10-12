package menu;

import api.AdminResource;
import api.HotelResource;
import model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AdminMenu {

    public static Scanner scanner = new Scanner(System.in);
    public static AdminResource adminResource = AdminResource.getInstance();
    public static HotelResource hotelResource = HotelResource.getInstance();

    public static void main(String[] args) {

        boolean keepRunning = true;

        // display initial admin menu
        printAdminMenu();

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
                    case 5 -> addTestData();
                    case 7 -> {
                        MainMenu.main(null);
                        keepRunning = false;
                    }
                    default -> {}
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number from 1 to 7.");
                AdminMenu.main(null);
            }
        }

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
                
                Please enter your option (1 - 7):\040""";

        System.out.println(adminMenu);
    }

    // handle (y/n) options
    public static String handleConfirmOptions() {

        List<String> confirmConditions = Arrays.asList("y", "n");
        String userInput = scanner.nextLine().toLowerCase(Locale.ROOT);

        while (!confirmConditions.contains(userInput)) {
            System.out.println("Invalid input. Please enter (y/n): ");
            userInput = scanner.nextLine().toLowerCase(Locale.ROOT);
        }
        return userInput;

    }

    // handle option 1: See all Customers
    public static void seeAllCustomers() {

        try {

            Map<String, Customer> customers = adminResource.getAllCustomers();
            int i = 1;

            if (customers.isEmpty()) {
                System.out.println("\nSorry, the customer list is empty.");
            } else {

                // print the customer list
                System.out.print("\nCustomer List: ");
                for (Customer customer : customers.values()) {
                    System.out.printf("\n%d. %s", i, customer);
                    i++;

                    if (i == customers.size() + 1) {
                        System.out.println();
                    }
                }
                System.out.println();

            }

            System.out.println("Back to Admin Menu? (y/n)");
            String confirmOption = handleConfirmOptions();

            if (confirmOption.equals("y")) {
                printAdminMenu();
            } else if (confirmOption.equals("n")) {
                seeAllCustomers();
            }

        } catch (NullPointerException e) {
            System.out.println("Sorry, the customer list is empty.");
            seeAllCustomers();
        }

    }

    // handle option 2: See all Rooms
    public static void seeAllRooms() {

        boolean keepRunning = true;

        while (keepRunning) {
            try {

                Map<String, IRoom> rooms = adminResource.getAllRooms();
                int i = 1;

                if (rooms.isEmpty()) {
                    System.out.println("Sorry, the room list is empty.");
                    keepRunning = false;
                } else {

                    // print the room list
                    System.out.print("\nRoom List: ");
                    for (IRoom room : rooms.values()) {
                        System.out.printf("\n%d. %s", i, room);
                        i++;

                        if (i == rooms.size() + 1) {
                            System.out.println();
                        }
                    }
                    System.out.println();

                }

                System.out.println("Back to Admin Menu? (y/n)");
                String confirmOption = handleConfirmOptions();

                if (confirmOption.equals("y")) {
                    keepRunning = false;
                } else if (confirmOption.equals("n")) {
                    seeAllRooms();
                }

            } catch (NullPointerException e) {
                System.out.println("Sorry, the room list is empty.");
            }
        }
        printAdminMenu();

    }

    // handle option 3: See all Reservations
    public static void seeAllReservations() {

        boolean keepRunning = true;

        while (keepRunning) {
            try {

                List<Reservation> reservations = adminResource.getAllReservations();
                int i = 1;

                if (reservations.isEmpty()) {
                    System.out.println("Sorry, the reservation list is empty.");
                    keepRunning = false;
                } else {

                    // print the reservation list
                    System.out.print("\nReservation List: ");
                    for (Reservation reservation : reservations) {
                        System.out.printf("\n%d. %s", i, reservation);
                        i++;

                        if (i == reservations.size() + 1) {
                            System.out.println();
                        }
                    }
                    System.out.println();

                }

                System.out.println("Back to Admin Menu? (y/n)");
                String confirmOption = handleConfirmOptions();

                if (confirmOption.equals("y")) {
                    keepRunning = false;
                } else if (confirmOption.equals("n")) {
                    seeAllReservations();
                }

            } catch (NullPointerException e) {
                System.out.println("Sorry, the reservation list is empty.");
            }
        }
        printAdminMenu();

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

        printAdminMenu();

    }

    // handle option 5: Add test data
    public static void addTestData() {

        addCustomerSamples();
        addRoomSamples();
        addReservationSamples();

        System.out.println("Sample data added successfully.");
        printAdminMenu();

    }

    // add customer sample data
    public static void addCustomerSamples() {

        hotelResource.addCustomer("John", "Doe","john@gmail.com");
        hotelResource.addCustomer("Max", "Musterman","Muster@web.com");
        hotelResource.addCustomer("Frieda", "Meyer","F.Meyer@gmx.com");
        hotelResource.addCustomer("Carla", "Schmidt","CarlaSchmidt@webmail.com");

    }

    // add room sample data
    public static void addRoomSamples() {

        IRoom room1 = new Room("100", 50.0, RoomType.SINGLE);
        IRoom room2 = new Room("101", 70.0, RoomType.DOUBLE);
        IRoom room3 = new Room("102", 90.0, RoomType.SINGLE);
        IRoom room4 = new Room("103", 110.0, RoomType.DOUBLE);

        adminResource.addRoom(room1);
        adminResource.addRoom(room2);
        adminResource.addRoom(room3);
        adminResource.addRoom(room4);

    }

    // add reservation sample data
    public static void addReservationSamples() {

        Customer john = hotelResource.getCustomer("john@gmail.com");
        Customer max = hotelResource.getCustomer("Muster@web.com");
        Customer frieda = hotelResource.getCustomer("F.Meyer@gmx.com");
        Customer carla = hotelResource.getCustomer("CarlaSchmidt@webmail.com");

        IRoom room1 = hotelResource.getRoom("100");
        IRoom room2 = hotelResource.getRoom("101");
        IRoom room3 = hotelResource.getRoom("102");
        IRoom room4 = hotelResource.getRoom("103");

        Date checkInDate1 = null;
        Date checkOutDate1 = null;
        Date checkInDate2 = null;
        Date checkOutDate2 = null;
        Date checkInDate3 = null;
        Date checkOutDate3 = null;
        Date checkInDate4 = null;
        Date checkOutDate4 = null;

        try {

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            checkInDate1 = dateFormat.parse("23/12/2020");
            checkOutDate1 = dateFormat.parse("31/12/2020");

            checkInDate2 = dateFormat.parse("04/01/2021");
            checkOutDate2 = dateFormat.parse("12/01/2021");

            checkInDate3 = dateFormat.parse("26/12/2020");
            checkOutDate3 = dateFormat.parse("27/12/2020");

            checkInDate4 = dateFormat.parse("12/12/2020");
            checkOutDate4 = dateFormat.parse("19/01/2021");

        }  catch (ParseException e) {
            e.printStackTrace();
        }

        hotelResource.reserveARoom(john, room1, checkInDate1, checkOutDate1);
        hotelResource.reserveARoom(max, room2, checkInDate2, checkOutDate2);
        hotelResource.reserveARoom(frieda, room3, checkInDate3, checkOutDate3);
        hotelResource.reserveARoom(carla, room4, checkInDate4, checkOutDate4);

    }

}
