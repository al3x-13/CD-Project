package cd.project.backend.database;

import java.sql.*;

public class DbConnection {
    private static Connection conn = null;

    public static void initialize(String dbPath) throws SQLException, ClassNotFoundException {
        if (conn == null) {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        }
    }

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
