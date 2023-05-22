import java.sql.*;
import java.util.Scanner;

public interface UserInterface {
    void run(Scanner scanner);

    default void filterByGenre() {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            System.out.println("Select the ID of genre you want to filter by");
            this.listGenres();
            int gid = InputHandler.getIDinput();

            String sql = "SELECT title FROM Media m,Media_Genres mg WHERE m.tid=mg.tid AND mg.gid = ?";
            connection = DBConnection.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, gid);
            resultSet = stmt.executeQuery();
            if (!resultSet.next()) {
                System.out.println("Sorry, we don't have any media in the genre with ID " + gid);
            } else {
                System.out.println("We have the following title in the genre with ID " + gid);
                while (true) {
                    String title = resultSet.getString("title");
                    System.out.println(title);
                    if(!resultSet.next()) break;
                }
            }

        } catch (Exception e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
        } finally {
            DBConnection.closeResources(connection, stmt, resultSet);
        }
    }

    default void filterByLanguage() {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            System.out.println("Select the ID of language you want to filter by");
            this.displayAllLanguages();
            int lid = InputHandler.getIDinput();

            String sql = "SELECT title FROM Media m,Media_Languages lg WHERE m.tid=lg.tid AND lg.lid = ? ";
            connection = DBConnection.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, lid);
            resultSet = stmt.executeQuery();
            if (!resultSet.next()) {
                System.out.println("Sorry, we don't have any media in the language with ID " + lid);
            } else {
                System.out.println("We have the following title in the language with ID " + lid);
                while (true) {
                    String title = resultSet.getString("title");
                    System.out.println(title);
                    if(!resultSet.next()) break;
                }
            }

        } catch (Exception e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
        } finally {
            DBConnection.closeResources(connection, stmt, resultSet);
        }
    }

    default void searchByTitle() {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            // Get the search keyword from the user
            System.out.print("Enter the title to search: ");
            String searchKeyword = InputHandler.getNonEmptyString();

            // Create the SQL query
            String sql = "SELECT title,tid FROM Media WHERE title LIKE ?";

            // Establish database connection
            connection = DBConnection.getConnection();

            // Prepare the statement
            stmt = connection.prepareStatement(sql);

            // Set the search keyword parameter
            stmt.setString(1, "%" + searchKeyword + "%");

            // Execute the query
            resultSet = stmt.executeQuery();

            System.out.println("Searching...");
            Boolean found = false;
            // Process the result set
            while (resultSet.next()) {
                found = true;
                int tid = resultSet.getInt("tid");
                String title = resultSet.getString("title");

                // Print the media information
                System.out.println(tid + ". " + title);

            }
            if (!found) {
                System.out.println("No media found for keyword " + searchKeyword);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
        } finally {
            // Close database resources
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(stmt);
            DBConnection.closeConnection(connection);
        }
    }

    default void getAvgRating() {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            // Create the SQL query
            String sql = "SELECT m.tid, m.title, AVG(r.rating) AS average_rating " +
                    "FROM Media m " +
                    "LEFT JOIN Ratings r ON m.tid = r.tid " +
                    "GROUP BY m.tid, m.title " +
                    "ORDER BY average_rating DESC";

            // Establish database connection
            connection = DBConnection.getConnection();

            // Prepare the statement
            stmt = connection.prepareStatement(sql);

            // Execute the query
            resultSet = stmt.executeQuery();

            // Process the result set
            System.out.println("Average Ratings for All Media (Descending Order):");
            while (resultSet.next()) {
                int tid = resultSet.getInt("tid");
                String title = resultSet.getString("title");
                double averageRating = resultSet.getDouble("average_rating");

                // Print the tid, title, and average rating for each media
                System.out.println("TID: " + tid);
                System.out.println("Title: " + title);
                System.out.println("Average Rating: " + averageRating);
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
        } finally {
            // Close database resources
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(stmt);
            DBConnection.closeConnection(connection);
        }
    }

    // helper functions

    default void listGenres() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish database connection
            connection = DBConnection.getConnection();
            statement = connection.createStatement();

            // Execute the query to retrieve genres
            String sql = "SELECT gid,genre FROM Genres";
            resultSet = statement.executeQuery(sql);

            // Process the result set
            while (resultSet.next()) {
                String genre = resultSet.getString("genre");
                int gid = resultSet.getInt("gid");
                System.out.println(gid + ". " + genre);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while accessing the database:");
            System.out.println("Error details: " + e.getMessage());
        } finally {
            // Close database resources
            DBConnection.closeResultSet(resultSet);
            DBConnection.closeStatement(statement);
            DBConnection.closeConnection(connection);
        }
    }

    default void displayAllLanguages() throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT lid, language FROM Languages ORDER BY lid";
            connection = DBConnection.getConnection();
            stmt = connection.prepareStatement(sql);
            resultSet = stmt.executeQuery();

            System.out.println("Available Languages, Select the ID of the language you want to add:");
            while (resultSet.next()) {
                int lid = resultSet.getInt("lid");
                String languageName = resultSet.getString("language");

                System.out.println(lid + ". " + languageName);
            }
        } finally {
            DBConnection.closeResources(null, stmt, resultSet);
        }
    }

    default boolean checkMediaExistence(int tid) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT tid FROM Media WHERE tid = ?";
            connection = DBConnection.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, tid);
            resultSet = stmt.executeQuery();

            return resultSet.next();
        } finally {
            DBConnection.closeResources(null, stmt, resultSet);
        }
    }

    default boolean checkLanguageExistence(int lid) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT lid FROM Languages WHERE lid = ?";
            connection = DBConnection.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, lid);
            resultSet = stmt.executeQuery();

            return resultSet.next();
        } finally {
            DBConnection.closeResources(null, stmt, resultSet);
        }
    }

    default void listMedia() {
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String query = "SELECT tid,title FROM Media";
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            System.out.println("\nThe following titles are currently available\n");

            while (resultSet.next()) {
                System.out.println(resultSet.getInt("tid") + ". " + resultSet.getString("title"));
            }
            System.out.println();

            DBConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
