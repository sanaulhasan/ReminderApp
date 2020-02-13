
package remainder;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import windows.DatabaseNotFoundError;


public class MyConnection {
    
    public Connection conn;
    public Statement st;
    public static Statement st2;
    public ResultSet rs;
    public static ResultSet rs2;
    
    public MyConnection(){
    
        try {
            
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:Reminder_db.sqlite");
           st = conn.createStatement();
           st2 = conn.createStatement();
          } catch (Exception ex) {
            DatabaseNotFoundError error = new DatabaseNotFoundError();
            error.setVisible(true);
        }finally{ try {  st.close();} catch (SQLException ex) { }}
        
        
        
        
      }
    
    
    
    
    
}
