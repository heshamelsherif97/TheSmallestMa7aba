package CommandPattern.userStories;

import CommandPattern.Command;
import CommandPattern.DBConnection;
import CommandPattern.JsonArrayToObject;
import CommandPattern.ResultSetConverter;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class login implements Command {
    private static Connection con = DBConnection.getInstance().getConnection();
    public JSONObject execute(JSONObject json) {
        JSONObject result;
        try {
            String name = json.getString("username");
            String password = json.getString("password");
            PreparedStatement statement = con.prepareStatement("SELECT * from public.users WHERE  username = ?");
            String oldPass = "";
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                oldPass = resultSet.getString("userpassword");
            }
            statement.close();
            BCrypt.Result result2 = BCrypt.verifyer().verify(json.getString("password").toCharArray(), oldPass);
            if (result2.verified){
                result = new JSONObject("{\"message\":\"Logged In\"}");
            }
            else{
                result = new JSONObject("{\"message\":\"Wrong Username or Password\"}");
            }
        } catch(Exception ex){
            ex.getMessage();
            System.out.print(ex.getMessage());
            result = new JSONObject("{\"message\":\"Error getting the User\"}");
        }
        return result;
    }
}
