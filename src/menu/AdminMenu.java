package menu;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;

import java.util.Map;
import java.util.Scanner;

public class AdminMenu {

    public static Scanner scanner = new Scanner(System.in);
    public static HotelResource hotelResource = HotelResource.getInstance();
    public static AdminResource adminResource = AdminResource.getInstance();

    public static void main(String[] args) {

        // display initial admin menu
        printAdminMenu();

    }

    public static void printAdminMenu() {
        String adminMenu = """
                
                Admin Menu
                1. See all Customers
                2. See all Rooms
                3. See all Reservations
                4. Add a Room
                5. Back to Main Menu
                Please enter your option (1 - 5):
                """;

        System.out.println(adminMenu);
    }

    // handle option 1: See all Customers
    public static void seeAllCustomers() {

        Map<String, Customer> customers = adminResource.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("Sorry, the customer list is empty.");
        } else {

            int i = 1;

            // print the customer list
            System.out.println("Customer List: ");
            for (Customer customer : customers.values()) {
                System.out.printf("\n%d\\. %s", i, customer);
                i++;
            }

        }

    }

    // handle option 2: See all Rooms
    public static void seeAllRooms() {

        Map<String, IRoom> rooms = adminResource.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("Sorry, the room list is empty.");
        } else {

            int i = 1;

            // print the room list
            System.out.println("Room List: ");
            for (IRoom room : rooms.values()) {
                System.out.printf("\n%d\\. %s", i, room);
                i++;
            }

        }

    }

}
