
package windows;

import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import remainder.MyConnection;
import remainder.ShowAlerts;
import static windows.ApplicationIndex.idget;

public class NotificationPopup extends javax.swing.JFrame {
      MyConnection con = new MyConnection();
      Clip clip;
    public NotificationPopup() {
        initComponents();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {}
        setFramePositionAndSound();
        
        populateNotificationPanel();
        
    }
    
   public void populateNotificationPanel(){
        
       try {
            con.rs =  con.st.executeQuery("SELECT user_name from crs_setting");
           
            while(con.rs.next()){
           
           
            jLabel12.setText(con.rs.getString("user_name")) ;
           
            
            }
}catch(Exception e){System.out.print(e);}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
       
       
      //  jPanel1.setBackground(new java.awt.Color(0,178,227));
   try{
   con.rs =  con.st.executeQuery("SELECT id, customer_name, cheque_no, bank_name, cheque_duedate from crs_cheque_list where id="+ShowAlerts.alert_id);
            while(con.rs.next()){
            int id = con.rs.getInt("id");
            String name = con.rs.getString("customer_name");
            String chq_no = con.rs.getString("cheque_no");
            String bank = con.rs.getString("bank_name");
            String due = con.rs.getString("cheque_duedate");
            jLabel6.setText(name);
            jLabel5.setText(bank);
            jLabel7.setText(chq_no);
            
            LocalDate today = LocalDate.now();
           Date  date1 = new SimpleDateFormat("d-MMM-yyyy", Locale.ENGLISH).parse(due);
           LocalDate date2 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
           Period p = Period.between(today,date2);
           int year = p.getYears();
           int month = p.getMonths();
           int day = p.getDays();
           String time = day+" Days ";
           if(month>0){
           time+= month+" Month ";
           }
           if(year>0){
           time+= year+" Year";
           }
            jLabel1.setText(time);
            
            }
   }catch(Exception e){
                  
                  }finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
   
   
   
   }

    public void setFramePositionAndSound(){
    
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
    /*
        For Top Right
          int x = (int) rect.getMaxX() - this.getWidth();
          int y = 0;
        
        For Top Left
          int x = 0;
          int y = 0;
        
        For Bottom Right
          int x = (int) rect.getMaxX() - this.getWidth();
        int y = (int) rect.getMaxY() - this.getHeight();
        
        For Bottom Left
         int = 0;
         int y = (int) rect.getMaxY() - this.getHeight();
        
        
      */  
        int x = (int) rect.getMaxX() - this.getWidth();
        int y = (int) rect.getMaxY() - this.getHeight();
        
        this.setLocation(x, y);
        this.setVisible(true);
    
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource("chequeAlert.wav")); 
            clip  = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
            clip.loop(40);
             } catch (Exception ex) {
            
       }
        
        
    
    }
    
    
    
    
    void openDetal(){
    
    ApplicationIndex index = new ApplicationIndex();
       index.setVisible(true);
    
    
    dispose();
    }
    
    void openSpecific(){
    
        idget = ShowAlerts.alert_id;
        ChequeDetail start = new ChequeDetail();
        start.setVisible(true);
       
    }
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Alert");
        setAlwaysOnTop(true);
        setPreferredSize(new java.awt.Dimension(490, 225));
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        getContentPane().setLayout(null);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/windows/clock2.png"))); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(10, 20, 149, 130);

        jButton3.setBackground(new java.awt.Color(153, 204, 255));
        jButton3.setText("Close");
        jButton3.setToolTipText("Close Notification");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(370, 170, 100, 29);

        jLabel1.setFont(new java.awt.Font("Heiti TC", 0, 14)); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(280, 130, 260, 30);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setToolTipText("Click here to Open Detal of Cheque");
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1MouseEntered(evt);
            }
        });
        jPanel1.setLayout(null);

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 153));
        jLabel5.setText("Unknown");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(110, 30, 260, 30);

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 153));
        jLabel6.setText("Unknown");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(110, 10, 320, 30);

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 153, 153));
        jLabel7.setText("Unknown");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(110, 50, 260, 30);

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel8.setText("CHQ name:  ");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(20, 10, 320, 30);

        jLabel9.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel9.setText("Bank:  ");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(60, 30, 260, 30);

        jLabel10.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel10.setText("CHQ no:  ");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(40, 50, 260, 30);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(130, 40, 340, 90);

        jLabel12.setFont(new java.awt.Font("Heiti TC", 0, 14)); // NOI18N
        jLabel12.setText(" ");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(170, 10, 260, 30);

        jButton5.setBackground(new java.awt.Color(153, 204, 255));
        jButton5.setText("Open");
        jButton5.setToolTipText("Open Application");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5);
        jButton5.setBounds(250, 170, 120, 29);

        jLabel13.setFont(new java.awt.Font("Heiti TC", 0, 14)); // NOI18N
        jLabel13.setText("Dear ");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(130, 15, 150, 20);

        jLabel14.setFont(new java.awt.Font("Heiti TC", 0, 14)); // NOI18N
        jLabel14.setText("Has Remaining Days:  ");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(130, 130, 260, 30);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/windows/back.jpg"))); // NOI18N
        jLabel3.setText("jLabel3");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(0, 0, 520, 290);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       clip.stop();
        
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        clip.stop();
        openSpecific();
        
    }//GEN-LAST:event_jPanel1MouseClicked

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
       clip.stop();
       dispose();
    }//GEN-LAST:event_formMouseClicked

    private void jPanel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseEntered
        
    }//GEN-LAST:event_jPanel1MouseEntered

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        clip.stop();
        openDetal();
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NotificationPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NotificationPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NotificationPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NotificationPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NotificationPopup().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
