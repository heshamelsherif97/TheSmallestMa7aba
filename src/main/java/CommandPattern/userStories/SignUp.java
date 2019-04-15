package CommandPattern.userStories;

import CommandPattern.Command;
import CommandPattern.DBConnection;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.json.JSONObject;

import java.security.*;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;



public class SignUp implements Command {
    private static Connection con = DBConnection.getInstance().getConnection();

public static String hashPassword(String password){

    String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
    return bcryptHashString;

}
//"Level 0 Statement , level 1 prepared Statement(no out ) , level 2 callable statement ( in and out )"

    public boolean getGender(String s){
    if (s.equals("male")){
        return true;
    }else{
        return false;
    }
    }

    public int[] getDate(String s){
    String [] x  = s.split("-");
    int [] y = new int[x.length];
        for (int i = 0; i < x.length; i++) {
            y[i] = Integer.parseInt(x[0]);
        }
        return y;
    }
    @Override
    public JSONObject execute(JSONObject json) {
    JSONObject result;
    try {
       PreparedStatement myProc = con.prepareStatement("call insertuser(?, ?, ?, ?, ?, ?, ?) ");

        myProc.setString(1,json.getString("firstName"));
        myProc.setString(2,json.getString("lastName"));
        myProc.setString(3,json.getString("email"));
        myProc.setString(4,json.getString("username"));
        myProc.setBoolean(5,getGender(json.getString("gender")));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, getDate(json.getString("birthdate"))[2]);
        calendar.set(Calendar.DAY_OF_MONTH, getDate(json.getString("birthdate"))[0]);
        calendar.set(Calendar.MONTH, getDate(json.getString("birthdate"))[1]);
        java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
        myProc.setDate(6, date);
        myProc.setString(7,hashPassword(json.getString("password")));
        myProc.execute();
        myProc.close();
        result = new JSONObject("{\"message\":\"Registered Successfully\"}");
    } catch (Exception ex){
        System.out.print(ex.getMessage());
        result = new JSONObject("{\"message\":\"Failed to register\"}");
    }
    return result;
    }
}




