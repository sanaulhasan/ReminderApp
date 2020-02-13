
package windows;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import remainder.MyConnection;
import remainder.TableColorScheme;
import remainder.TableColumnBold;


public class ChequeCleared extends javax.swing.JFrame {

    
    public ChequeCleared() {
        initComponents();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {}
        this.setLocationRelativeTo(null);
        this.jTable1.getColumnModel().getColumn(0).setCellRenderer(new TableColumnBold());
        populateDetail();
    }

    
    
     public void populateDetail(){
     
     
     DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        jTable1.setDefaultRenderer(Object.class, new TableColorScheme());
        model.setRowCount(0);
        MyConnection con = new MyConnection();
        
        try {
            
            con.rs =  con.st.executeQuery("SELECT * from crs_cheque_list INNER JOIN crs_status_list "
                    + "ON crs_cheque_list.status = crs_status_list.id where crs_cheque_list.id="+ApplicationIndex.idget+"");
           
            while(con.rs.next()){
            
            String name = con.rs.getString("customer_name");
           
            String chq_no = con.rs.getString("cheque_no");
            String chq_amount = con.rs.getString("cheque_amount");
            String chq_type = con.rs.getString("cheque_type");
            String bank = con.rs.getString("bank_name");
            String due_date = con.rs.getString("cheque_duedate");
           String status = con.rs.getString("status_name");
           
            
            model.addRow(new Object[]{"Customer Name", name});
            
            model.addRow(new Object[]{"Cheque No ", chq_no});
            model.addRow(new Object[]{"Cheque Amount", chq_amount});
            model.addRow(new Object[]{"Cheque Type", chq_type});
            model.addRow(new Object[]{"Bank Name", bank});
            
            model.addRow(new Object[]{"Cheque Due Date", due_date});
            
            model.addRow(new Object[]{"Cheque Current Status", status});
            }
        }catch(Exception e){}
     
     
     }
    
    
    public void updateCleared(){
    
     MyConnection con = new MyConnection();
        
     
     String due = jDateChooser1.getDate()+"";
    
        if(!due.trim().equals("null")){
     
     
     String name = "";
     String no = "";
        try {
            String query = "SELECT customer_name, cheque_no from crs_cheque_list where crs_cheque_list.id="+ApplicationIndex.idget+"";
            con.rs =  con.st.executeQuery(query);
           
            while(con.rs.next()){
              name = con.rs.getString("customer_name");
              no = con.rs.getString("cheque_no");
            }
            
        }catch(Exception e){}
        try {
            
          con.st.executeUpdate("UPDATE crs_cheque_list SET status=3 where crs_cheque_list.id="+ApplicationIndex.idget+"");
        String record = "CHQ of Name:  "+name.toUpperCase()+",  CHQ no:  "+no+"  was Cleared";
          Date d = new Date();
          con.st.executeUpdate("INSERT into crs_history (`name`, `date_of_done`,`FK_id`) values('"+record+"','"+d+"','"+ApplicationIndex.idget+"')") ;  
          
          Date dueDate1 = jDateChooser1.getDate();
          SimpleDateFormat format = new SimpleDateFormat("d-MMM-yyyy");
          String date = format.format(dueDate1);
         
          con.st.executeUpdate("INSERT into crs_cheque_clear_date (`date`,`FK_id`) values('"+date+"','"+ApplicationIndex.idget+"')") ;  
          
          
          
        }catch(Exception e){
        System.out.print(e);
        }
    
    
    dispose();
        }else{
        
        jLabel4.setText("Please choose a Date of Clearance!");
        
        }
                
    }
    
    
    
   public void clearedToday(){
   
   
   MyConnection con = new MyConnection();
        
     
     
     
     String name = "";
     String no = "";
        try {
            String query = "SELECT customer_name, cheque_no from crs_cheque_list where crs_cheque_list.id="+ApplicationIndex.idget+"";
            con.rs =  con.st.executeQuery(query);
           
            while(con.rs.next()){
              name = con.rs.getString("customer_name");
              no = con.rs.getString("cheque_no");
            }
            
        }catch(Exception e){}
        
        
        try {
            
          con.st.executeUpdate("UPDATE crs_cheque_list SET status=3 where crs_cheque_list.id="+ApplicationIndex.idget+"");
        String record = "CHQ of Name:  "+name.toUpperCase()+",  CHQ no:  "+no+"  was Cleared";
          Date d = new Date();
          con.st.executeUpdate("INSERT into crs_history (`name`, `date_of_done`,`FK_id`) values('"+record+"','"+d+"','"+ApplicationIndex.idget+"')") ;  
          
          Date dueDate1 = new Date();
          SimpleDateFormat format = new SimpleDateFormat("d-MMM-yyyy");
          String date = format.format(dueDate1);
         
          con.st.executeUpdate("INSERT into crs_cheque_clear_date (`date`,`FK_id`) values('"+date+"','"+ApplicationIndex.idget+"')") ;  
          
          
          
        }catch(Exception e){
        System.out.print(e);
        }
    
    
    dispose();
        
   
   
   }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton12 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cheque Cleared");
        setPreferredSize(new java.awt.Dimension(520, 455));
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CHQ to be Cleared", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Lucida Grande", 0, 15), new java.awt.Color(204, 204, 255))); // NOI18N
        jPanel1.setLayout(null);

        jButton11.setBackground(new java.awt.Color(51, 51, 51));
        jButton11.setForeground(new java.awt.Color(153, 204, 255));
        jButton11.setText("Cancel");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton11);
        jButton11.setBounds(290, 380, 120, 40);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Attribute", "Detail"
            }
        ));
        jTable1.setRowHeight(22);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(200);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(200);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(200);
        }

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 30, 500, 190);

        jButton12.setBackground(new java.awt.Color(51, 51, 51));
        jButton12.setForeground(new java.awt.Color(153, 204, 255));
        jButton12.setText("OK");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton12);
        jButton12.setBounds(410, 380, 90, 40);

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setLayout(null);

        jButton10.setBackground(new java.awt.Color(51, 51, 51));
        jButton10.setForeground(new java.awt.Color(204, 204, 255));
        jButton10.setText("It Cleared Today");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton10);
        jButton10.setBounds(260, 30, 190, 30);

        jDateChooser1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.add(jDateChooser1);
        jDateChooser1.setBounds(260, 70, 230, 28);

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 255));
        jLabel5.setText("OR Select Clearance Date");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(40, 80, 190, 20);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(10, 230, 500, 120);

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 102, 102));
        jPanel1.add(jLabel4);
        jLabel4.setBounds(40, 390, 220, 20);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 520, 440);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
       ApplicationIndex.idget = 0;
        dispose();
       
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        
        updateCleared();
        
        
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
       
        clearedToday();
        
        
    }//GEN-LAST:event_jButton10ActionPerformed

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
            java.util.logging.Logger.getLogger(ChequeCleared.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChequeCleared.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChequeCleared.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChequeCleared.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChequeCleared().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
