
package windows;

import java.sql.SQLException;
import javax.swing.DefaultListModel;
import remainder.MyConnection;

public class Preferences extends javax.swing.JFrame {
     MyConnection con = new MyConnection();

    public Preferences() {
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
        populateGenrelSetting();
        populateUserInformation();
        populateChequeInformation();
        populatebanksInformation();
        populateChequeInHand();
        
        
    }

    public void populateGenrelSetting(){
    try {
        
            con.rs =  con.st.executeQuery("SELECT remind_b_days, remind_repeat, distrb from crs_setting");
            while(con.rs.next()){
                jComboBox2.setSelectedItem(con.rs.getString("remind_repeat"));
                 jComboBox1.setSelectedItem(con.rs.getString("remind_b_days"));
               if(con.rs.getInt("distrb")==0){
                 jCheckBox1.setSelected(true);
               }
           }
            
           }catch (Exception ex){}
    }
    
    
     public void updateGenrelSetting(){
    
        
        int dd = 1;
        if(jCheckBox1.isSelected()){
            
        dd = 0;
        }
       
        try {
             con.st.executeUpdate("UPDATE crs_setting SET remind_b_days= '"+ jComboBox1.getSelectedItem()+"', remind_repeat= '"+ jComboBox2.getSelectedItem()+"', distrb= "+dd+"");
         } catch (SQLException ex) {}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
        
        
            
           
    jLabel10.setText("Setting Updated!");
    }
    
    
    public void populateUserInformation(){
    
    jTextField6.setEditable(false);
    jTextField5.setEditable(false);
    try {
        
            con.rs =  con.st.executeQuery("SELECT user_name, company_name from crs_setting");
            while(con.rs.next()){
                
                jTextField6.setText(con.rs.getString("user_name"));
                jTextField5.setText(con.rs.getString("company_name"));
           }
            
           }catch (Exception ex){}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
    
    
    
    }
    
    
     public void updateUserInformation(){
    
    
        
        try {
             con.st.executeUpdate("UPDATE crs_setting SET user_name= '"+jTextField6.getText()+"', company_name= '"+jTextField5.getText()+"'");
         } catch (SQLException ex) {}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
        
        
           
    
    jLabel9.setText("User Information Updated!");
    
    }
    
    
    
    
    public void populateChequeInformation(){
        jButton21.setEnabled(false);
    DefaultListModel list = new DefaultListModel();
    try {
        
            con.rs =  con.st.executeQuery("SELECT * from crs_chequetype_list");
            while(con.rs.next()){
                int id = con.rs.getInt("id");
                String name = con.rs.getString("type");
                list.addElement(name);
           
           }
            jList2.setModel(list);
            
           }catch (Exception ex){}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
    
    
    
    
    
    
    }
    
    public void populatebanksInformation(){
       jButton14.setEnabled(false);
       
       
       DefaultListModel list = new DefaultListModel();
    try {
        
            con.rs =  con.st.executeQuery("SELECT * from crs_bank_list");
            while(con.rs.next()){
                int id = con.rs.getInt("id");
                String name = con.rs.getString("bank_name");
                list.addElement(name);
           
           }
            jList1.setModel(list);
            
           }catch (Exception ex){}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
    
  
    
    }
    
     
    
    
    
    
    public void populateChequeInHand(){
     jButton27.setEnabled(false);
       DefaultListModel list = new DefaultListModel();
    try {
        
            con.rs =  con.st.executeQuery("SELECT * from crs_cheque_inhand_to");
            while(con.rs.next()){
                int id = con.rs.getInt("id");
                String name = con.rs.getString("name");
                list.addElement(name);
           
           }
            jList3.setModel(list);
            
           }catch (Exception ex){}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
    
    
    
    
    }
    
    
    public void addbanksInformation(){
    if(!jTextField7.getText().trim().equals("")){
         try {
             con.st.executeUpdate("INSERT into crs_bank_list (`bank_name`) values('"+jTextField7.getText()+"')");
         } catch (SQLException ex) {}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
    
    populatebanksInformation();
    }
    jTextField7.setText("");
    }
    
    public void removebankInfo(){
      String value =  jList1.getSelectedValue().toString();
        
    try {
             con.st.executeUpdate("DELETE from crs_bank_list where bank_name='"+value+"'");
         } catch (SQLException ex) {}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
    
    populatebanksInformation();
    
   }
    
    
    
     public void addChequeInformation(){
    if(!jTextField8.getText().trim().equals("")){
         try {
             con.st.executeUpdate("INSERT into crs_chequetype_list (`type`) values('"+jTextField8.getText()+"')");
         } catch (SQLException ex) {}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
    
    populateChequeInformation();
    }
    jTextField8.setText("");
    }
    
    public void removeChequeInformation(){
      String value =  jList2.getSelectedValue().toString();
        
    try {
             con.st.executeUpdate("DELETE from crs_chequetype_list where type='"+value+"'");
         } catch (SQLException ex) {}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
    
    populateChequeInformation();
    
   }
    
    
    
    
     public void addChequeInHand(){
    if(!jTextField9.getText().trim().equals("")){
         try {
             con.st.executeUpdate("INSERT into crs_cheque_inhand_to (`name`) values('"+jTextField9.getText()+"')");
         } catch (SQLException ex) {}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
    
    populateChequeInHand();
    
    }
    jTextField9.setText("");
    }
    
    public void removeChequeInHand(){
      String value =  jList3.getSelectedValue().toString();
        
    try {
             con.st.executeUpdate("DELETE from crs_cheque_inhand_to where name='"+value+"'");
         } catch (SQLException ex) {}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
    
    populateChequeInHand();
    
   }
    
    
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton14 = new javax.swing.JButton();
        jTextField7 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jTextField8 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList();
        jTextField9 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();

        jButton1.setText("jButton1");

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Preferences");
        setPreferredSize(new java.awt.Dimension(690, 545));
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        getContentPane().setLayout(null);

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Preferences and Setting", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 18), new java.awt.Color(153, 204, 255))); // NOI18N
        jPanel2.setLayout(null);

        jTabbedPane1.setForeground(new java.awt.Color(153, 204, 255));
        jTabbedPane1.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 153, 153), null));
        jPanel1.setLayout(null);

        jPanel9.setBackground(new java.awt.Color(102, 102, 102));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Notification Setting", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 15), new java.awt.Color(153, 204, 255))); // NOI18N
        jPanel9.setLayout(null);

        jComboBox1.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1 Day", "2 Days", "3 Days", "4 Days", "5 Days" }));
        jPanel9.add(jComboBox1);
        jComboBox1.setBounds(290, 140, 170, 27);

        jLabel2.setFont(new java.awt.Font("Heiti TC", 0, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Repeat Remind After");
        jPanel9.add(jLabel2);
        jLabel2.setBounds(90, 80, 200, 20);

        jComboBox2.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20 Minuts", "30 Minuts", "1 hour", "2 hour", "3 hour", "4 hour", "5 hour" }));
        jPanel9.add(jComboBox2);
        jComboBox2.setBounds(290, 70, 170, 27);

        jLabel3.setFont(new java.awt.Font("Heiti TC", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Remind me Before");
        jPanel9.add(jLabel3);
        jLabel3.setBounds(110, 150, 160, 17);

        jCheckBox1.setFont(new java.awt.Font("Heiti TC", 0, 15)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setText("Do not Show Cheque Notification");
        jPanel9.add(jCheckBox1);
        jCheckBox1.setBounds(20, 220, 300, 23);

        jPanel1.add(jPanel9);
        jPanel9.setBounds(20, 20, 610, 260);

        jButton15.setBackground(new java.awt.Color(51, 51, 51));
        jButton15.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton15.setForeground(new java.awt.Color(204, 204, 204));
        jButton15.setText("Apply");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton15);
        jButton15.setBounds(390, 370, 120, 40);

        jButton16.setBackground(new java.awt.Color(51, 51, 51));
        jButton16.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton16.setForeground(new java.awt.Color(204, 204, 204));
        jButton16.setText("Cancel");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton16);
        jButton16.setBounds(510, 370, 120, 40);

        jLabel10.setFont(new java.awt.Font("Heiti TC", 0, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 204, 204));
        jPanel1.add(jLabel10);
        jLabel10.setBounds(70, 300, 260, 30);

        jTabbedPane1.addTab("General Setting", jPanel1);

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 153, 153), null));
        jPanel4.setLayout(null);

        jPanel11.setBackground(new java.awt.Color(102, 102, 102));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Notification Setting", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 15), new java.awt.Color(153, 204, 255))); // NOI18N
        jPanel11.setLayout(null);

        jLabel4.setFont(new java.awt.Font("Heiti TC", 0, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("User Name");
        jPanel11.add(jLabel4);
        jLabel4.setBounds(150, 90, 100, 20);

        jLabel5.setFont(new java.awt.Font("Heiti TC", 0, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Company Name");
        jPanel11.add(jLabel5);
        jLabel5.setBounds(110, 160, 160, 17);

        jTextField5.setFont(new java.awt.Font("Lucida Grande", 0, 17)); // NOI18N
        jTextField5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField5FocusGained(evt);
            }
        });
        jTextField5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField5MouseClicked(evt);
            }
        });
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        jPanel11.add(jTextField5);
        jTextField5.setBounds(270, 150, 260, 30);

        jTextField6.setFont(new java.awt.Font("Lucida Grande", 0, 17)); // NOI18N
        jTextField6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField6FocusGained(evt);
            }
        });
        jTextField6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField6MouseClicked(evt);
            }
        });
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        jPanel11.add(jTextField6);
        jTextField6.setBounds(270, 70, 260, 30);

        jPanel4.add(jPanel11);
        jPanel11.setBounds(20, 20, 610, 260);

        jButton17.setBackground(new java.awt.Color(51, 51, 51));
        jButton17.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton17.setForeground(new java.awt.Color(204, 204, 204));
        jButton17.setText("Cancel");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton17);
        jButton17.setBounds(510, 370, 120, 40);

        jButton18.setBackground(new java.awt.Color(51, 51, 51));
        jButton18.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton18.setForeground(new java.awt.Color(204, 204, 204));
        jButton18.setText("Apply");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton18);
        jButton18.setBounds(390, 370, 120, 40);

        jLabel9.setFont(new java.awt.Font("Heiti TC", 0, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 204, 204));
        jPanel4.add(jLabel9);
        jLabel9.setBounds(70, 300, 260, 30);

        jTabbedPane1.addTab("User Setting", jPanel4);

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 153, 153), null));
        jPanel3.setLayout(null);

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bank Names", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 15), new java.awt.Color(153, 204, 255))); // NOI18N
        jPanel6.setLayout(null);

        jList1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 204, 255), null));
        jList1.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jScrollPane1.setViewportView(jList1);

        jPanel6.add(jScrollPane1);
        jScrollPane1.setBounds(20, 30, 250, 350);

        jPanel3.add(jPanel6);
        jPanel6.setBounds(10, 20, 290, 400);

        jButton14.setBackground(new java.awt.Color(51, 51, 51));
        jButton14.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton14.setForeground(new java.awt.Color(153, 204, 255));
        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/windows/plus_icon.png"))); // NOI18N
        jButton14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton14);
        jButton14.setBounds(550, 140, 60, 30);

        jTextField7.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jTextField7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTextField7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField7MouseClicked(evt);
            }
        });
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });
        jPanel3.add(jTextField7);
        jTextField7.setBounds(320, 140, 230, 30);

        jLabel6.setFont(new java.awt.Font("Heiti TC", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Add new Bank");
        jPanel3.add(jLabel6);
        jLabel6.setBounds(320, 110, 150, 30);

        jButton19.setBackground(new java.awt.Color(51, 51, 51));
        jButton19.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton19.setForeground(new java.awt.Color(204, 204, 204));
        jButton19.setText("Remove");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton19);
        jButton19.setBounds(320, 370, 120, 40);

        jButton20.setBackground(new java.awt.Color(51, 51, 51));
        jButton20.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton20.setForeground(new java.awt.Color(204, 204, 204));
        jButton20.setText("Cancel");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton20);
        jButton20.setBounds(510, 370, 120, 40);

        jTabbedPane1.addTab("Banks in Record", jPanel3);

        jPanel10.setBackground(new java.awt.Color(102, 102, 102));
        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 153, 153), null));
        jPanel10.setLayout(null);

        jPanel7.setBackground(new java.awt.Color(102, 102, 102));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cheque Types", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 15), new java.awt.Color(153, 204, 255))); // NOI18N
        jPanel7.setLayout(null);

        jList2.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 204, 255), null));
        jList2.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jScrollPane2.setViewportView(jList2);

        jPanel7.add(jScrollPane2);
        jScrollPane2.setBounds(20, 30, 250, 350);

        jPanel10.add(jPanel7);
        jPanel7.setBounds(10, 20, 290, 400);

        jTextField8.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jTextField8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
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
        jPanel10.add(jTextField8);
        jTextField8.setBounds(320, 140, 230, 30);

        jLabel7.setFont(new java.awt.Font("Heiti TC", 0, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Add new Type");
        jPanel10.add(jLabel7);
        jLabel7.setBounds(320, 110, 150, 30);

        jButton21.setBackground(new java.awt.Color(51, 51, 51));
        jButton21.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton21.setForeground(new java.awt.Color(153, 204, 255));
        jButton21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/windows/plus_icon.png"))); // NOI18N
        jButton21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton21);
        jButton21.setBounds(550, 140, 60, 30);

        jButton22.setBackground(new java.awt.Color(51, 51, 51));
        jButton22.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton22.setForeground(new java.awt.Color(204, 204, 204));
        jButton22.setText("Remove");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton22);
        jButton22.setBounds(320, 370, 120, 40);

        jButton23.setBackground(new java.awt.Color(51, 51, 51));
        jButton23.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton23.setForeground(new java.awt.Color(204, 204, 204));
        jButton23.setText("Cancel");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton23);
        jButton23.setBounds(510, 370, 120, 40);

        jTabbedPane1.addTab("CHQ Types", jPanel10);

        jPanel14.setBackground(new java.awt.Color(102, 102, 102));
        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 153, 153), null));
        jPanel14.setLayout(null);

        jPanel8.setBackground(new java.awt.Color(102, 102, 102));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Names Who can have CHQ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 15), new java.awt.Color(153, 204, 255))); // NOI18N
        jPanel8.setLayout(null);

        jList3.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 204, 255), null));
        jList3.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jScrollPane3.setViewportView(jList3);

        jPanel8.add(jScrollPane3);
        jScrollPane3.setBounds(20, 30, 250, 350);

        jPanel14.add(jPanel8);
        jPanel8.setBounds(10, 20, 290, 400);

        jTextField9.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jTextField9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTextField9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField9MouseClicked(evt);
            }
        });
        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });
        jPanel14.add(jTextField9);
        jTextField9.setBounds(320, 140, 230, 30);

        jLabel8.setFont(new java.awt.Font("Heiti TC", 0, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Add new Name");
        jPanel14.add(jLabel8);
        jLabel8.setBounds(320, 110, 150, 30);

        jButton27.setBackground(new java.awt.Color(51, 51, 51));
        jButton27.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton27.setForeground(new java.awt.Color(153, 204, 255));
        jButton27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/windows/plus_icon.png"))); // NOI18N
        jButton27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton27);
        jButton27.setBounds(550, 140, 60, 30);

        jButton28.setBackground(new java.awt.Color(51, 51, 51));
        jButton28.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton28.setForeground(new java.awt.Color(204, 204, 204));
        jButton28.setText("Remove");
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton28);
        jButton28.setBounds(320, 370, 120, 40);

        jButton29.setBackground(new java.awt.Color(51, 51, 51));
        jButton29.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton29.setForeground(new java.awt.Color(204, 204, 204));
        jButton29.setText("Cancel");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton29);
        jButton29.setBounds(510, 370, 120, 40);

        jTabbedPane1.addTab("CHQ in Hand to", jPanel14);

        jPanel2.add(jTabbedPane1);
        jTabbedPane1.setBounds(10, 50, 670, 470);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 0, 690, 530);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jTextField8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField8MouseClicked
jButton21.setEnabled(true);       
    }//GEN-LAST:event_jTextField8MouseClicked

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
addChequeInformation();        
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jTextField7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField7MouseClicked
        jButton14.setEnabled(true);
    }//GEN-LAST:event_jTextField7MouseClicked

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        addbanksInformation();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        removebankInfo();
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jTextField9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField9MouseClicked
        jButton27.setEnabled(true); 
    }//GEN-LAST:event_jTextField9MouseClicked

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        removeChequeInformation();
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
       dispose();
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        addChequeInHand();
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
       removeChequeInHand();
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
         dispose();
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jTextField6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField6MouseClicked
        jTextField6.setEditable(true);
        jButton18.setEnabled(true);
    }//GEN-LAST:event_jTextField6MouseClicked

    private void jTextField5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField5MouseClicked
       jTextField5.setEditable(true);
       jButton18.setEnabled(true);
    }//GEN-LAST:event_jTextField5MouseClicked

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        updateUserInformation();
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        updateGenrelSetting();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jTextField6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField6FocusGained
       jTextField6.selectAll();
    }//GEN-LAST:event_jTextField6FocusGained

    private void jTextField5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField5FocusGained
       jTextField5.selectAll();
    }//GEN-LAST:event_jTextField5FocusGained

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
            java.util.logging.Logger.getLogger(Preferences.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Preferences.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Preferences.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Preferences.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Preferences().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JList jList3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
