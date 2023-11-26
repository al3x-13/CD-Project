package cd.project.backend.domain;

import cd.project.backend.database.DbConnection;

import java.sql.ResultSet;

public class Lounge {
    private final String id;
    private final char beachId;
    private final int maxCapacity;

    /**
     * Creates a new lounge object from an existing database entry.
     * @param db database connection
     * @param loungeId lounge id
     * @throws Exception db entry not found
     */
    public Lounge(DbConnection db, String loungeId) throws Exception {
        this.id = loungeId;
        ResultSet data = db.executeQuery("SELECT * FROM lounges WHERE id = ?", loungeId);
        if (!data.next()) throw new Exception("Failed to instantiate object: lounge id not found");
        this.beachId = data.getString("beach_id").charAt(0);
        this.maxCapacity = data.getInt("max_capacity");
    }

    public String getId() {
        return this.id;
    }

    public char getBeachId() {
        return this.beachId;
    }

    public int getMaxCapacity() {
        return this.maxCapacity;
    }
}
