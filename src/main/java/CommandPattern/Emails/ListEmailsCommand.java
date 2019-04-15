package CommandPattern.Emails;

import CommandPattern.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListEmailsCommand {
    static Connection con = DBConnection.getInstance().getConnection();

    public static void selectEmail() throws SQLException {
        ResultSet res = con.createStatement().executeQuery("SELECT * FROM email");
        while (res.next()) {

            System.out.println(res.getString("sender"));
        }
    }


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Connect to the "email" database.
        try {
            selectEmail();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
