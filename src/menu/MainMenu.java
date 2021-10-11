package menu;

public class MainMenu {

    public static void main(String[] args) {

        // display initial menu
        printMainMenu();

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

}
