package CommandPattern.userStories;

import CommandPattern.Command;
import CommandPattern.DBConnection;
import CommandPattern.JsonArrayToObject;
import CommandPattern.ResultSetConverter;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class getAllUsers implements Command {
    private static Connection con = DBConnection.getInstance().getConnection();
    @Override
    public JSONObject execute(JSONObject json) {
        JSONObject result;
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM public.users");
            JSONArray array = ResultSetConverter.convertResultSetIntoJSON(resultSet);
            JsonArrayToObject converter = new JsonArrayToObject("Error retrieving Users", array);
            result = converter.convertArray();
        } catch(Exception ex){
            ex.getMessage();
            result = new JSONObject("{\"message\":\"Error retrieving Users\"}");
        }
        return result;
    }
    }