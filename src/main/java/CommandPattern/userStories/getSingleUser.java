package CommandPattern.userStories;
import CommandPattern.JsonArrayToObject;
import CommandPattern.ResultSetConverter;
import CommandPattern.Command;
import CommandPattern.DBConnection;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class getSingleUser implements Command {
    private static Connection con = DBConnection.getInstance().getConnection();
    @Override
    public JSONObject execute(JSONObject json) {
        JSONObject result;
        try {
            String name = json.getString("username");
            PreparedStatement statement = con.prepareStatement("SELECT * from public.users WHERE  username = ?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            JSONArray array = ResultSetConverter.convertResultSetIntoJSON(resultSet);
            JsonArrayToObject converter = new JsonArrayToObject("User not found", array);
            result = converter.convertArray();
        } catch(Exception ex){
            ex.getMessage();
            result = new JSONObject("{\"message\":\"Error getting the User\"}");
        }
        return result;
    }
}
