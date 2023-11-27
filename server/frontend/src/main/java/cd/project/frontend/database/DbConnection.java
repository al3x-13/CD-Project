package cd.project.frontend.database;

import java.sql.*;

public class DbConnection {
    private static Connection conn;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://" + System.getenv("DB_URL"),
                    System.getenv("DB_USERNAME"),
                    System.getenv("DB_PASSWORD")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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
