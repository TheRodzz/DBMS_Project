import java.sql.*;
import java.util.Scanner;


public class UserManager {
    static Connection connection = null;
    static Statement statement = null;
    static ResultSet resultSet = null;
    static Scanner sc = new Scanner(System.in);
    public static void registerNewUser() {
        try {
            while (true) {
                System.out.println("Select 1 to register as end user or 2 to register as admin user");
                int adm = sc.nextInt();
                sc.nextLine(); // consume new line char
                Boolean isAdm = (adm == 1) ? false : true;
                System.out.println("Enter your full name");
                String name = sc.nextLine();
                String user_name, pass, pass1;

                while (true) {
                    System.out.println("Enter username");
                    user_name = sc.nextLine();

                    // check for duplicate username
                    try {
                        connection = DBConnection.getConnection();
                        statement = connection.createStatement();

                        String query = "SELECT * FROM User WHERE user_name = ?";
                        PreparedStatement stmt = connection.prepareStatement(query);
                        stmt.setString(1, user_name);
                        ResultSet resultSet = stmt.executeQuery();
                        if (resultSet.next()) {
                            System.out.println("Username is already in use. Try a different username");
                        } else
                            break;

                    } catch (SQLException e) {
                        System.out.println("An error occurred while accessing the database:");
                        System.out.println("Error details: " + e.getMessage());
                        // Log the exception
                    }

                }

                // while (true) {
                //     System.out.println("Enter new password");
                //     pass = sc.nextLine();
                //     System.out.println("Re-enter password");
                //     pass1 = sc.nextLine();
                //     if (!pass.equals(pass1)) {
                //         System.out.println("Password mismatch. Try again");
                //     } else
                //         break;
                // }

                pass=InputHandler.pwdInputAndConfirm(sc);

                String sql = "INSERT INTO User (name, user_name, password, isAdmin) VALUES (?,?, ?, ?)";
                PreparedStatement stmt1 = null;
                try {
                    stmt1 = connection.prepareStatement(sql);
                    stmt1.setString(1, name);
                    stmt1.setString(2, user_name);
                    stmt1.setString(3, pass);
                    stmt1.setBoolean(4, isAdm);
                    int rowsAff = stmt1.executeUpdate();
                    if (rowsAff == 1) {
                        System.out.println("New user registered successfully! You can now proceed to login.");
                        break;
                    } else {
                        System.out.println("Failed to register new user. Try again.");
                    }
                } catch (SQLException e) {
                    System.out.println("An error occurred while accessing the database:");
                    System.out.println("Error details: " + e.getMessage());
                    // Log the exception
                } finally {
                    DBConnection.closeStatement(stmt1);
                }
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            // Log the exception
        } finally {
            // Close resources in a finally block
            DBConnection.closeResources(connection,statement,resultSet);
        }
    }

    public static UserInterface login() {
        try {
            while (true) {
                System.out.println("Enter your username");
                String username = sc.nextLine();
                System.out.println("Enter your password");
                String pass = sc.nextLine();

                // Login processing
                try {
                    connection = DBConnection.getConnection();
                    statement = connection.createStatement();

                    String query = "SELECT * FROM User WHERE user_name = ? AND password = ?";
                    PreparedStatement stmt = connection.prepareStatement(query);
                    stmt.setString(1, username);
                    stmt.setString(2, pass);
                    resultSet = stmt.executeQuery();
                    if (resultSet.next()) {
                        boolean isAdmin = resultSet.getBoolean("isAdmin");
                        if (isAdmin) {
                            System.out.println("Admin login successful!");
                            return new AdminUser(
                                    new User(resultSet.getInt("uid"), resultSet.getString("name"), username, pass,
                                            true));
                        } else {
                            System.out.println("User login successful!");
                            return new EndUser(new User(resultSet.getInt("uid"), resultSet.getString("name"), username,
                                    pass, false));
                        }
                    } else {
                        System.out.println("Invalid login credentials. Do you want to try again? Y/N");
                        char ch = sc.next().charAt(0);
                        sc.nextLine(); // Consume the newline character
                        if (ch == 'N' || ch == 'n') {
                            System.out.println("Exiting application, bye.");
                            break;
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("An error occurred while accessing the database:");
                    System.out.println("Error details: " + e.getMessage());
                    // Log the exception
                } 
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            // Log the exception
        } finally {
            // Close resources in a finally block
            DBConnection.closeResources(connection, statement, resultSet);
        }
        return null;
    }
}
