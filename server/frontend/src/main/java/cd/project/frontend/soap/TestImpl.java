package cd.project.frontend.soap;

import cd.project.frontend.database.DbConnection;
import jakarta.jws.WebService;

import java.sql.ResultSet;
import java.sql.SQLException;

@WebService(endpointInterface = "cd.project.frontend.soap.Test")
public class TestImpl implements Test {
    @Override
    public String test(int id) {
        ResultSet data = DbConnection.executeQuery("SELECT username FROM users WHERE id = ?", id);
        try {
            if (data == null) return "Not found";
            data.next();
            return data.getString("username");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
