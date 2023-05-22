import java.sql.*;

public class DBConnection {
    private static Connection connection;

    /**
     * Retrieves a connection to the database.
     *
     * @return The database connection.
     * @throws SQLException if a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                // Register the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Create a new connection using DBConfiguration parameters
                connection = DriverManager.getConnection(DBConfiguration.getDbUrl(), DBConfiguration.getUsername(),
                        DBConfiguration.getPassword());
                connection.setAutoCommit(false); // Disable auto-commit for transaction management
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Could not find database driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Could not connect to the database: " + e.getMessage());
            throw e;
        }

        return connection;
    }

    /**
     * Closes the database connection.
     *
     * @param connection The database connection to close.
     */
    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.commit(); // Commit any pending changes before closing
                connection.close();
                // System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback changes if closing the connection fails
            } catch (SQLException rollbackEx) {
                System.out.println("Failed to rollback the transaction: " + rollbackEx.getMessage());
            }
            System.out.println("Failed to close the connection: " + e.getMessage());
        }
    }

    /**
     * Closes the database resources: result set, statement, and connection.
     *
     * @param connection The database connection to close.
     * @param statement  The statement to close.
     * @param resultSet  The result set to close.
     */
    public static void closeResources(Connection connection, Statement statement, ResultSet resultSet) {
        closeResultSet(resultSet);
        closeStatement(statement);
        closeConnection(connection);
    }

    /**
     * Closes the result set.
     *
     * @param resultSet The result set to close.
     */
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

    /**
     * Closes the statement.
     *
     * @param statement The statement to close.
     */
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

    /**
     * Commits a transaction.
     *
     * @param connection The database connection associated with the transaction.
     */
    public static void commitTransaction(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.commit();
            }
        } catch (SQLException e) {
            System.out.println("Failed to commit the transaction: " + e.getMessage());
        }
    }

    /**
     * Rolls back a transaction.
     *
     * @param connection The database connection associated with the transaction.
     */
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
