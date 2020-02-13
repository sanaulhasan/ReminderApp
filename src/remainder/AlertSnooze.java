/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remainder;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import windows.AlertPopup;

/**
 *
 * @author sanaulhassan
 */
public class AlertSnooze implements Runnable{
MyConnection con = new MyConnection();
    @Override
    public void run() {
   
     int mint = 0;
        try {
            con.rs =  con.st.executeQuery("SELECT snooze from crs_setting ");
           
            while(con.rs.next()){
           
          mint =  con.rs.getInt("snooze");
            
            }
}catch(Exception e){}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
    
    
        
    try {
        Thread.sleep(1000*60*mint);
    } catch (InterruptedException ex) {
        Logger.getLogger(AlertSnooze.class.getName()).log(Level.SEVERE, null, ex);
    }
        
    
    AlertPopup p = new AlertPopup();
    p.analyzeTime(AlertPopup.title1);
    }

    
    
}
