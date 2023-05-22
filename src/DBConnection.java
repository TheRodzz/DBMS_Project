import java.sql.*;

public class DBConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ottplatform?characterEncoding=latin1&useConfigs=maxPerformance";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "7349";

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false); // Disable auto-commit

            // System.out.println("Connected to the database");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not find database driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Could not connect to the database: " + e.getMessage());
            throw e;
        }

        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.commit();
                connection.close();
                // System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("Failed to rollback the transaction: " + rollbackEx.getMessage());
            }
            System.out.println("Failed to close the connection: " + e.getMessage());
        }
    }

    public static void closeResources(Connection connection, Statement statement, ResultSet resultSet) {
        closeResultSet(resultSet);
        closeStatement(statement);
        closeConnection(connection);
    }

    public static void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while closing the result set: " + e.getMessage());
            // Log the exception
        }
    }

    public static void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while closing the statement: " + e.getMessage());
            // Log the exception
        }
    }

}
