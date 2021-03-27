import java.sql.*;
import javax.swing.JOptionPane;
public class DBConnect {
    Connection conn;
    
    public static Connection ConnectDB()
    {
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:E:\\sqlite\\Players.db");
            System.out.println("connected to database");
            return conn;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
            return null;
        }
    }
}