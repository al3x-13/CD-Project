package cd.project.backend.database;

import java.sql.*;
import java.util.ArrayList;

public class DbConnection {
    private static Connection conn;

    // Initializes DB connection
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
     * Executes a data query that takes an ArrayList as the query parameters.
     * @param query sql query
     * @param params query parameters as arraylist
     * @return ResultSet object
     */
    public static ResultSet executeQueryWithArraylist(String query, ArrayList<String> params) {
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }
            return statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("Failed to execute db query: " + e.getMessage());
            return null;
        }
    }

    /**
     * Sets auto-commit status for database connection.
     * @param value auto-commit value
     */
    public static void setAutoCommit(boolean value) throws SQLException {
        conn.setAutoCommit(value);
    }

    /**
     * Executes all pending database queries.
     */
    public static void commit() throws SQLException {
        conn.commit();
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

    /**
     * Creates a sql VARCHAR array from a string list.
     * @param data string list
     * @return SQL VARCHAR array
     */
    public static Array stringListToVarcharArray(ArrayList<String> data) {
        String[] dataArray = data.toArray(new String[0]);
        try {
            return conn.createArrayOf("varchar", dataArray);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
