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

            int choice = sc.nextInt();
            sc.nextLine(); // consume new line
            if (choice == 0)
                break;
            else if (choice == 1) {
                this.listMedia();
            } else if (choice == 2) {
                this.watchMedia(sc);
            } else if (choice == 3) {
                this.getHistory();
            } else if (choice == 4) {
                this.getWatchTime();
            } else if (choice == 5) {
                this.updateProfile(sc);
            } else if (choice == 6) {
                this.addRating(sc);
            } else if (choice == 7) {
                this.filterByGenre(sc);
            } else if (choice == 8) {
                this.filterByLanguage(sc);
            } else if (choice == 9) {
                this.searchByTitle(sc);
            } else if (choice == 10) {
                this.getAvgRating();
            }
        }
    }

    private void watchMedia(Scanner sc) {
        System.out.println("Enter the ID of the media you want to watch");
        this.listMedia();
        int tid = sc.nextInt();
        sc.nextLine(); // consume new line char
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
                System.out.println("You watched media with ID = " + tid);
            } else {
                System.out.println("Failed to watch media ");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
        } finally {
            DBConnection.closeResources(connection, stmt, null);
        }
    }

    private void getHistory() {
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
            DBConnection.closeResources(connection, stmt, resultSet);
        }
    }

    private void getWatchTime() {
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
            DBConnection.closeResources(connection, stmt, resultSet);
        }
    }

    private void updateProfile(Scanner sc) {
        Connection connection = null;
        PreparedStatement stmt = null;
        User oldUser = this.user;
        try {
            System.out.println("Your current profile");
            System.out.println(this.user);

            System.out.println("Enter the new full name (or press Enter to keep the current value):");
            String newName = sc.nextLine();
            if (!newName.isEmpty()) {
                this.user.setName(newName);
            }

            System.out.println("Enter the new user name (or press Enter to keep the current value):");
            String newUserName = sc.nextLine();
            if (!newUserName.isEmpty()) {
                this.user.setUser_name(newUserName);
            }

            System.out.println("Do you want to change your password? Y/N");
            String choice = sc.nextLine();
            if (choice.charAt(0) == 'Y' || choice.charAt(0) == 'y') {
                String newPass = InputHandler.pwdInputAndConfirm(sc);
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
                System.out.println("Profile updated successfully. Here is the updated profile:");
                System.out.println(this.user);
            } else {
                System.out.println("Failed to update profile");
                // this is done so that if update fails, the current user doesnt get attribute
                // values different from those stored in the database
                this.user = oldUser;
            }

        } catch (Exception e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
        } finally {
            DBConnection.closeResources(connection, stmt, null);
        }
    }

    private void addRating(Scanner sc) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        System.out.println("Enter the ID of the media item you want to give rating to");
        this.listMedia();
        int tid = sc.nextInt();
        sc.nextLine(); // consume new line char
        try {
            String check = "SELECT uid FROM View_History WHERE uid = ? AND tid = ?";
            connection = DBConnection.getConnection();
            stmt = connection.prepareStatement(check);
            stmt.setInt(1, this.user.getUid());
            stmt.setInt(2, tid);

            resultSet = stmt.executeQuery();
            if (!resultSet.next()) {
                System.out.println("You cannot give rating to media you have not watched");
            } else {
                double rating = InputHandler.getRatingInput(sc);
                String sql = "INSERT INTO Ratings VALUES (?,?,?)";
                stmt = connection.prepareStatement(sql);
                stmt.setInt(1, tid);
                stmt.setInt(2, this.user.getUid());
                stmt.setDouble(3, rating);
                int rows = stmt.executeUpdate();
                if (rows == 1) {
                    System.out.println("Rating added successfully");
                } else {
                    System.out.println("Falied to add rating");
                }
            }

        } catch (Exception e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
        } finally {
            DBConnection.closeResources(connection, stmt, resultSet);
        }
    }

}