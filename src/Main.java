import java.sql.*;
import java.util.Scanner;

public class Main {
    static Connection connection = null;
    static Statement statement = null;
    static ResultSet resultSet = null;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            while (true) {
                System.out.println("Welcome to OTT Platform!");
                System.out.println("Press 0 to exit, 1 to register a new account or 2 to login");
                int choice = sc.nextInt();
                sc.nextLine(); // consume new line character
                if (choice == 0)
                    break;
                else if (choice == 1)
                    UserManager.registerNewUser();
                else {
                    UserInterface user = UserManager.login();
                    if (user != null) {
                        if (user instanceof EndUser) {
                            EndUser endUser = (EndUser) user;
                            endUser.run(sc);
                        } else if (user instanceof AdminUser) {
                            AdminUser adminUser = (AdminUser) user;
                            adminUser.run(sc);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            // Log the exception
        } finally {
            DBConnection.closeResources(connection, statement, resultSet);
            sc.close();
        }
    }

}
