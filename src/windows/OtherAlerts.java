
package windows;

import java.awt.Color;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;
import remainder.FireAlarm;
import remainder.FireAlarm.FireThread;
import static remainder.FireAlarm.c_sec;
import static remainder.FireAlarm.c_title;
import remainder.MyConnection;
import remainder.TableColorScheme;
import remainder.TableColumnBold;
import static windows.ApplicationIndex.idget;

public class OtherAlerts extends javax.swing.JFrame {
      MyConnection con = new MyConnection();
    
    public OtherAlerts() {
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

    
    
    
    
    public void saveNewRecord(){
        jLabel5.setText("");
         String due = jDateChooser1.getDate()+"";
        
        if(jTextArea1.getText().trim().equals("") || due.trim().equals("null")){
            jLabel5.setText("Sorry! Title or Date missing!");
        }
        else{
        String title = jTextArea1.getText();
        int hour = Integer.parseInt(jSpinner2.getValue().toString());  
        int mint = Integer.parseInt(jSpinner1.getValue().toString()); 
        Date dueDate1 = jDateChooser1.getDate();
        SimpleDateFormat format = new SimpleDateFormat("d-MMM-yyyy");
        String date = format.format(dueDate1);
       
        
        int status=0;
        
        
           LocalDate today = LocalDate.now();
           LocalDate date2 = dueDate1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
           Period p = Period.between(today,date2);
           int year = p.getYears();
           int month = p.getMonths();
           int day = p.getDays();
        if(day>=0){
        if(month>=0){
        if(year>=0){
        status = 1;
        }}}
        else{
        status = 0;
        }
        
     String query = "insert into crs_otherAlerts (`title`, `hour`, `mint`, `date`, `status`) values('"+title+"', '"+hour+"', '"+mint+"', '"+date+"',"+status+")";
         
         try {
             con.st.executeUpdate(query);
             
             jLabel5.setText("New Alert Added!");
             
         } catch (Exception ex) {
         System.out.print(ex);
         }
    
    populateTable();
    }
    jTextArea1.setText("");
    jTextArea1.requestFocus(false);
    
    
    }
    
    
    public void populateTable(){
    
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        jTable1.setDefaultRenderer(Object.class, new TableColorScheme());
        this.jTable1.getColumnModel().getColumn(3).setCellRenderer(new TableColumnBold());
        this.jTable1.getColumnModel().getColumn(1).setCellRenderer(new TableColumnBold());
        model.setRowCount(0);
        
        
        try {
            con.rs =  con.st.executeQuery("SELECT * from crs_otherAlerts order by id DESC");
           int count = 1;
            while(con.rs.next()){
            int id = con.rs.getInt("id");
            String title = con.rs.getString("title");
            String date = con.rs.getString("date");
            String hour = con.rs.getInt("hour")+"";
            String mint = con.rs.getInt("mint")+"";
            if(mint.length()<=1){
       mint = "0"+mint;
       }
       if(hour.length()<=1){
       hour = "0"+hour;
       }
            
            String time = hour+":"+mint;
           
            model.addRow(new Object[]{id,count,title,time,date});
            count++;
            }
           int row_count = jTable1.getRowCount();
           
           
            
            if(row_count<=9){
            int remaining = 9 - row_count;
            for(int i=1;i<=remaining;i++){
             model.addRow(new Object[]{""});
                }
            
                }
            
            
          } catch (Exception ex) {
             System.out.println(ex);
        }
    }
    
    public void removeItem(){
    int row = jTable1.getSelectedRow();
        String value_id = (jTable1.getModel().getValueAt(row, 0).toString());
        int x = Integer.parseInt(value_id);
    
     
    try {
             con.st.executeUpdate("DELETE from crs_otherAlerts where id='"+x+"'");
         } catch (SQLException ex) {}
    
    populateTable();
    
   
    
    
    
    }
    
    void alertSet(){
    
        ArrayList<Integer> ids = new ArrayList<Integer>();
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
               FireAlarm f = new FireAlarm();
               
           FireAlarm.FireThread fire = f.new FireThread();
          new Thread(fire).start();
               
               
               
               }
             
             }}}
           } // end statement loop
            
            
           
            for(int i=0;i<ids.size();i++){
            String query ="Update crs_otherAlerts SET status=0 where id="+ids.get(i)+"";
            con.st.executeUpdate(query);
            
            }
            
    }catch(Exception e){}
        
        
    }

    
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jSpinner2 = new javax.swing.JSpinner();
        jPanel7 = new javax.swing.JPanel();
        jSpinner9 = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jSpinner10 = new javax.swing.JSpinner();
        jPanel8 = new javax.swing.JPanel();
        jSpinner11 = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        jSpinner12 = new javax.swing.JSpinner();
        jPanel9 = new javax.swing.JPanel();
        jSpinner13 = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        jSpinner14 = new javax.swing.JSpinner();
        jPanel10 = new javax.swing.JPanel();
        jSpinner15 = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        jSpinner16 = new javax.swing.JSpinner();
        jPanel11 = new javax.swing.JPanel();
        jSpinner17 = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        jSpinner18 = new javax.swing.JSpinner();
        jPanel12 = new javax.swing.JPanel();
        jSpinner19 = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        jSpinner20 = new javax.swing.JSpinner();
        jPanel13 = new javax.swing.JPanel();
        jSpinner21 = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        jSpinner22 = new javax.swing.JSpinner();
        jPanel14 = new javax.swing.JPanel();
        jSpinner23 = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();
        jSpinner24 = new javax.swing.JSpinner();
        jPanel15 = new javax.swing.JPanel();
        jSpinner25 = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        jSpinner26 = new javax.swing.JSpinner();
        jPanel16 = new javax.swing.JPanel();
        jSpinner27 = new javax.swing.JSpinner();
        jLabel16 = new javax.swing.JLabel();
        jSpinner28 = new javax.swing.JSpinner();
        jPanel17 = new javax.swing.JPanel();
        jSpinner29 = new javax.swing.JSpinner();
        jLabel17 = new javax.swing.JLabel();
        jSpinner30 = new javax.swing.JSpinner();
        jPanel18 = new javax.swing.JPanel();
        jSpinner31 = new javax.swing.JSpinner();
        jLabel18 = new javax.swing.JLabel();
        jSpinner32 = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        jLabel4.setText("jLabel4");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Other Reminders");
        setPreferredSize(new java.awt.Dimension(630, 615));
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 15), new java.awt.Color(204, 204, 255))); // NOI18N
        jPanel1.setLayout(null);

        jTable1.setFont(new java.awt.Font("Lucida Grande", 0, 13)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "No", "Description", "Alarm Time", "Alarm Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(330);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(330);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 30, 610, 220);

        jButton12.setBackground(new java.awt.Color(51, 51, 51));
        jButton12.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton12.setForeground(new java.awt.Color(153, 204, 255));
        jButton12.setText("Save");
        jButton12.setBorder(null);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton12);
        jButton12.setBounds(380, 540, 110, 40);

        jButton13.setBackground(new java.awt.Color(51, 51, 51));
        jButton13.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton13.setForeground(new java.awt.Color(153, 204, 255));
        jButton13.setText("Setting");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton13);
        jButton13.setBounds(290, 0, 110, 30);

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add New", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Heiti TC", 0, 16), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setLayout(null);

        jTextArea1.setBackground(new java.awt.Color(153, 153, 153));
        jTextArea1.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextArea1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTextArea1);

        jPanel2.add(jScrollPane2);
        jScrollPane2.setBounds(170, 50, 360, 110);

        jLabel3.setFont(new java.awt.Font("Heiti TC", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 255));
        jLabel3.setText("Reminder Description");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(170, 30, 180, 17);

        jLabel2.setFont(new java.awt.Font("Heiti TC", 0, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 255));
        jLabel2.setText("Date & Time");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(170, 170, 130, 17);

        jDateChooser1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.add(jDateChooser1);
        jDateChooser1.setBounds(170, 200, 200, 40);

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setLayout(null);

        jSpinner1.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(0, 0, 60, 1));
        jPanel3.add(jSpinner1);
        jSpinner1.setBounds(80, 2, 50, 40);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 26)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText(":");
        jPanel3.add(jLabel1);
        jLabel1.setBounds(70, 0, 10, 40);

        jSpinner2.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner2.setModel(new javax.swing.SpinnerNumberModel(0, 0, 23, 1));
        jSpinner2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jSpinner2FocusGained(evt);
            }
        });
        jPanel3.add(jSpinner2);
        jSpinner2.setBounds(10, 2, 50, 40);

        jPanel7.setBackground(new java.awt.Color(102, 102, 102));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel7.setLayout(null);

        jSpinner9.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner9.setModel(new javax.swing.SpinnerNumberModel(0, 0, 60, 1));
        jPanel7.add(jSpinner9);
        jSpinner9.setBounds(80, 2, 50, 40);

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 1, 26)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText(":");
        jPanel7.add(jLabel7);
        jLabel7.setBounds(70, 0, 10, 40);

        jSpinner10.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner10.setModel(new javax.swing.SpinnerNumberModel(9, 1, 12, 1));
        jPanel7.add(jSpinner10);
        jSpinner10.setBounds(10, 2, 50, 40);

        jPanel8.setBackground(new java.awt.Color(102, 102, 102));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel8.setLayout(null);

        jSpinner11.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner11.setModel(new javax.swing.SpinnerNumberModel(0, 0, 60, 1));
        jPanel8.add(jSpinner11);
        jSpinner11.setBounds(80, 2, 50, 40);

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 1, 26)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText(":");
        jPanel8.add(jLabel8);
        jLabel8.setBounds(70, 0, 10, 40);

        jSpinner12.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner12.setModel(new javax.swing.SpinnerNumberModel(9, 1, 12, 1));
        jPanel8.add(jSpinner12);
        jSpinner12.setBounds(10, 2, 50, 40);

        jPanel7.add(jPanel8);
        jPanel8.setBounds(390, 480, 140, 50);

        jPanel9.setBackground(new java.awt.Color(102, 102, 102));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel9.setLayout(null);

        jSpinner13.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner13.setModel(new javax.swing.SpinnerNumberModel(0, 0, 60, 1));
        jPanel9.add(jSpinner13);
        jSpinner13.setBounds(80, 2, 50, 40);

        jLabel9.setFont(new java.awt.Font("Lucida Grande", 1, 26)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText(":");
        jPanel9.add(jLabel9);
        jLabel9.setBounds(70, 0, 10, 40);

        jSpinner14.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner14.setModel(new javax.swing.SpinnerNumberModel(9, 1, 12, 1));
        jPanel9.add(jSpinner14);
        jSpinner14.setBounds(10, 2, 50, 40);

        jPanel10.setBackground(new java.awt.Color(102, 102, 102));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel10.setLayout(null);

        jSpinner15.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner15.setModel(new javax.swing.SpinnerNumberModel(0, 0, 60, 1));
        jPanel10.add(jSpinner15);
        jSpinner15.setBounds(80, 2, 50, 40);

        jLabel10.setFont(new java.awt.Font("Lucida Grande", 1, 26)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText(":");
        jPanel10.add(jLabel10);
        jLabel10.setBounds(70, 0, 10, 40);

        jSpinner16.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner16.setModel(new javax.swing.SpinnerNumberModel(9, 1, 12, 1));
        jPanel10.add(jSpinner16);
        jSpinner16.setBounds(10, 2, 50, 40);

        jPanel9.add(jPanel10);
        jPanel10.setBounds(390, 480, 140, 50);

        jPanel7.add(jPanel9);
        jPanel9.setBounds(390, 480, 140, 50);

        jPanel3.add(jPanel7);
        jPanel7.setBounds(390, 480, 140, 50);

        jPanel11.setBackground(new java.awt.Color(102, 102, 102));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel11.setLayout(null);

        jSpinner17.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner17.setModel(new javax.swing.SpinnerNumberModel(0, 0, 60, 1));
        jPanel11.add(jSpinner17);
        jSpinner17.setBounds(80, 2, 50, 40);

        jLabel11.setFont(new java.awt.Font("Lucida Grande", 1, 26)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText(":");
        jPanel11.add(jLabel11);
        jLabel11.setBounds(70, 0, 10, 40);

        jSpinner18.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner18.setModel(new javax.swing.SpinnerNumberModel(9, 1, 12, 1));
        jPanel11.add(jSpinner18);
        jSpinner18.setBounds(10, 2, 50, 40);

        jPanel12.setBackground(new java.awt.Color(102, 102, 102));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel12.setLayout(null);

        jSpinner19.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner19.setModel(new javax.swing.SpinnerNumberModel(0, 0, 60, 1));
        jPanel12.add(jSpinner19);
        jSpinner19.setBounds(80, 2, 50, 40);

        jLabel12.setFont(new java.awt.Font("Lucida Grande", 1, 26)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText(":");
        jPanel12.add(jLabel12);
        jLabel12.setBounds(70, 0, 10, 40);

        jSpinner20.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner20.setModel(new javax.swing.SpinnerNumberModel(9, 1, 12, 1));
        jPanel12.add(jSpinner20);
        jSpinner20.setBounds(10, 2, 50, 40);

        jPanel11.add(jPanel12);
        jPanel12.setBounds(390, 480, 140, 50);

        jPanel13.setBackground(new java.awt.Color(102, 102, 102));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel13.setLayout(null);

        jSpinner21.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner21.setModel(new javax.swing.SpinnerNumberModel(0, 0, 60, 1));
        jPanel13.add(jSpinner21);
        jSpinner21.setBounds(80, 2, 50, 40);

        jLabel13.setFont(new java.awt.Font("Lucida Grande", 1, 26)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText(":");
        jPanel13.add(jLabel13);
        jLabel13.setBounds(70, 0, 10, 40);

        jSpinner22.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner22.setModel(new javax.swing.SpinnerNumberModel(9, 1, 12, 1));
        jPanel13.add(jSpinner22);
        jSpinner22.setBounds(10, 2, 50, 40);

        jPanel14.setBackground(new java.awt.Color(102, 102, 102));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel14.setLayout(null);

        jSpinner23.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner23.setModel(new javax.swing.SpinnerNumberModel(0, 0, 60, 1));
        jPanel14.add(jSpinner23);
        jSpinner23.setBounds(80, 2, 50, 40);

        jLabel14.setFont(new java.awt.Font("Lucida Grande", 1, 26)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText(":");
        jPanel14.add(jLabel14);
        jLabel14.setBounds(70, 0, 10, 40);

        jSpinner24.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner24.setModel(new javax.swing.SpinnerNumberModel(9, 1, 12, 1));
        jPanel14.add(jSpinner24);
        jSpinner24.setBounds(10, 2, 50, 40);

        jPanel13.add(jPanel14);
        jPanel14.setBounds(390, 480, 140, 50);

        jPanel11.add(jPanel13);
        jPanel13.setBounds(390, 480, 140, 50);

        jPanel15.setBackground(new java.awt.Color(102, 102, 102));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel15.setLayout(null);

        jSpinner25.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner25.setModel(new javax.swing.SpinnerNumberModel(0, 0, 60, 1));
        jPanel15.add(jSpinner25);
        jSpinner25.setBounds(80, 2, 50, 40);

        jLabel15.setFont(new java.awt.Font("Lucida Grande", 1, 26)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText(":");
        jPanel15.add(jLabel15);
        jLabel15.setBounds(70, 0, 10, 40);

        jSpinner26.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner26.setModel(new javax.swing.SpinnerNumberModel(9, 1, 12, 1));
        jPanel15.add(jSpinner26);
        jSpinner26.setBounds(10, 2, 50, 40);

        jPanel16.setBackground(new java.awt.Color(102, 102, 102));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel16.setLayout(null);

        jSpinner27.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner27.setModel(new javax.swing.SpinnerNumberModel(0, 0, 60, 1));
        jPanel16.add(jSpinner27);
        jSpinner27.setBounds(80, 2, 50, 40);

        jLabel16.setFont(new java.awt.Font("Lucida Grande", 1, 26)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText(":");
        jPanel16.add(jLabel16);
        jLabel16.setBounds(70, 0, 10, 40);

        jSpinner28.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner28.setModel(new javax.swing.SpinnerNumberModel(9, 1, 12, 1));
        jPanel16.add(jSpinner28);
        jSpinner28.setBounds(10, 2, 50, 40);

        jPanel15.add(jPanel16);
        jPanel16.setBounds(390, 480, 140, 50);

        jPanel17.setBackground(new java.awt.Color(102, 102, 102));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel17.setLayout(null);

        jSpinner29.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner29.setModel(new javax.swing.SpinnerNumberModel(0, 0, 60, 1));
        jPanel17.add(jSpinner29);
        jSpinner29.setBounds(80, 2, 50, 40);

        jLabel17.setFont(new java.awt.Font("Lucida Grande", 1, 26)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText(":");
        jPanel17.add(jLabel17);
        jLabel17.setBounds(70, 0, 10, 40);

        jSpinner30.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner30.setModel(new javax.swing.SpinnerNumberModel(9, 1, 12, 1));
        jPanel17.add(jSpinner30);
        jSpinner30.setBounds(10, 2, 50, 40);

        jPanel18.setBackground(new java.awt.Color(102, 102, 102));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel18.setLayout(null);

        jSpinner31.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner31.setModel(new javax.swing.SpinnerNumberModel(0, 0, 60, 1));
        jPanel18.add(jSpinner31);
        jSpinner31.setBounds(80, 2, 50, 40);

        jLabel18.setFont(new java.awt.Font("Lucida Grande", 1, 26)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText(":");
        jPanel18.add(jLabel18);
        jLabel18.setBounds(70, 0, 10, 40);

        jSpinner32.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSpinner32.setModel(new javax.swing.SpinnerNumberModel(9, 1, 12, 1));
        jPanel18.add(jSpinner32);
        jSpinner32.setBounds(10, 2, 50, 40);

        jPanel17.add(jPanel18);
        jPanel18.setBounds(390, 480, 140, 50);

        jPanel15.add(jPanel17);
        jPanel17.setBounds(390, 480, 140, 50);

        jPanel11.add(jPanel15);
        jPanel15.setBounds(390, 480, 140, 50);

        jPanel3.add(jPanel11);
        jPanel11.setBounds(380, 480, 140, 50);

        jPanel2.add(jPanel3);
        jPanel3.setBounds(380, 200, 140, 50);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/windows/bel.png"))); // NOI18N
        jPanel2.add(jLabel6);
        jLabel6.setBounds(50, 60, 100, 100);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(10, 260, 610, 270);

        jLabel5.setFont(new java.awt.Font("Heiti TC", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 204, 204));
        jPanel1.add(jLabel5);
        jLabel5.setBounds(70, 550, 250, 20);

        jButton14.setBackground(new java.awt.Color(51, 51, 51));
        jButton14.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton14.setForeground(new java.awt.Color(153, 204, 255));
        jButton14.setText("Cancel");
        jButton14.setBorder(null);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton14);
        jButton14.setBounds(500, 540, 110, 40);

        jButton15.setBackground(new java.awt.Color(51, 51, 51));
        jButton15.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton15.setForeground(new java.awt.Color(153, 204, 255));
        jButton15.setText("New");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton15);
        jButton15.setBounds(510, 0, 110, 30);

        jButton16.setBackground(new java.awt.Color(51, 51, 51));
        jButton16.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton16.setForeground(new java.awt.Color(153, 204, 255));
        jButton16.setText("Remove");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton16);
        jButton16.setBounds(400, 0, 110, 30);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 630, 600);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
       saveNewRecord();
       alertSet();
       
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        
        SnoozeSetting s = new SnoozeSetting();
        s.setVisible(true);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
       jTextArea1.setText("");
       jTextArea1.requestFocus(true);
       jTextArea1.setBackground(Color.white);
       jSpinner2.setValue(9);

    }//GEN-LAST:event_jButton15ActionPerformed

    private void jTextArea1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea1MouseClicked
   jTextArea1.setBackground(Color.white);
   jSpinner2.setValue(9);
    }//GEN-LAST:event_jTextArea1MouseClicked

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        removeItem();   
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jSpinner2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jSpinner2FocusGained
    
    }//GEN-LAST:event_jSpinner2FocusGained

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
            java.util.logging.Logger.getLogger(OtherAlerts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OtherAlerts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OtherAlerts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OtherAlerts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OtherAlerts().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner10;
    private javax.swing.JSpinner jSpinner11;
    private javax.swing.JSpinner jSpinner12;
    private javax.swing.JSpinner jSpinner13;
    private javax.swing.JSpinner jSpinner14;
    private javax.swing.JSpinner jSpinner15;
    private javax.swing.JSpinner jSpinner16;
    private javax.swing.JSpinner jSpinner17;
    private javax.swing.JSpinner jSpinner18;
    private javax.swing.JSpinner jSpinner19;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JSpinner jSpinner20;
    private javax.swing.JSpinner jSpinner21;
    private javax.swing.JSpinner jSpinner22;
    private javax.swing.JSpinner jSpinner23;
    private javax.swing.JSpinner jSpinner24;
    private javax.swing.JSpinner jSpinner25;
    private javax.swing.JSpinner jSpinner26;
    private javax.swing.JSpinner jSpinner27;
    private javax.swing.JSpinner jSpinner28;
    private javax.swing.JSpinner jSpinner29;
    private javax.swing.JSpinner jSpinner30;
    private javax.swing.JSpinner jSpinner31;
    private javax.swing.JSpinner jSpinner32;
    private javax.swing.JSpinner jSpinner9;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
