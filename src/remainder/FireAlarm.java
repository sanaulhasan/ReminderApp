
package remainder;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import static remainder.ShowAlerts.alert_id;
import windows.AlertPopup;
import windows.NotificationPopup;


public class FireAlarm implements Runnable{
    MyConnection con = new MyConnection();
    public static long c_sec = 0;
    
    public static String c_title = "";
    @Override
    public void run() {
  
    alarm();
     }
    
    
    
    public void alarm(){
    ArrayList<Integer> ids = new ArrayList<Integer>();
      while(true){
           
         try{
            con.rs =  con.st.executeQuery("SELECT * from crs_otherAlerts where status=1");
            while(con.rs.next()){
            int id = con.rs.getInt("id");
            
            c_title = con.rs.getString("title");
            
            String date = con.rs.getString("date");
            int hour = con.rs.getInt("hour");
            int mint = con.rs.getInt("mint");
            
            
            LocalDate today = LocalDate.now();
            Date  date1 = new SimpleDateFormat("d-MMM-yyyy", Locale.ENGLISH).parse(date);
            LocalDate date2 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            Period p = Period.between(today,date2);
            int year = p.getYears();
            int month = p.getMonths();
            int day = p.getDays();
            
            
            if(day==0){
            if(month==0){
            if(year==0){
               ids.add(id);
               
               LocalTime now = LocalTime.now();
               int h = now.getHour();
               int m =  now.getMinute();
               
               if(h<=hour){
               
                   int nowtime = ((h*60)+m)*60;
               int giventime = ((hour*60)+mint)*60;
               
               c_sec = giventime - nowtime;
               
           FireThread fire = new FireThread();
           new Thread(fire).start();
               
               
               
               }
             
             }}}
           } // end statement loop
            
            
           
            for(int i=0;i<ids.size();i++){
            String query ="Update crs_otherAlerts SET status=0 where id="+ids.get(i)+"";
            con.st.executeUpdate(query);
            
            }
            
    }catch(Exception e){}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
         // 1000*60*60*4
         try {
                Thread.sleep(1000*60*60*2);
            } catch (InterruptedException ex) {
                Logger.getLogger(FireAlarm.class.getName()).log(Level.SEVERE, null, ex);
            }
    } // end of main loop
  
    
    
    }
    
    public class FireThread implements Runnable{

        @Override
        public void run() {
       
         Thread t = new Thread(this);
               long sec = FireAlarm.c_sec*1000;
               String title = FireAlarm.c_title;
            try {
                t.sleep(sec);
            } catch (InterruptedException ex) {
                Logger.getLogger(FireAlarm.class.getName()).log(Level.SEVERE, null, ex);
            }
        
                   
               AlertPopup popup = new AlertPopup();
               
               
               popup.analyzeTime(title);
        
        }




}
    
    
    
}


