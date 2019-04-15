package CommandPattern.userStories;

import CommandPattern.Command;
import CommandPattern.DBConnection;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class updateLastName implements Command {
    private static Connection con = DBConnection.getInstance().getConnection();



    @Override
    public JSONObject execute(JSONObject json) {
        JSONObject result;
        try {
            PreparedStatement myProc = con.prepareStatement("call updatelastname(?, ?) ");
            myProc.setString(1,json.getString("lastName"));
            myProc.setString(2,json.getString("username"));
            myProc.execute();
            myProc.close();
            result = new JSONObject("{\"message\":\"Last Name updated\"}");
        } catch(Exception ex){
            ex.getMessage();
            result = new JSONObject("{\"message\":\"Failed to update\"}");
        }
        return result;
    }
}

