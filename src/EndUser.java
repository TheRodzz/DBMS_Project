import java.sql.*;
import java.util.Scanner;

public class EndUser implements UserInterface {
    User user;

    EndUser(User user) {
        this.user = user;
    }

    @Override
    public void run(Scanner sc) {
        while (true) {
            System.out.println("-------------------------------------");
            System.out.println("Select your choice");
            System.out.println("0. Logout");
            System.out.println("1. List the available media");
            System.out.println("2. Watch a media item");
            System.out.println("3. Get your viewing history");
            System.out.println("4. Check your total watch time");
            System.out.println("5. Update your profile");
            System.out.println("6. To add rating for a media item");
            System.out.println("7. Search by genre");
            System.out.println("8. Search by language");
            System.out.println("9. Search by title");
            System.out.println("10. Get average rating of all media items");
            System.out.println("-------------------------------------");
            int choice = sc.nextInt();
            sc.nextLine(); // consume new line
            if (choice == 0)
                break;
            else if (choice == 1) {
                this.listMedia();
            } else if (choice == 2) {
                this.watchMedia();
            } else if (choice == 3) {
                this.getHistory();
            } else if (choice == 4) {
                this.getWatchTime();
            } else if (choice == 5) {
                this.updateProfile();
            } else if (choice == 6) {
                this.addRating();
            } else if (choice == 7) {
                this.filterByGenre();
            } else if (choice == 8) {
                this.filterByLanguage();
            } else if (choice == 9) {
                this.searchByTitle();
            } else if (choice == 10) {
                this.getAvgRating();
            }

        }
    }

    /**
     * Allows the user to watch a media item.
     */
    private void watchMedia() {
        System.out.println("-------------------------------------");
        System.out.println("Enter the ID of the media you want to watch");
        this.listMedia();
        int tid = InputHandler.getIDinput();
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "INSERT INTO View_History VALUES (?,?,?)";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, tid);
            stmt.setInt(2, this.user.getUid());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            stmt.setTimestamp(3, timestamp);
            int rows = stmt.executeUpdate();
            if (rows == 1) {
                DBConnection.commitTransaction(connection);
                System.out.println("You watched media with ID = " + tid);
            } else {
                System.out.println("Failed to watch media ");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
        } finally {
            System.out.println("-------------------------------------");
            DBConnection.closeResources(connection, stmt, null);
        }
    }

    /**
     * Retrieves the viewing history for the user.
     */
    private void getHistory() {
        System.out.println("-------------------------------------");
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT title,watched_on FROM Media m, View_History vh, User u WHERE u.uid = ? AND u.uid=vh.uid AND m.tid=vh.tid ORDER BY watched_on DESC";
            connection = DBConnection.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, this.user.getUid());
            resultSet = stmt.executeQuery();
            System.out.println("Watch history for " + this.user.getName());
            while (resultSet.next()) {
                System.out.println(
                        "Watched " + resultSet.getString("title") + " on " + resultSet.getTimestamp("watched_on"));
            }

        } catch (Exception e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
        } finally {
            System.out.println("-------------------------------------");
            DBConnection.closeResources(connection, stmt, resultSet);
        }
    }

    /**
     * Retrieves the total watch time for the user.
     */
    private void getWatchTime() {
        System.out.println("-------------------------------------");
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        int watchTime = 0;
        try {
            String sql = "SELECT duration FROM Media m, View_History vh WHERE vh.uid = ? AND m.tid=vh.tid";
            connection = DBConnection.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, this.user.getUid());
            resultSet = stmt.executeQuery();
            if (!resultSet.next()) {
                System.out.println("It looks like you have not watched any media on the OTT Platform yet.");
            } else {
                while (true) {
                    watchTime = watchTime + resultSet.getInt("duration");
                    if (!resultSet.next())
                        break;
                }
                System.out.println("You have watched media on OTT Platform for a total of " + watchTime + " minutes");
            }

        } catch (Exception e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
        } finally {
            System.out.println("-------------------------------------");
            DBConnection.closeResources(connection, stmt, resultSet);
        }
    }

    /**
     * Allows the user to update their profile information.
     */
    private void updateProfile() {
        System.out.println("-------------------------------------");
        Connection connection = null;
        PreparedStatement stmt = null;
        User oldUser = this.user;
        try {
            System.out.println("Your current profile");
            System.out.println(this.user);

            System.out.println("Enter the new full name (or press Enter to keep the current value):");
            String newName = InputHandler.getStringInput();
            if (!newName.isEmpty()) {
                this.user.setName(newName);
            }

            System.out.println("Enter the new user name (or press Enter to keep the current value):");
            String newUserName = InputHandler.getStringInput();
            if (!newUserName.isEmpty()) {
                this.user.setUser_name(newUserName);
            }

            System.out.println("Do you want to change your password? Y/N");
            String choice = InputHandler.getNonEmptyString();
            if (choice.charAt(0) == 'Y' || choice.charAt(0) == 'y') {
                String newPass = InputHandler.pwdInputAndConfirm();
                this.user.setPassword(newPass);
            }

            String sql = "UPDATE User SET name = ?, user_name = ?, password = ? WHERE uid = ?";
            connection = DBConnection.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, this.user.getName());
            stmt.setString(2, this.user.getUser_name());
            stmt.setString(3, this.user.getPassword());
            stmt.setInt(4, this.user.getUid());

            int rows = stmt.executeUpdate();
            if (rows == 1) {
                DBConnection.commitTransaction(connection);
                System.out.println("Profile updated successfully. Here is the updated profile:");
                System.out.println(this.user);
            } else {
                System.out.println("Failed to update profile");
                // this is done so that if update fails, the current user doesn't get attribute
                // values different from those stored in the database
                this.user = oldUser;
            }

        } catch (Exception e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
        } finally {
            System.out.println("-------------------------------------");
            DBConnection.closeResources(connection, stmt, null);
        }
    }

    /**
     * Allows the user to add a rating for a media item they have watched.
     */
    private void addRating() {
        System.out.println("-------------------------------------");
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        System.out.println("Enter the ID of the media item you want to give a rating to");
        this.listMedia();
        int tid = InputHandler.getIDinput();
        try {
            String check = "SELECT uid FROM View_History WHERE uid = ? AND tid = ?";
            connection = DBConnection.getConnection();
            stmt = connection.prepareStatement(check);
            stmt.setInt(1, this.user.getUid());
            stmt.setInt(2, tid);

            resultSet = stmt.executeQuery();
            if (!resultSet.next()) {
                System.out.println("You cannot give a rating to media you have not watched");
            } else {
                double rating = InputHandler.getRatingInput();
                String sql = "INSERT INTO Ratings VALUES (?,?,?)";
                stmt = connection.prepareStatement(sql);
                stmt.setInt(1, tid);
                stmt.setInt(2, this.user.getUid());
                stmt.setDouble(3, rating);
                int rows = stmt.executeUpdate();
                if (rows == 1) {
                    DBConnection.commitTransaction(connection);
                    System.out.println("Rating added successfully");
                } else {
                    System.out.println("Failed to add rating");
                }
            }

        } catch (Exception e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
        } finally {
            System.out.println("-------------------------------------");
            DBConnection.closeResources(connection, stmt, resultSet);
        }
    }
}
