package CommandPattern.userStories;

import CommandPattern.Command;
import CommandPattern.DBConnection;
import CommandPattern.JsonArrayToObject;
import CommandPattern.ResultSetConverter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class fib implements Command {
    private static Connection con = DBConnection.getInstance().getConnection();


    private void ack() throws InterruptedException {
        Thread.sleep(60000);
    }
    @Override
    public JSONObject execute(JSONObject json) {
        JSONObject result;
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM public.users");
            JSONArray array = ResultSetConverter.convertResultSetIntoJSON(resultSet);
            JsonArrayToObject converter = new JsonArrayToObject("Error retrieving Users", array);
            ack();
            result = converter.convertArray();
        } catch(Exception ex){
            ex.getMessage();
            result = new JSONObject("{\"message\":\"Error retrieving Users\"}");
        }
        return result;
    }
}
