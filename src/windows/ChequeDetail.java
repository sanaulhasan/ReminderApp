
package windows;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import remainder.MyConnection;
import remainder.TableColorScheme;
import remainder.TableColumnBold;


public class ChequeDetail extends javax.swing.JFrame {

  
    MyConnection con = new MyConnection();
  public int c_f = 0;  
    public ChequeDetail() {
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
        
        
        try {
            
            con.rs =  con.st.executeQuery("SELECT * from crs_cheque_list INNER JOIN crs_status_list "
                    + "ON crs_cheque_list.status = crs_status_list.id where crs_cheque_list.id="+ApplicationIndex.idget+"");
           
            while(con.rs.next()){
            
            String name = con.rs.getString("customer_name");
            String location = con.rs.getString("customer_location");
            String chq_no = con.rs.getString("cheque_no");
            String chq_amount = con.rs.getString("cheque_amount");
            String chq_type = con.rs.getString("cheque_type");
            String bank = con.rs.getString("bank_name");
            String due_date = con.rs.getString("cheque_duedate");
            String added_date = con.rs.getString("date_added");
            String inhand = con.rs.getString("cheque_inhand");
            String status = con.rs.getString("status_name");
            int x = con.rs.getInt("status");
            model.addRow(new Object[]{"Customer Name", name});
            model.addRow(new Object[]{"Customer Location", location});
            model.addRow(new Object[]{"Cheque No ", chq_no});
            model.addRow(new Object[]{"Cheque Amount", chq_amount});
            model.addRow(new Object[]{"Cheque Type", chq_type});
            model.addRow(new Object[]{"Bank Name", bank});
            model.addRow(new Object[]{"Cheque In Hand", inhand});
            model.addRow(new Object[]{"Cheque Due Date", due_date});
            model.addRow(new Object[]{"Added On", added_date});
          if(x==7){
          status = "Post Dated (Date Extended)";
          }
            
            model.addRow(new Object[]{"Cheque Current Status", status});
            
           if(x==4){
               
               
               
           bf.setVisible(false);
           be.setVisible(false);
           bc.setText("Clear (F)");
           c_f  = 1;
               con.rs =  con.st.executeQuery("SELECT * from crs_cheque_forward_list where FK_id="+ApplicationIndex.idget+" ORDER BY id DESC LIMIT 1 ");
           
            while(con.rs.next()){
            String forward_to = con.rs.getString("forward_to");
            String forward_date = con.rs.getString("forward_date");   
            int forward_status1 = con.rs.getInt("status");
            String forward_status = "";
            if(forward_status1==1){forward_status ="Cleared";}
            else if(forward_status1==0){forward_status = "Pending, Not Confirmed";} 
           model.addRow(new Object[]{"Forward To", forward_to});
           model.addRow(new Object[]{"Forward Date", forward_date});
           
           model.addRow(new Object[]{"Forward Status", forward_status});
           
           if(forward_status1==1){
           bc.setVisible(false);
           
           }
           
           }
           }
            
           
           if(x==1){
               bc.setVisible(false);
               
           LocalDate today = LocalDate.now();
           Date  date1 = new SimpleDateFormat("d-MMM-yyyy", Locale.ENGLISH).parse(due_date);
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
           
           model.addRow(new Object[]{"Remaining Days", time});
           }
           
           if(x==3){
                bc.setVisible(false);
                bf.setVisible(false);
                bd.setVisible(false);
                be.setVisible(false);
               
               con.rs =  con.st.executeQuery("SELECT * from crs_cheque_clear_date where FK_id="+ApplicationIndex.idget+" ORDER BY id DESC LIMIT 1 ");
           
            while(con.rs.next()){
            String date = con.rs.getString("date");
           model.addRow(new Object[]{"Clearance Date", date});
           }
          }
           
           if(x==2){
               
               
            LocalDate today = LocalDate.now();
           Date  date1 = new SimpleDateFormat("d-MMM-yyyy", Locale.ENGLISH).parse(due_date);
           LocalDate date2 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
           Period p = Period.between(date2,today);
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
           
           model.addRow(new Object[]{"Unpresented From", time});
           }
           
           if(x==7){
           bc.setVisible(false);
             con.rs =  con.st.executeQuery("SELECT * from crs_cheque_extended_list where FK_id="+ApplicationIndex.idget+" order by id DESC");
           
            while(con.rs.next()){
            String date = con.rs.getString("date");
           model.addRow(new Object[]{"Date Extend From", date});
           }
           }
           
          
            if(x==6){
           
             con.rs =  con.st.executeQuery("SELECT * from crs_cheque_returned_list where FK_id="+ApplicationIndex.idget+" ORDER BY id DESC LIMIT 1 ");
           
            while(con.rs.next()){
            String date = con.rs.getString("date");
           model.addRow(new Object[]{"Returned Date", date});
           }
           }
           
           if(x==5){
           
             bd.setVisible(false);
           }
            
            }
           int row_count = jTable1.getRowCount();
           
           
            
            if(row_count<=16){
            int remaining = 16 - row_count;
            for(int i=1;i<=remaining;i++){
             model.addRow(new Object[]{});
                }
            
                }
            
            
          } catch (Exception ex) {
             System.out.println(ex);
        }
    
    
    
    }
    
    void clearForward(){
    
        try {
            con.st.executeUpdate("Update crs_cheque_forward_list SET status = 1  where FK_id="+ApplicationIndex.idget+"");
            
            
        } catch (SQLException ex) {
           
        }
        
        dispose();
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        bd = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        bf = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        be = new javax.swing.JButton();
        bc = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(460, 585));
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cheque Detail", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 18), new java.awt.Color(153, 204, 255))); // NOI18N
        jPanel1.setLayout(null);

        jTable1.setFont(new java.awt.Font("Lucida Grande", 0, 13)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Attributes", "Detail"
            }
        ));
        jTable1.setRowHeight(22);
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 30, 440, 380);

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Actions", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 18), new java.awt.Color(153, 204, 255))); // NOI18N
        jPanel2.setLayout(null);

        bd.setBackground(new java.awt.Color(51, 51, 51));
        bd.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        bd.setForeground(new java.awt.Color(153, 204, 255));
        bd.setText("Dishonour");
        bd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bdActionPerformed(evt);
            }
        });
        jPanel2.add(bd);
        bd.setBounds(310, 30, 120, 40);

        jButton10.setBackground(new java.awt.Color(51, 51, 51));
        jButton10.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(153, 204, 255));
        jButton10.setText("Edit");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton10);
        jButton10.setBounds(50, 30, 120, 40);

        bf.setBackground(new java.awt.Color(51, 51, 51));
        bf.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        bf.setForeground(new java.awt.Color(153, 204, 255));
        bf.setText("Forward");
        bf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bfActionPerformed(evt);
            }
        });
        jPanel2.add(bf);
        bf.setBounds(180, 30, 120, 40);

        jButton12.setBackground(new java.awt.Color(51, 51, 51));
        jButton12.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton12.setForeground(new java.awt.Color(153, 204, 255));
        jButton12.setText("Cancel");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton12);
        jButton12.setBounds(310, 70, 120, 40);

        be.setBackground(new java.awt.Color(51, 51, 51));
        be.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        be.setForeground(new java.awt.Color(153, 204, 255));
        be.setText("Extend");
        be.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beActionPerformed(evt);
            }
        });
        jPanel2.add(be);
        be.setBounds(180, 70, 120, 40);

        bc.setBackground(new java.awt.Color(51, 51, 51));
        bc.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        bc.setForeground(new java.awt.Color(153, 204, 255));
        bc.setText("Cleared");
        bc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcActionPerformed(evt);
            }
        });
        jPanel2.add(bc);
        bc.setBounds(50, 70, 120, 40);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 420, 460, 130);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 460, 570);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
       ApplicationIndex.idget = 0;
        dispose();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
       EditCheque e = new EditCheque();
       e.setVisible(true);
       dispose();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void bcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcActionPerformed
       if(c_f ==1){
       clearForward();
       }
       else{
        ChequeCleared c = new ChequeCleared();
        c.setVisible(true);
       }
    }//GEN-LAST:event_bcActionPerformed

    private void bdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bdActionPerformed
       ChequeDishonoured d = new ChequeDishonoured();
       d.setVisible(true);
    }//GEN-LAST:event_bdActionPerformed

    private void beActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beActionPerformed
       ChequeExtend e = new ChequeExtend();
       e.setVisible(true);
    }//GEN-LAST:event_beActionPerformed

    private void bfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bfActionPerformed
        ChequeForward f = new ChequeForward();
        f.setVisible(true);
    }//GEN-LAST:event_bfActionPerformed

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
            java.util.logging.Logger.getLogger(ChequeDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChequeDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChequeDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChequeDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChequeDetail().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bc;
    private javax.swing.JButton bd;
    private javax.swing.JButton be;
    private javax.swing.JButton bf;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton12;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables



}

