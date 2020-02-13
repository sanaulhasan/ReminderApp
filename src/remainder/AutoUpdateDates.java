/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remainder;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sanaulhassan
 */
public class AutoUpdateDates implements Runnable  {
    MyConnection con = new MyConnection();
     @Override
    public void run() {
    
   autoUpdate();
    
    }
    
    
   void autoUpdate(){
    ArrayList<Integer> ids = new ArrayList<Integer>();
    while(true){ 
    try {
        
         
            con.rs =  con.st.executeQuery("SELECT id, cheque_duedate from crs_cheque_list where status=1 OR status=7");
            while(con.rs.next()){
            int id = con.rs.getInt("id");
            String due = con.rs.getString("cheque_duedate");
            Date d = new Date();
            LocalDate today = LocalDate.now();
            Date  date1 = new SimpleDateFormat("d-MMM-yyyy", Locale.ENGLISH).parse(due);
            LocalDate date2 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
             if(date1.compareTo(d)<0){
               
            ids.add(id);
               
             }
            }
          
            for(int i=0;i<ids.size();i++){
            String query = "UPDATE crs_cheque_list SET status=2  where id="+ids.get(i)+"";
            con.st.executeUpdate(query);
            }
            
           
            
         
            
            
     }catch(Exception e){}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
        try {
            Thread.sleep(1000*60*60*4);
        } catch (InterruptedException ex) {
            Logger.getLogger(AutoUpdateDates.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
}
