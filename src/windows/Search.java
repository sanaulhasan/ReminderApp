
package windows;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import remainder.MyConnection;
import remainder.TableColorScheme;
import remainder.TableColumnBold;
import remainder.TextAutoSuggest;
import static windows.ApplicationIndex.idget;
import static windows.EditCheque.c_name;

public class Search extends javax.swing.JFrame {
        
   MyConnection con = new MyConnection();
    public Search() {
        
        
        initComponents();
        this.setLocationRelativeTo(null);
        jTextField1.setEditable(false);
        jTextField2.setEditable(false);
        jTextField3.setEditable(false);
        jTextField4.setEditable(false);
        populateComboBox();
        TextAuto();
    }
    
    
    public void TextAuto(){
    
          ArrayList<String> items = new ArrayList<String>();
          ArrayList<String> items2 = new ArrayList<String>();
        
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
            
           }catch (Exception ex){}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
        
        TextAutoSuggest t = new TextAutoSuggest(jTextField1,items);
        TextAutoSuggest t2 = new TextAutoSuggest(jTextField4,items2);
         
         
    }
    
    public void populateComboBox(){
        jButton6.setVisible(false);
        try {
            con.rs =  con.st.executeQuery("SELECT bank_name from crs_bank_list");
           
            while(con.rs.next()){
            
            String name = con.rs.getString("bank_name");
           jComboBox3.addItem(name);
           
           }
            
            con.rs =  con.st.executeQuery("SELECT type from crs_chequetype_list");
           
            while(con.rs.next()){
            
            String name = con.rs.getString("type");
           jComboBox2.addItem(name);
           
           }
            
            
            
            
          } catch (Exception ex) {
             
        }finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
        
        
    }
    
    public void search(){
       
        jButton6.setVisible(false);
          DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            jTable1.setDefaultRenderer(Object.class, new TableColorScheme());
            this.jTable1.getColumnModel().getColumn(1).setCellRenderer(new TableColumnBold());
            model.setRowCount(0);
            try {
            con.rs =  con.st.executeQuery("SELECT  crs_cheque_list.id AS 'id', customer_name, cheque_no, "
                    + "cheque_amount, customer_location, cheque_type, bank_name, cheque_duedate, status_name, status from crs_cheque_list INNER JOIN crs_status_list "
                    + "ON crs_cheque_list.status = crs_status_list.id where "
                    + "customer_name='"+jTextField1.getText()+"'"
                    + "OR cheque_no='"+jTextField2.getText()+"'"
                    + "OR cheque_amount='"+jTextField3.getText()+"'"
                    + "OR bank_name='"+jComboBox3.getSelectedItem()+"'"
                    + "OR customer_location='"+jTextField4.getText()+"'"
                    + "OR cheque_type='"+jComboBox2.getSelectedItem()+"'");
           
            while(con.rs.next()){
            int id = con.rs.getInt("id");
            String name = con.rs.getString("customer_name");
            String chq_no = con.rs.getString("cheque_no");
            String chq_amount = con.rs.getString("cheque_amount");
            String chq_type = con.rs.getString("cheque_type");
            String bank = con.rs.getString("bank_name");
            String due_date = con.rs.getString("cheque_duedate");
            String status = con.rs.getString("status_name");
           
            model.addRow(new Object[]{id,name, chq_no, chq_type, chq_amount, bank, due_date, status});
            
            
            
            }
            jLabel1.setText(" Search Complete. Record Found: "+jTable1.getRowCount()+"");
            if(jTable1.getRowCount()==0){
            jLabel1.setText(" Sorry! No Record Found!");
            }
            int row_count = jTable1.getRowCount();
            if(row_count<=12){
            int remaining = 12 - row_count;
            for(int i=1;i<=remaining;i++){
             model.addRow(new Object[]{""});
                }
            
                }
             } catch (Exception ex) {
             System.out.println(ex);
             }
        
       }

    
    
      public void openDetail(){
        
        int row = jTable1.getSelectedRow();
        String value_id = (jTable1.getModel().getValueAt(row, 0).toString());
        if(!value_id.trim().equals("")){
        int x = Integer.parseInt(value_id);
        idget = x;
        ChequeDetail start = new ChequeDetail();
        start.setVisible(true);
        
        }
        else {
             
        jLabel1.setText("Please Select Record First!");
        }
          
        
    }
    
      
      public void showdialog(){
      jButton6.setVisible(true);
      jLabel1.setText("Are you Sure to Delete Record?");
      }
      
      public void deleteRecord(){
      
          
          int row = jTable1.getSelectedRow();
        String value_id = (jTable1.getModel().getValueAt(row, 0).toString());
        if(!value_id.trim().equals("")){
        int x = Integer.parseInt(value_id);
        
        String name1 = "";
        String no1 = "";
                
        try {
            
            
            con.rs =  con.st.executeQuery("SELECT customer_name, cheque_no from crs_cheque_list where id="+x+"");
           
            while(con.rs.next()){
            
            
            name1 = con.rs.getString("customer_name");
            no1 = con.rs.getString("cheque_no");
            }
            
            
            
            String record = "CHQ of Name:  "+name1.toUpperCase()+",  CHQ no:  "+no1+"  was Removed from Record Permanently";      
        Date d = new Date();
       con.st.executeUpdate("INSERT into crs_history (`name`, `date_of_done`,`FK_id`) values('"+record+"','"+d+"','0')") ;  
          
            
            
            
             con.st.executeUpdate("DELETE from crs_cheque_list where id='"+x+"'");
             
             
             
           //  JOptionPane.showMessageDialog(null, "Record has been removed!");
             
             
             
             
         } catch (Exception ex) {System.out.print(ex);}
    jLabel1.setText("Deleted Successfully!");
    jButton6.setVisible(false);
    search();
        
        
        
        }
        else {
             
        jLabel1.setText("Please Select Record First!");
        }
          
          
          
      
      }        

      
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox();
        jComboBox3 = new javax.swing.JComboBox();
        jTextField4 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Search");
        setPreferredSize(new java.awt.Dimension(950, 535));
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Results", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 16), new java.awt.Color(204, 204, 204))); // NOI18N
        jPanel1.setLayout(null);

        jTable1.setFont(new java.awt.Font("Lucida Grande", 0, 13)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Customer Name", "Cheque No.", "Cheque Type", "Amount", "Bank", "Due Date", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(19);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(0);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(130);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(130);
        }

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 30, 930, 260);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 150, 950, 310);

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Set Search Criteria", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 15), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setLayout(null);

        jButton5.setBackground(new java.awt.Color(51, 51, 51));
        jButton5.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton5.setForeground(new java.awt.Color(204, 204, 204));
        jButton5.setText("Start Search");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5);
        jButton5.setBounds(770, 40, 130, 80);

        jTextField1.setFont(new java.awt.Font("Lucida Grande", 2, 13)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(153, 153, 153));
        jTextField1.setText(" Enter Customer Name");
        jTextField1.setToolTipText("Enter Customer Name here");
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
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
        jTextField1.setBounds(50, 40, 230, 30);

        jTextField2.setFont(new java.awt.Font("Lucida Grande", 2, 13)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(153, 153, 153));
        jTextField2.setText(" Cheque no.");
        jTextField2.setToolTipText("Enter Cheque no here");
        jTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField2FocusLost(evt);
            }
        });
        jTextField2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField2MouseClicked(evt);
            }
        });
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField2);
        jTextField2.setBounds(50, 90, 230, 30);

        jTextField3.setFont(new java.awt.Font("Lucida Grande", 2, 13)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(153, 153, 153));
        jTextField3.setText(" Cheque Amount");
        jTextField3.setToolTipText("Enter Amount here");
        jTextField3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField3FocusLost(evt);
            }
        });
        jTextField3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField3MouseClicked(evt);
            }
        });
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField3);
        jTextField3.setBounds(300, 40, 230, 30);

        jComboBox2.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Cheque Type" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox2);
        jComboBox2.setBounds(540, 90, 190, 30);

        jComboBox3.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select a Bank" }));
        jPanel2.add(jComboBox3);
        jComboBox3.setBounds(540, 40, 190, 30);

        jTextField4.setFont(new java.awt.Font("Lucida Grande", 2, 13)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(153, 153, 153));
        jTextField4.setText(" Location");
        jTextField4.setToolTipText("Enter Amount here");
        jTextField4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField4FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField4FocusLost(evt);
            }
        });
        jTextField4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField4MouseClicked(evt);
            }
        });
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField4);
        jTextField4.setBounds(300, 89, 230, 28);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 0, 950, 150);

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setLayout(null);

        jLabel1.setBackground(new java.awt.Color(153, 204, 255));
        jLabel1.setFont(new java.awt.Font("Heiti TC", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/windows/go-into.png"))); // NOI18N
        jLabel1.setText("Comments");
        jPanel4.add(jLabel1);
        jLabel1.setBounds(30, 20, 310, 20);

        jButton6.setBackground(new java.awt.Color(51, 51, 51));
        jButton6.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton6.setForeground(new java.awt.Color(204, 204, 204));
        jButton6.setText("Yes");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton6);
        jButton6.setBounds(390, 10, 70, 40);

        jButton26.setBackground(new java.awt.Color(51, 51, 51));
        jButton26.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton26.setForeground(new java.awt.Color(204, 204, 204));
        jButton26.setText("Delete Record");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton26);
        jButton26.setBounds(530, 10, 140, 40);

        jButton27.setBackground(new java.awt.Color(51, 51, 51));
        jButton27.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton27.setForeground(new java.awt.Color(204, 204, 204));
        jButton27.setText("See Detail");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton27);
        jButton27.setBounds(680, 10, 120, 40);

        jButton7.setBackground(new java.awt.Color(51, 51, 51));
        jButton7.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton7.setForeground(new java.awt.Color(204, 204, 204));
        jButton7.setText("Close");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton7);
        jButton7.setBounds(810, 10, 100, 40);

        getContentPane().add(jPanel4);
        jPanel4.setBounds(0, 460, 950, 60);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
      DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        search();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        
    }//GEN-LAST:event_formMouseEntered

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
      deleteRecord();       
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
   showdialog();      
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked

        jTextField1.setEditable(true);
        jTextField1.setText("");     
        jTextField1.setForeground(Color.black);
        jTextField1.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
    }//GEN-LAST:event_jTextField1MouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
        
    }//GEN-LAST:event_jTextField1FocusGained

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        
    }//GEN-LAST:event_jTextField1FocusLost

    private void jTextField2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2FocusGained

    private void jTextField2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2FocusLost

    private void jTextField2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField2MouseClicked
        jTextField2.setEditable(true);
        jTextField2.setText("");     
        jTextField2.setForeground(Color.black);
        jTextField2.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
    }//GEN-LAST:event_jTextField2MouseClicked

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField3FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3FocusGained

    private void jTextField3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField3FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3FocusLost

    private void jTextField3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField3MouseClicked
        jTextField3.setEditable(true);
        jTextField3.setText("");     
        jTextField3.setForeground(Color.black);
        jTextField3.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
    }//GEN-LAST:event_jTextField3MouseClicked

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jTextField4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField4FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4FocusGained

    private void jTextField4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField4FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4FocusLost

    private void jTextField4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField4MouseClicked
        jTextField4.setEditable(true);
        jTextField4.setText("");     
        jTextField4.setForeground(Color.black);
        jTextField4.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
    }//GEN-LAST:event_jTextField4MouseClicked

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
       openDetail();
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        
        dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
         ApplicationIndex x = new ApplicationIndex();
         x.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

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
            java.util.logging.Logger.getLogger(Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Search().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
