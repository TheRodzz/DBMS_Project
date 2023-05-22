import java.sql.*;

public class DBConnection{
    // private static final String DB_URL = "jdbc:mysql://localhost:3306/ottplatform?characterEncoding=latin1&useConfigs=maxPerformance";
    // private static final String USERNAME = "root";
    // private static final String PASSWORD = "7349";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                // Register the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Create a new connection
                connection = DriverManager.getConnection(DBConfiguration.getDbUrl(), DBConfiguration.getUsername(), DBConfiguration.getPassword());
                connection.setAutoCommit(false); // Disable auto-commit
            }
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
        
    public static void commitTransaction(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.commit();
            }
        } catch (SQLException e) {
            System.out.println("Failed to commit the transaction: " + e.getMessage());
        }
    }

    public static void rollbackTransaction(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Failed to rollback the transaction: " + e.getMessage());
        }
    }

}
