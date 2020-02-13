
package windows;

import java.sql.SQLException;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import remainder.MyConnection;
import remainder.TableColorScheme;
import remainder.TableColumnBold;
import static windows.ApplicationIndex.idget;

public class History extends javax.swing.JFrame {

    
    public History() {
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
        populateTable();
        
    }

    
    
    public void populateTable(){
    
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        jTable1.setDefaultRenderer(Object.class, new TableColorScheme());
        this.jTable1.getColumnModel().getColumn(2).setCellRenderer(new TableColumnBold());
        model.setRowCount(0);
        MyConnection con = new MyConnection();
        
        try {
            con.rs =  con.st.executeQuery("SELECT * from crs_history order by id DESC");
           
            while(con.rs.next()){
            int id = con.rs.getInt("id");
            String name = con.rs.getString("name");
            String date = con.rs.getString("date_of_done");
            int fid = con.rs.getInt("FK_id");
           
            model.addRow(new Object[]{fid,id,date,name});
            
            }
           int row_count = jTable1.getRowCount();
           
           
            
            if(row_count<=17){
            int remaining = 17 - row_count;
            for(int i=1;i<=remaining;i++){
             model.addRow(new Object[]{""});
                }
            
                }
            
            
          } catch (Exception ex) {
             System.out.println(ex);
        }finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
    }
    
    
    
    
    public void goToIt(){
    
    int row = jTable1.getSelectedRow();
        String value_id = (jTable1.getModel().getValueAt(row, 0).toString());
        if(!value_id.trim().equals("")){
        int x = Integer.parseInt(value_id);
        idget = x;
        ChequeDetail start = new ChequeDetail();
        start.setVisible(true);
        
        }
        else {
        jLabel6.setText("Please Select Record First!");
        }
    
    }
    
    
    
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("My Activity Log");
        setPreferredSize(new java.awt.Dimension(830, 545));
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Activity Log", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 15), new java.awt.Color(204, 204, 255))); // NOI18N
        jPanel1.setLayout(null);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "f", "No", "Time Stamp", "Activity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(21);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(0);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(35);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(35);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(210);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(210);
        }

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 30, 810, 390);

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setLayout(null);

        jButton11.setBackground(new java.awt.Color(51, 51, 51));
        jButton11.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton11.setForeground(new java.awt.Color(153, 204, 255));
        jButton11.setText("Close");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton11);
        jButton11.setBounds(670, 20, 120, 40);

        jButton12.setBackground(new java.awt.Color(51, 51, 51));
        jButton12.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton12.setForeground(new java.awt.Color(153, 204, 255));
        jButton12.setText("See Detail");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton12);
        jButton12.setBounds(540, 20, 120, 40);

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 204, 255));
        jPanel2.add(jLabel6);
        jLabel6.setBounds(40, 20, 370, 40);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(10, 430, 810, 80);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 830, 530);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
       dispose();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
       goToIt();
    }//GEN-LAST:event_jButton12ActionPerformed

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
            java.util.logging.Logger.getLogger(History.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(History.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(History.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(History.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new History().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
