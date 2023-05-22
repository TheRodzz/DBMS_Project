public class DBConfiguration {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ottplatform?characterEncoding=latin1&useConfigs=maxPerformance";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "7349";

    public static String getDbUrl() {
        return DB_URL;
    }

    public static String getUsername() {
        return USERNAME;
    }

    public static String getPassword() {
        return PASSWORD;
    }
}
