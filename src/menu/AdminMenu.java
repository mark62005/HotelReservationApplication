package menu;

public class AdminMenu {

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
}
