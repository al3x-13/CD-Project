package cd.project.backend.database;

import java.sql.*;

public class DbConnection {
    private Connection conn = null;

    /**
     * Initializes sqlite database connection.
     * @param dbPath sqlite db path
     */
    public DbConnection(String dbPath) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
    }

    /**
     * Executes a query that uses INSERT, UPDATE or DELETE.
     * @param query sql query
     * @param params query parameters
     * @return number of affected rows
     */
    public int executeUpdate(String query, Object... params) {
        try {
            PreparedStatement statement = this.conn.prepareStatement(query);
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
    public ResultSet executeQuery(String query, Object... params) {
        try {
            PreparedStatement statement = this.conn.prepareStatement(query);
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
     * Sets auto-commit status for database connection.
     * @param value auto-commit value
     */
    public void setAutoCommit(boolean value) throws SQLException {
        this.conn.setAutoCommit(value);
    }

    /**
     * Executes all pending database queries.
     */
    public void commit() throws SQLException {
        this.conn.commit();
    }

    /**
     * Closes the db connection.
     * @return whether connection was successfully closed
     */
    public boolean close() {
        try {
            if (this.conn != null) {
                this.conn.close();
                this.conn = null;
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Failed to close db connection: " + e.getMessage());
            return false;
        }
    }
}
