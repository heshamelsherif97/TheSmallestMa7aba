package CommandPattern.userStories;

import CommandPattern.Command;
import CommandPattern.DBConnection;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class updateFirstName implements Command {
        private static Connection con = DBConnection.getInstance().getConnection();

        public static String hashPassword(String password) {

            String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            return bcryptHashString;

        }

        @Override
        public JSONObject execute(JSONObject json) {
            JSONObject result;
            try {
                PreparedStatement myProc = con.prepareStatement("call updatefirstname(?, ?) ");
                myProc.setString(1, json.getString("firstName"));
                myProc.setString(2, json.getString("username"));
                myProc.execute();
                myProc.close();
                result = new JSONObject("{\"message\":\"First Name updated\"}");
            } catch (Exception ex) {
                ex.getMessage();
                result = new JSONObject("{\"message\":\"Failed to update\"}");
            }
            return result;
        }
    }

