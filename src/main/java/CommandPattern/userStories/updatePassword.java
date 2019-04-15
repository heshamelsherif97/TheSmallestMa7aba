package CommandPattern.userStories;

import CommandPattern.Command;
import CommandPattern.DBConnection;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class updatePassword implements Command {
    private static Connection con = DBConnection.getInstance().getConnection();

    public static String hashPassword(String password) {

        String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        return bcryptHashString;

    }

    @Override
    public JSONObject execute(JSONObject json) {
        JSONObject result;
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * from public.users WHERE  username = ?");
            statement.setString(1, json.getString("username"));
            ResultSet resultSet = statement.executeQuery();
            String oldPass = "";
            while (resultSet.next()) {
                oldPass = resultSet.getString("userpassword");
            }
            BCrypt.Result result2 = BCrypt.verifyer().verify(json.getString("oldPassword").toCharArray(), oldPass);
            statement.close();
            if(result2.verified) {
                PreparedStatement myProc = con.prepareStatement("call updatepassword(?, ?) ");
                myProc.setString(2, json.getString("username"));
                myProc.setString(1, hashPassword(json.getString("newPassword")));
                myProc.execute();
                myProc.close();
                result = new JSONObject("{\"message\":\"Password updated\"}");
            }
            else{
                result = new JSONObject("{\"message\":\"Failed to update\"}");
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            result = new JSONObject("{\"message\":\"Failed to update\"}");
        }
        return result;
    }
}

