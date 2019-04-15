package CommandPattern;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class DBConnection {
    private static DBConnection dbInstance;
    private static Connection con;
    private DBConnection() {
        // private constructor //
    }
    public static DBConnection getInstance() {
        if (dbInstance == null) {
            dbInstance = new DBConnection();
        }
        return dbInstance;
    }
    public Connection getConnection() {
        if (con == null) {
            //String url = "jdbc:postgresql://127.0.0.1:26257/";
            String url = "jdbc:postgresql://localhost:5432/";
            String dbName = "scalable?sslmode=disable";
            String driver = "org.postgresql.Driver";
            String userName = "postgres";
            String password = "";
            try {
                Class.forName(driver).newInstance();
                this.con = DriverManager.getConnection(url + dbName, userName, password);
//                System.out.println(this.con);
            } catch (Exception ex) {
                System.out.print(ex);
            }
        }
        return con;
    }
//"Level 0 Statement , level 1 prepared Statement(no out ) , level 2 callable statement ( in and out )"
  public static void main(String[] args) {
       con = DBConnection.getInstance().getConnection();
       System.out.println(con);
   }
}