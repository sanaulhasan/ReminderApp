/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remainder;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import windows.NotificationPopup;


public class ShowAlerts implements Runnable{
    public static int alert_id = 0;
    MyConnection con = new MyConnection();
     @Override
    public void run() {
    
   alert();
    
    }
    
    void alert(){
    
    while(true){
         int time_repeat = 5000;
        try{
        String value = "";
        String before = "";
       int dis = 1;
        int days = 0;
         try {
            con.rs =  con.st.executeQuery("SELECT remind_repeat, remind_b_days, distrb from crs_setting");
           
            while(con.rs.next()){
           
           
           value = con.rs.getString("remind_repeat") ;
           before = con.rs.getString("remind_b_days") ;
           dis = con.rs.getInt("distrb") ; 
            if(value.equals("20 Minuts")){
            time_repeat = 1000*60*20;
            
            }
            else if(value.equals("30 Minuts")){
            time_repeat = 1000*60*30;
            
            }
            
             else if(value.equals("1 hour")){
            time_repeat = 1000*60*60;
            
            }
             else if(value.equals("2 hour")){
            time_repeat = 1000*60*60*2;
            
            }
             else if(value.equals("3 hour")){
            time_repeat = 1000*60*60*3;
            
            }
             else if(value.equals("4 hour")){
            time_repeat = 1000*60*60*4;
            
            }
             else if(value.equals("5 hour")){
            
            time_repeat = 1000*60*60*4;
            }
           
           
            if(before.equals("1 Day")){
            days = 1;
            
            }
            
            else if(before.equals("2 Days")){
            days = 2;
            
            }
            
            else if(before.equals("3 Days")){
            days = 3;
            
            }
            
            else if(before.equals("4 Days")){
            days = 4;
            
            }
            
            else if(before.equals("5 Days")){
            days = 5;
            
            }
            
           
           
            
            }
}catch(Exception e){System.out.print(e);}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
        
        
        if(dis==1){
            con.rs =  con.st.executeQuery("SELECT id, cheque_duedate from crs_cheque_list where status=1 OR status=7 OR status=2");
            while(con.rs.next()){
            int id = con.rs.getInt("id");
            
            String due = con.rs.getString("cheque_duedate");
            
          
            LocalDate today = LocalDate.now();
            Date  date1 = new SimpleDateFormat("d-MMM-yyyy", Locale.ENGLISH).parse(due);
            LocalDate date2 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            Period p = Period.between(today,date2);
            int year = p.getYears();
            int month = p.getMonths();
            int day = p.getDays();
            
            String time = day+" Days ";
            if(day<=days && day>=0){
            if(month==0){
            if(year==0){
            alert_id = id;
            
            NotificationPopup notification = new NotificationPopup();
            notification.setVisible(true);
           // System.out.println(id);
            
           // System.out.println("days before" +days);
           // System.out.println(time_repeat);
            
            
             }
            }
            }
            
            
            
            }
        }
    
    
    }catch(Exception e){}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
        try {
            Thread.sleep(time_repeat);
        } catch (InterruptedException ex) {
            Logger.getLogger(ShowAlerts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
    
}
