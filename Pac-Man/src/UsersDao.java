import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class UsersDao implements IDao<Users> {
    
    Connection conn;
    ResultSet rs;
    PreparedStatement pst;

    public UsersDao() {
        conn = DBConnect.ConnectDB();
    }
    public void ConnectionClose()
    {
        try {
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public Users get(String UserName, String Password)
    {
        String query = "SELECT * FROM Players WHERE name = ? AND password = ?";
        try{
            pst = conn.prepareStatement(query);
            pst.setString(1, UserName);
            pst.setString(2, Password);
            rs = pst.executeQuery();
            if(rs.next()){
                try
                {
                    Users getUsers = new Users(
                rs.getString("Password"), rs.getString("Name"));
                     return getUsers;
                }catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Incorrent username/password");
            }
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
		return null;
    }
    @Override
    public void save(Users t) {
        try{
            String query = "INSERT INTO Players (name, password) VALUES (?,?)";
            pst = conn.prepareStatement(query);
            pst.setString(1, t.getUsername());
            pst.setString(2, t.getPassword());
            pst.execute();
            JOptionPane.showMessageDialog(null, "New customer addeed");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }

    @Override
    public void update(Users t, String[] params) {

    }

    @Override
    public void remove(Users t) {

    }
    
}