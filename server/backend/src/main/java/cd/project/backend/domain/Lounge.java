package cd.project.backend.domain;

import cd.project.backend.database.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Lounge {
    private final String id;
    private final char beachId;
    private final int maxCapacity;

    /**
     * Creates a new lounge object.
     * @param loungeId lounge id
     * @param beachId beach id
     * @param maxCapacity max capacity
     */
    public Lounge(String loungeId, char beachId, int maxCapacity) {
        this.id = loungeId;
        this.beachId = beachId;
        this.maxCapacity = maxCapacity;
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

    @Override
    public String toString() {
        return this.id;
    }

    /**
     * Gets lounges from database by id.
     * @param db db connection
     * @param loungeIds lounge ids
     * @return Lounges
     */
    public static ArrayList<Lounge> getLoungesByID(DbConnection db, ArrayList<Integer> loungeIds) {
        ArrayList<Lounge> lounges = new ArrayList<>();

        ResultSet data = db.executeQuery("SELECT * FROM ?", loungeIds);
        while (true) {
            try {
                if (!data.next()) break;
                String id = data.getString("id");
                char beachId = data.getString("beach_id").charAt(0);
                int maxCapacity = data.getInt("max_capacity");
                lounges.add(new Lounge(id, beachId, maxCapacity));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return lounges.isEmpty() ? null : lounges;
    }
}
