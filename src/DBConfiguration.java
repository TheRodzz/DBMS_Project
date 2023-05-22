public class DBConfiguration {
    // Configuration parameters for connecting to the database
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ottplatform?characterEncoding=latin1&useConfigs=maxPerformance";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "7349";

    /**
     * Retrieves the URL of the database.
     *
     * @return The URL of the database.
     */
    public static String getDbUrl() {
        return DB_URL;
    }

    /**
     * Retrieves the username for accessing the database.
     *
     * @return The database username.
     */
    public static String getUsername() {
        return USERNAME;
    }

    /**
     * Retrieves the password for accessing the database.
     *
     * @return The database password.
     */
    public static String getPassword() {
        return PASSWORD;
    }
}
