import java.sql.*;
import java.util.Scanner;

public interface UserInterface {
    void run(Scanner scanner);

    default void listMedia() {
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String query = "SELECT tid,title FROM Media";
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            System.out.println("\nThe following titles are currently available\n");

            while (resultSet.next()) {
                System.out.println(resultSet.getInt("tid")+ ". " + resultSet.getString("title"));
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
    // default filter by genre, filter by language, sort by rating, get avg rating of a movie,search by title
}
