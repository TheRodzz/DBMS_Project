import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import java.text.SimpleDateFormat;

public class AdminUser implements UserInterface {

    User user;

    public AdminUser(User user) {
        this.user = user;
    }

    @Override
    public void run(Scanner sc) {
        try {
            while (true) {
                System.out.println("-------------------------------------");
                System.out.println("Select your choice");
                System.out.println("0. Logout");
                System.out.println("1. List the available media");
                System.out.println("2. Add a media item");
                System.out.println("3. Delete a media item");
                System.out.println("4. Update a media item");
                System.out.println("5. Add a new language to the database");
                System.out.println("6. Add a new language for an existing media item");
                System.out.println("7. Get each viewers total watch time");
                System.out.println("8. Get each media item's total play time across all users");
                System.out.println("9. Get a performance report");

                System.out.println("-------------------------------------");
                int choice;
                try {
                    choice = sc.nextInt();
                    sc.nextLine(); // Consume newline character
                } catch (InputMismatchException e) {
                    System.out.println("Invalid choice. Please enter a valid integer.");
                    sc.nextLine(); // Consume invalid input
                    continue; // Restart the loop to prompt for input again
                }
                if (choice == 0) {
                    break;
                } else if (choice == 1) {
                    this.listMedia();
                } else if (choice == 2) {
                    this.addMedia();
                } else if (choice == 3) {
                    this.deleteMedia();
                } else if (choice == 4) {
                    this.updateMedia();
                } else if (choice == 5) {
                    this.addNewLanguage();
                } else if (choice == 6) {
                    this.addMediaLanguage();
                } else if (choice == 7) {
                    this.viewerWatchTimeStats();
                } else if (choice == 8) {
                    this.mediaWatchTimeStats();
                } else if (choice == 9) {
                    this.performanceReport();
                } else if (choice == 10) {
                    this.filterByGenre();
                } else if (choice == 11) {
                    this.filterByLanguage();
                } else if (choice == 12) {
                    this.searchByTitle();
                } else if (choice == 13) {
                    this.getAvgRating();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new media item to the database.
     */
    private void addMedia() {
        System.out.println("-------------------------------------");
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            Media m = InputHandler.getMediaInput();
            connection = DBConnection.getConnection();
            String sql = "INSERT INTO Media (title, release_date, duration, poster_url, trailer_url) VALUES (?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, m.getTitle());
            stmt.setString(2, m.getRelease_date());
            stmt.setInt(3, m.getDuration());
            stmt.setString(4, m.getPoster_url());
            stmt.setString(5, m.getTrailer_url());

            int rows = stmt.executeUpdate();
            if (rows == 1) {
                System.out.println("The following media has been added successfully: ");
                DBConnection.commitTransaction(connection);
                System.out.println(m);
            } else {
                System.out.println("Failed to add media");
                DBConnection.rollbackTransaction(connection);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
            DBConnection.rollbackTransaction(connection);
        } finally {
            DBConnection.closeConnection(connection);
            DBConnection.closeStatement(stmt);
            System.out.println("-------------------------------------");
        }
    }

    /**
     * Deletes a media item from the database.
     */
    private void deleteMedia() {
        System.out.println("-------------------------------------");
        System.out.println("Select the ID of the media item you want to delete");
        this.listMedia();
        int tid = InputHandler.getIDinput();
        String sql = "DELETE FROM Media WHERE tid=?";
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DBConnection.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, tid);
            int rows = stmt.executeUpdate();
            if (rows == 1) {
                System.out.println("Media deleted successfully");
                DBConnection.commitTransaction(connection);
            } else {
                System.out.println("Failed to delete media");
                DBConnection.rollbackTransaction(connection);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
            DBConnection.rollbackTransaction(connection);
        } finally {
            DBConnection.closeResources(connection, stmt, null);
            System.out.println("-------------------------------------");
        }
    }

    /**
     * Updates a media item in the database.
     */
    public void updateMedia() {
        System.out.println("-------------------------------------");
        System.out.println("Enter the tid (media ID) of the media item to update:");
        this.listMedia();
        int tid = InputHandler.getIDinput();

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.getConnection();

            // Fetch current media attributes
            String selectSql = "SELECT * FROM Media WHERE tid = ?";
            stmt = connection.prepareStatement(selectSql);
            stmt.setInt(1, tid);
            resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                // Media item found, create a Media object with existing values
                String title = resultSet.getString("title");
                Date releaseDate = resultSet.getDate("release_date");
                int duration = resultSet.getInt("duration");
                String posterUrl = resultSet.getString("poster_url");
                String trailerUrl = resultSet.getString("trailer_url");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = dateFormat.format(releaseDate);
                Media media = new Media(tid, duration, title, dateString, posterUrl, trailerUrl);

                // Prompt the user to update each attribute of the media item
                System.out.println("Current media details:");
                System.out.println(media);

                Media updatedMedia = InputHandler.updateMedia(media);

                // Update the media item in the database
                String updateSql = "UPDATE Media SET title = ?, release_date = ?, duration = ?, poster_url = ?, trailer_url = ? WHERE tid = ?";
                stmt = connection.prepareStatement(updateSql);
                stmt.setString(1, updatedMedia.getTitle());
                stmt.setDate(2, Date.valueOf(updatedMedia.getRelease_date()));
                stmt.setInt(3, updatedMedia.getDuration());
                stmt.setString(4, updatedMedia.getPoster_url());
                stmt.setString(5, updatedMedia.getTrailer_url());
                stmt.setInt(6, updatedMedia.getTid());

                int rows = stmt.executeUpdate();
                if (rows == 1) {
                    DBConnection.commitTransaction(connection);
                    System.out.println("The media item with tid " + tid + " has been updated successfully.");
                    System.out.println("Updated media details:");
                    System.out.println(media);
                } else {
                    System.out.println("Failed to update the media item with tid " + tid + ".");
                    DBConnection.rollbackTransaction(connection);

                }
            } else {
                System.out.println("No media item found with tid " + tid + ".");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
            DBConnection.rollbackTransaction(connection);
        } finally {
            System.out.println("-------------------------------------");
            DBConnection.closeResources(connection, stmt, resultSet);
        }
    }

    /**
     * Adds a new language to the database.
     */
    private void addNewLanguage() {
        System.out.println("-------------------------------------");
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        System.out.println("Enter the language you want to add");
        String lang = InputHandler.getNonEmptyString();
        try {
            String sql = "INSERT INTO Languages (language) VALUES (?)";
            connection = DBConnection.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, lang);
            int rows = stmt.executeUpdate();
            if (rows == 1) {
                DBConnection.commitTransaction(connection);
                System.out.println("New language " + lang + " has been added succuessfully");
            } else {
                System.out.println("Failed to add language " + lang);
                DBConnection.rollbackTransaction(connection);
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
     * Adds a new language for a selected media.
     * Prompts the user to enter the media ID and language ID.
     * Checks if the provided IDs exist in their respective tables before adding the
     * mapping to the Media_Languages table.
     * Prints success or failure messages based on the execution result.
     */
    private void addMediaLanguage() {
        System.out.println("-------------------------------------");
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        System.out.println();
        try {
            System.out.println("Enter the Media ID (tid):");
            this.listMedia();
            int tid = InputHandler.getIDinput();

            System.out.println("Enter the Language ID (lid):");
            this.displayAllLanguages();
            int lid = InputHandler.getIDinput();

            // Check if the media and language IDs exist in their respective tables before
            // adding them to the Media_Languages table
            boolean mediaExists = checkMediaExistence(tid);
            boolean languageExists = checkLanguageExistence(lid);

            if (mediaExists && languageExists) {
                // Execute the SQL query to insert the media-language mapping into the
                // Media_Languages table
                String sql = "INSERT INTO Media_Languages (tid, lid) VALUES (?, ?)";
                connection = DBConnection.getConnection();
                stmt = connection.prepareStatement(sql);
                stmt.setInt(1, tid);
                stmt.setInt(2, lid);

                int rows = stmt.executeUpdate();

                if (rows == 1) {
                    DBConnection.commitTransaction(connection);
                    System.out.println("New language added for the selected media successfully.");
                } else {
                    System.out.println("Failed to add the media-language mapping.");
                    DBConnection.rollbackTransaction(connection);
                }
            } else {
                System.out.println("Media or Language does not exist. Please make sure to enter valid IDs.");

            }

        } catch (Exception e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
            DBConnection.rollbackTransaction(connection);
        } finally {
            System.out.println("-------------------------------------");
            DBConnection.closeResources(connection, stmt, resultSet);
        }
    }

    /**
     * Retrieves and displays the total watch time for each user.
     * Executes a SQL query to calculate the sum of the duration for each user from
     * the Media, View_History, and User tables.
     * Prints the total watch time for each user in minutes.
     */
    private void viewerWatchTimeStats() {
        System.out.println("-------------------------------------");
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultset = null;
        try {
            String sql = "SELECT SUM(duration) as time,n.name FROM (SELECT m.duration,vh.uid,u.name FROM Media m,View_History vh, User u WHERE m.tid=vh.tid AND u.uid=vh.uid ORDER BY vh.uid ) n GROUP BY n.name ORDER BY time DESC";
            connection = DBConnection.getConnection();
            stmt = connection.prepareStatement(sql);
            resultset = stmt.executeQuery();
            System.out.println("Total watch time for each user is as follows:");
            while (resultset.next()) {
                int time = resultset.getInt("time");
                String name = resultset.getString("name");
                System.out.println(name + " : " + time + " minutes");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
        } finally {
            DBConnection.closeConnection(connection);
            DBConnection.closeStatement(stmt);
        }
    }

    /**
     * Retrieves and displays the total watch time for each media.
     * Executes a SQL query to calculate the product of the number of viewers and
     * the duration for each media from the View_History and Media tables.
     * Prints the total watch time for each media in minutes.
     */
    private void mediaWatchTimeStats() {
        System.out.println("-------------------------------------");
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultset = null;
        try {
            String sql = "SELECT COUNT(uid) as viewers,duration,title FROM View_History vh,Media m WHERE vh.tid=m.tid GROUP BY m.tid ORDER BY viewers*duration DESC";
            connection = DBConnection.getConnection();
            stmt = connection.prepareStatement(sql);
            resultset = stmt.executeQuery();
            System.out.println("Total watch time for each media is as follows:");
            while (resultset.next()) {
                int viewers = resultset.getInt("viewers");
                int duration = resultset.getInt("duration");
                System.out.println(resultset.getString("title") + " : " + viewers * duration + " minutes");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
        } finally {
            System.out.println("-------------------------------------");
            DBConnection.closeConnection(connection);
            DBConnection.closeStatement(stmt);
        }
    }

    /**
     * Generates a performance report for the platform.
     * Executes SQL queries to calculate the total watch time and the total number
     * of views on the platform.
     * Prints the total platform watch time and total platform views.
     */
    private void performanceReport() {
        System.out.println("-------------------------------------");
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultset = null;
        try {
            // total watch time and total views

            String sql = "SELECT SUM(duration) AS totalTime FROM (SELECT m.duration FROM Media m, View_History vh WHERE vh.tid=m.tid) t";
            connection = DBConnection.getConnection();
            stmt = connection.prepareStatement(sql);
            resultset = stmt.executeQuery();
            resultset.next();
            int totalTime = resultset.getInt("totalTime");
            sql = "SELECT COUNT(*) AS views FROM View_History";
            stmt = connection.prepareStatement(sql);
            resultset = stmt.executeQuery();
            resultset.next();
            int totalViewers = resultset.getInt("views");
            System.out.println("Total platform watch time: " + totalTime + " minutes");
            System.out.println("Total platform views: " + totalViewers);

        } catch (Exception e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
        } finally {
            System.out.println("-------------------------------------");
            DBConnection.closeResources(connection, stmt, resultset);

        }
    }
}
