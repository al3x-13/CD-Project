package cd.project.backend.database;

import java.sql.*;

public class DbConnection {
    private static Connection conn = null;

    /**
     * Initializes sqlite database connection.
     * @param dbPath sqlite db path
     */
    public static void initialize(String dbPath) throws SQLException, ClassNotFoundException {
        if (conn == null) {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        }
    }

    /**
     * Executes a query that uses INSERT, UPDATE or DELETE.
     * @param query sql query
     * @param params query parameters
     * @return number of affected rows
     */
    public static int executeUpdate(String query, Object... params) {
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to execute db update: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Executes a data query.
     * @param query sql query
     * @param params query parameters
     * @return ResultSet object
     */
    public static ResultSet executeQuery(String query, Object... params) {
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            return statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("Failed to execute db query: " + e.getMessage());
            return null;
        }
    }

    /**
     * Closes the db connection.
     * @return whether connection was successfully closed
     */
    public static boolean close() {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Failed to close db connection: " + e.getMessage());
            return false;
        }
    }
}
