package cd.project.backend.domain;

import cd.project.backend.database.DbConnection;
import jakarta.xml.bind.annotation.XmlType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@XmlType(name = "Lounge")
public class Lounge {
    private String id;
    private char beachId;
    private int maxCapacity;

    public Lounge() {}

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

    public void setId(String id) {
        this.id = id;
    }

    public void setBeachId(char beachId) {
        this.beachId = beachId;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public String toString() {
        return this.id;
    }

    /**
     * Gets lounges from database by id.
     * @param loungeIds lounge ids
     * @return Lounges
     */
    public static ArrayList<Lounge> getLoungesByID(ArrayList<String> loungeIds) {
        ArrayList<Lounge> lounges = new ArrayList<>();

        // generate list of id values for query
        StringBuilder queryPlaceholders = new StringBuilder();

        for (int i = 0; i < loungeIds.size(); i++) {
            queryPlaceholders.append("?");
            if (i != loungeIds.size() - 1) {
                queryPlaceholders.append(",");
            }
        }

        ResultSet data = DbConnection.executeQueryWithArraylist(
                "SELECT * FROM lounges WHERE id in (" + queryPlaceholders + ")",
                loungeIds
        );

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
        return lounges;
    }
}
