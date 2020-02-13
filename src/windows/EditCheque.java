
package windows;

import java.awt.Color;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.swing.UIManager;
import remainder.MyConnection;
import remainder.TextAutoSuggest;


public class EditCheque extends javax.swing.JFrame {
          MyConnection con = new MyConnection();
          static int c_status = 0;
          static String c_name = "";
          static String c_no = "";
          
    public EditCheque() {
         try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {}
        initComponents();
        this.setLocationRelativeTo(null);
        
        populateFields();
        TextAuto();
      }

    
    public void populateFields(){
    
     try {
            
            con.rs =  con.st.executeQuery("SELECT * from crs_cheque_list INNER JOIN crs_status_list "
                    + "ON crs_cheque_list.status = crs_status_list.id where crs_cheque_list.id="+ApplicationIndex.idget+"");
           
            while(con.rs.next()){
            
            
            String chq_type = con.rs.getString("cheque_type");
            String bank = con.rs.getString("bank_name");
             String inhand = con.rs.getString("cheque_inhand");
            String due_date = con.rs.getString("cheque_duedate");
            int status = con.rs.getInt("status");
            c_status = status;
            c_name = con.rs.getString("customer_name");
            c_no =   con.rs.getString("cheque_no");      
            jTextField1.setText(con.rs.getString("customer_name"));
            jTextField1.selectAll();
            
            jTextField2.setText(con.rs.getString("customer_location"));
             jTextField2.selectAll();
            jTextField3.setText(con.rs.getString("cheque_no"));
            
            jTextField5.setText(con.rs.getString("cheque_amount"));
            jTextField8.setText(bank);
            jTextField7.setText(chq_type);
            jTextField4.setText(inhand);
            
            
            
            Date  date1 = new SimpleDateFormat("d-MMM-yyyy", Locale.ENGLISH).parse(due_date);
            
            
            jDateChooser1.setDate(date1);
           
            }
        }catch(Exception e){}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
     
    
    
    }
    
    
    
    public void TextAuto(){
    
          ArrayList<String> items = new ArrayList<String>();
          ArrayList<String> items2 = new ArrayList<String>();
           ArrayList<String> bank = new ArrayList<String>();
            ArrayList<String> type = new ArrayList<String>();
             ArrayList<String> inhand = new ArrayList<String>();
        
        try {
        //   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        
            con.rs =  con.st.executeQuery("SELECT DISTINCT(customer_name) from crs_cheque_list");
            while(con.rs.next()){
            String name = con.rs.getString("customer_name");
           items.add(name);
           }
            
            con.rs =  con.st.executeQuery("SELECT DISTINCT(customer_location) from crs_cheque_list");
            while(con.rs.next()){
            String name = con.rs.getString("customer_location");
           items2.add(name);
           }
            
            //crs_bank_list (`bank_name`)
            //crs_cheque_inhand_to name
            //crs_chequetype_list (`type`)
            con.rs =  con.st.executeQuery("SELECT bank_name from crs_bank_list");
            while(con.rs.next()){
            String name = con.rs.getString("bank_name");
           bank.add(name);
           }
            
            
            con.rs =  con.st.executeQuery("SELECT type from crs_chequetype_list");
            while(con.rs.next()){
            String name = con.rs.getString("type");
           type.add(name);
           }
            
            con.rs =  con.st.executeQuery("SELECT name from crs_cheque_inhand_to");
            while(con.rs.next()){
            String name = con.rs.getString("name");
           inhand.add(name);
           }
            
            
            
            
           }catch (Exception ex){}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
        
        TextAutoSuggest t = new TextAutoSuggest(jTextField1,items);
        TextAutoSuggest t2 = new TextAutoSuggest(jTextField2,items2);
        TextAutoSuggest t3 = new TextAutoSuggest(jTextField8,bank);
        TextAutoSuggest t4 = new TextAutoSuggest(jTextField7,type);
        TextAutoSuggest t5 = new TextAutoSuggest(jTextField4,inhand);
         
         
         
    }
    
   
    
    public void insertRecord(){
         
        String name = jTextField1.getText();
        String location = jTextField2.getText();
        String chq_no = jTextField3.getText();
        String chq_amount = jTextField5.getText();
        String bank = jTextField8.getText();
        String chq_type = jTextField7.getText();
        String chq_inhand = jTextField4.getText();
        Date dueDate1 = jDateChooser1.getDate();
        Date addeddate1 = new Date();
        int status=0;
        SimpleDateFormat format = new SimpleDateFormat("d-MMM-yyyy");
        String addeddate = format.format(addeddate1);
        String datedue = format.format(dueDate1);
        
           LocalDate today = LocalDate.now();
           LocalDate date2 = dueDate1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
           Period p = Period.between(today,date2);
           int year = p.getYears();
           int month = p.getMonths();
           int day = p.getDays();
        if(day>0){
        if(month>=0){
        if(year>=0){
        status = 1;
        }}}
        else if(day<=0 && c_status==1){
            
            
        status = 2;
        }
        else {
        status = c_status;
        }
       try {
        String record = "CHQ of Name:  "+c_name.toUpperCase()+",  CHQ no:  "+c_no+"  was Edited";      
        Date d = new Date();
        con.st.executeUpdate("INSERT into crs_history (`name`, `date_of_done`,`FK_id`) values('"+record+"','"+d+"','"+ApplicationIndex.idget+"')") ;  
          
     String query = "UPDATE crs_cheque_list SET "
             + "customer_name= '"+name+"', "
             + "customer_location= '"+location+"',"
             + "cheque_no= '"+chq_no+"',"
             + "cheque_amount= '"+chq_amount+"',"
             + "cheque_type= '"+chq_type+"',"
             + "bank_name= '"+bank+"',"
             + "cheque_inhand= '"+chq_inhand+"',"
             + "cheque_duedate= '"+datedue+"',"
             + "status= '"+status+"' where crs_cheque_list.id="+ApplicationIndex.idget+"";
             
         
             con.st.executeUpdate(query);
             
             
             
         } catch (Exception ex) {
         System.out.print(ex);
         }finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
        
    }
    
    public boolean successfullyAdded(){
        String due = jDateChooser1.getDate()+"";
    
        if(jTextField1.getText().trim().equals("") || 
               jTextField5.getText().trim().equals("") || jTextField3.getText().trim().equals("") || 
                jTextField8.getText().trim().equals("") || jTextField7.getText().trim().equals("") ||
                jTextField4.getText().trim().equals("") || due.trim().equals("null")){
        
        
        
        jLabel1.setForeground(Color.RED);
        jLabel6.setText("Please Fill the essential Fields!");
        
    return false;
    }
    else 
           dispose();
    return true;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit Cheque");
        setPreferredSize(new java.awt.Dimension(770, 495));
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        getContentPane().setLayout(null);

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Adding New Cheque", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 15), new java.awt.Color(153, 204, 255))); // NOI18N
        jPanel2.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Heiti TC", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 255));
        jLabel1.setText("Customer Name:");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(40, 50, 160, 17);

        jTextField1.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
        });
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField1);
        jTextField1.setBounds(40, 70, 260, 30);

        jLabel2.setFont(new java.awt.Font("Heiti TC", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 255));
        jLabel2.setText("Location:");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(40, 120, 130, 17);

        jTextField2.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jTextField2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField2FocusGained(evt);
            }
        });
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField2);
        jTextField2.setBounds(40, 140, 260, 30);

        jLabel3.setFont(new java.awt.Font("Heiti TC", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 255));
        jLabel3.setText("Cheque No:");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(40, 200, 130, 17);

        jTextField3.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jTextField3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTextField3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField3FocusGained(evt);
            }
        });
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField3);
        jTextField3.setBounds(40, 220, 260, 30);

        jLabel5.setFont(new java.awt.Font("Heiti TC", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 255));
        jLabel5.setText("Amount:");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(40, 270, 130, 17);

        jTextField5.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jTextField5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTextField5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField5FocusGained(evt);
            }
        });
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField5);
        jTextField5.setBounds(40, 290, 260, 30);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 0, 350, 380);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(null);

        jButton8.setBackground(new java.awt.Color(51, 51, 51));
        jButton8.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jButton8.setForeground(new java.awt.Color(153, 204, 255));
        jButton8.setText("Cancel");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton8);
        jButton8.setBounds(600, 30, 120, 40);

        jButton10.setBackground(new java.awt.Color(51, 51, 51));
        jButton10.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jButton10.setForeground(new java.awt.Color(153, 204, 255));
        jButton10.setText("Save");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton10);
        jButton10.setBounds(470, 30, 120, 40);

        jLabel6.setFont(new java.awt.Font("Heiti TC", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 204, 204));
        jPanel1.add(jLabel6);
        jLabel6.setBounds(40, 30, 360, 40);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 380, 770, 100);

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Other Information", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 15), new java.awt.Color(153, 204, 255))); // NOI18N
        jPanel3.setLayout(null);

        jLabel10.setFont(new java.awt.Font("Heiti TC", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(204, 204, 255));
        jLabel10.setText("Due Date:");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(60, 270, 80, 17);

        jDateChooser1.setBackground(new java.awt.Color(102, 102, 102));
        jDateChooser1.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jPanel3.add(jDateChooser1);
        jDateChooser1.setBounds(60, 290, 260, 30);

        jLabel12.setFont(new java.awt.Font("Heiti TC", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 204, 255));
        jLabel12.setText("Cheque Type:");
        jPanel3.add(jLabel12);
        jLabel12.setBounds(60, 120, 130, 17);

        jLabel13.setFont(new java.awt.Font("Heiti TC", 1, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 204, 255));
        jLabel13.setText("Bank Name:");
        jPanel3.add(jLabel13);
        jLabel13.setBounds(60, 50, 160, 17);

        jTextField7.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jTextField7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTextField7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField7FocusGained(evt);
            }
        });
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });
        jPanel3.add(jTextField7);
        jTextField7.setBounds(60, 140, 260, 30);

        jLabel8.setFont(new java.awt.Font("Heiti TC", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 204, 255));
        jLabel8.setText("Cheque In hand:");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(60, 200, 160, 17);

        jTextField8.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jTextField8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTextField8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField8FocusGained(evt);
            }
        });
        jTextField8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField8MouseClicked(evt);
            }
        });
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });
        jPanel3.add(jTextField8);
        jTextField8.setBounds(60, 70, 260, 30);

        jTextField4.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jTextField4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTextField4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField4FocusGained(evt);
            }
        });
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jPanel3.add(jTextField4);
        jTextField4.setBounds(60, 220, 260, 30);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(350, 0, 420, 380);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
       if(successfullyAdded()){
        insertRecord();
       dispose();
       }
       
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
       jLabel6.setText("");
    }//GEN-LAST:event_jTextField1MouseClicked

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8MouseClicked

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusGained
       jTextField2.selectAll();
    }//GEN-LAST:event_jTextField2FocusGained

    private void jTextField3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField3FocusGained
        jTextField3.selectAll();
    }//GEN-LAST:event_jTextField3FocusGained

    private void jTextField5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField5FocusGained
        jTextField5.selectAll();
    }//GEN-LAST:event_jTextField5FocusGained

    private void jTextField8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField8FocusGained
        jTextField8.selectAll();
    }//GEN-LAST:event_jTextField8FocusGained

    private void jTextField7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField7FocusGained
        jTextField7.selectAll();
    }//GEN-LAST:event_jTextField7FocusGained

    private void jTextField4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField4FocusGained
        jTextField4.selectAll();
    }//GEN-LAST:event_jTextField4FocusGained

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
        jTextField1.selectAll();
    }//GEN-LAST:event_jTextField1FocusGained

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
            java.util.logging.Logger.getLogger(EditCheque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditCheque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditCheque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditCheque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditCheque().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton8;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    // End of variables declaration//GEN-END:variables
}
