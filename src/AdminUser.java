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
                System.out.println("Select your choice");
                System.out.println("0. Logout");
                System.out.println("1. List the available media");
                System.out.println("2. Add a media item");
                System.out.println("3. Delete a media item");
                System.out.println("4. Update a media item");

                int choice;
                try {
                    choice = sc.nextInt();
                    sc.nextLine(); // Consume newline character
                } catch (InputMismatchException e) {
                    System.out.println("Invalid choice. Please enter a valid integer.");
                    sc.nextLine(); // Consume invalid input
                    continue; // Restart the loop to prompt for input again
                }
                if(choice==0){
                    break;
                }
                else if (choice == 1) {
                    this.listMedia();
                } else if (choice == 2) {
                    this.addMedia(sc);
                } else if (choice == 3) {
                    this.deleteMedia(sc);
                } else if (choice == 4) {
                    this.updateMedia(sc);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void addMedia(Scanner scanner) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            Media m = InputHandler.getMediaInput(scanner);
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
                System.out.println(m);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
        } finally {
            DBConnection.closeConnection(connection);
            DBConnection.closeStatement(stmt);
        }
    }

    private void deleteMedia(Scanner sc) {
        System.out.println("Select the ID of the media item you want to delete");
        this.listMedia();
        int tid = sc.nextInt();
        sc.nextLine(); // consume new line character
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
            }
        } catch (Exception e) {

        } finally {
            DBConnection.closeResources(connection, stmt, null);
        }
    }

    public void updateMedia(Scanner scanner) {
        System.out.println("Enter the tid (media ID) of the media item to update:");
        this.listMedia();
        int tid = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
    
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
    
                System.out.println("Enter the new title (or press Enter to keep the current value):");
                String newTitle = scanner.nextLine();
                if (!newTitle.isEmpty()) {
                    media.setTitle(newTitle);
                }
    
                System.out.println("Enter the new release date in the format (yyyy-MM-dd) (or press Enter to keep the current value):");
                String newReleaseDateStr = scanner.nextLine();
                if (!newReleaseDateStr.isEmpty()) {
                    media.setRelease_date(newReleaseDateStr);
                }
    
                System.out.println("Enter the new duration in minutes (or press Enter to keep the current value):");
                String newDurationStr = scanner.nextLine();
                if (!newDurationStr.isEmpty()) {
                    int newDuration = Integer.parseInt(newDurationStr);
                    media.setDuration(newDuration);
                }
    
                System.out.println("Enter the new poster URL (or press Enter to keep the current value):");
                String newPosterUrl = scanner.nextLine();
                if (!newPosterUrl.isEmpty()) {
                    media.setPoster_url(newPosterUrl);
                }
    
                System.out.println("Enter the new trailer URL (or press Enter to keep the current value):");
                String newTrailerUrl = scanner.nextLine();
                if (!newTrailerUrl.isEmpty()) {
                    media.setTrailer_url(newTrailerUrl);
                }
    
                // Update the media item in the database
                String updateSql = "UPDATE Media SET title = ?, release_date = ?, duration = ?, poster_url = ?, trailer_url = ? WHERE tid = ?";
                stmt = connection.prepareStatement(updateSql);
                stmt.setString(1, media.getTitle());
                stmt.setDate(2, Date.valueOf(media.getRelease_date()));
                stmt.setInt(3, media.getDuration());
                stmt.setString(4, media.getPoster_url());
                stmt.setString(5, media.getTrailer_url());
                stmt.setInt(6, media.getTid());
    
                int rows = stmt.executeUpdate();
                if (rows == 1) {
                    System.out.println("The media item with tid " + tid + " has been updated successfully.");
                    System.out.println("Updated media details:");
                    System.out.println(media);
                } else {
                    System.out.println("Failed to update the media item with tid " + tid + ".");
                }
            } else {
                System.out.println("No media item found with tid " + tid + ".");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
        } finally {
            DBConnection.closeResources(connection, stmt, resultSet);
        }
    }
}