
package windows;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;
import remainder.MyConnection;
import remainder.RecordHaveNearDate;
import remainder.TableColorScheme;
import remainder.TableColumnBold;

public class ApplicationIndex extends javax.swing.JFrame {
        static int idget = 0;
   MyConnection con = new MyConnection();
    public ApplicationIndex() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {}
        
        initComponents();
        jButton14.requestFocusInWindow();
        
        populateTable();
        populateUserInfo();
        setActivityLabel();
        jButton23.setMnemonic(KeyEvent.VK_D);
    }
    
    
    
    
public void populateUserInfo(){

try {
            con.rs =  con.st.executeQuery("SELECT user_name, company_name from crs_setting ");
           
            while(con.rs.next()){
           
           
            jLabel5.setText(con.rs.getString("user_name")) ;
            jLabel4.setText(con.rs.getString("company_name")) ;
            
            }
}catch(Exception e){} finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}


}

    public static void populateTable(){
    
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        jTable1.setDefaultRenderer(Object.class, new TableColorScheme());
        jTable1.getColumnModel().getColumn(7).setCellRenderer(new TableColumnBold());
       jTable1.getColumnModel().getColumn(1).setCellRenderer(new RecordHaveNearDate());
        
        model.setRowCount(0);
        
        try {
            MyConnection.rs2 =  MyConnection.st2.executeQuery("SELECT crs_cheque_list.id AS 'id', customer_name, cheque_no, "
                    + "cheque_amount, cheque_type, bank_name, cheque_duedate, status_name, status from crs_cheque_list INNER JOIN crs_status_list "
                    + "ON crs_cheque_list.status = crs_status_list.id order by crs_status_list.ordered ASC");
           
            while(MyConnection.rs2.next()){
            int id = MyConnection.rs2.getInt("id");
            String name = MyConnection.rs2.getString("customer_name");
           
            String chq_no = MyConnection.rs2.getString("cheque_no");
            String chq_amount = MyConnection.rs2.getString("cheque_amount");
            String chq_type = MyConnection.rs2.getString("cheque_type");
            String bank = MyConnection.rs2.getString("bank_name");
            String due_date = MyConnection.rs2.getString("cheque_duedate");
            String status = MyConnection.rs2.getString("status_name");
           
            
            int status1 = MyConnection.rs2.getInt("status");
            if(status1==1 || status1==2 || status1==7){
            
           LocalDate today = LocalDate.now();
           Date  date1 = new SimpleDateFormat("d-MMM-yyyy", Locale.ENGLISH).parse(due_date);
           LocalDate date2 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
           Period p = Period.between(today,date2);
           int year = p.getYears();
           int month = p.getMonths();
           int day = p.getDays();
           if(day<=3 && day>=0 && month==0 && year==0){
               if(day==0){
              status = status+" (Today)"; 
              
              }
               else{
              status = status+" ("+day+" Day)"; 
               }
              
           }}
            
            
            
            
            model.addRow(new Object[]{id,name, chq_no, chq_type, chq_amount, bank, due_date, status});
            
            }
           int row_count = jTable1.getRowCount();
           
           
            
            if(row_count<=21){
            int remaining = 21 - row_count;
            for(int i=1;i<=remaining;i++){
             model.addRow(new Object[]{""});
                }
            
                }
            
            
          } catch (Exception ex) {
            DatabaseNotFoundError error = new DatabaseNotFoundError();
            error.setVisible(true);
        }finally{ try { MyConnection.rs2.close(); MyConnection.st2.close();} catch (SQLException ex) { }}
            
      }
    
    
/*

 public void populateTable(){
    
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        jTable1.setDefaultRenderer(Object.class, new TableColorScheme());
        jTable1.getColumnModel().getColumn(7).setCellRenderer(new TableColumnBold());
       jTable1.getColumnModel().getColumn(1).setCellRenderer(new RecordHaveNearDate());
        
        model.setRowCount(0);
        
        try {
           con.rs =  con.st.executeQuery("SELECT crs_cheque_list.id AS 'id', customer_name, cheque_no, "
                    + "cheque_amount, cheque_type, bank_name, cheque_duedate, status_name, status from crs_cheque_list INNER JOIN crs_status_list "
                    + "ON crs_cheque_list.status = crs_status_list.id order by crs_status_list.ordered ASC");
           
            while(con.rs.next()){
            int id = con.rs.getInt("id");
            String name = con.rs.getString("customer_name");
           
            String chq_no = con.rs.getString("cheque_no");
            String chq_amount = con.rs.getString("cheque_amount");
            String chq_type = con.rs.getString("cheque_type");
            String bank = con.rs.getString("bank_name");
            String due_date = con.rs.getString("cheque_duedate");
            String status = con.rs.getString("status_name");
           
            
            int status1 = con.rs.getInt("status");
            if(status1==1 || status1==2 || status1==7){
            
           LocalDate today = LocalDate.now();
           Date  date1 = new SimpleDateFormat("d-MMM-yyyy", Locale.ENGLISH).parse(due_date);
           LocalDate date2 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
           Period p = Period.between(today,date2);
           int year = p.getYears();
           int month = p.getMonths();
           int day = p.getDays();
           if(day<=3 && day>=0 && month==0 && year==0){
               if(day==0){
              status = status+" (Today)"; 
              
              }
               else{
              status = status+" ("+day+" Day)"; 
               }
              
           }}
            
            
            
            
            model.addRow(new Object[]{id,name, chq_no, chq_type, chq_amount, bank, due_date, status});
            
            }
           int row_count = jTable1.getRowCount();
           
           
            
            if(row_count<=21){
            int remaining = 21 - row_count;
            for(int i=1;i<=remaining;i++){
             model.addRow(new Object[]{""});
                }
            
                }
            
            
          } catch (Exception ex) {
            DatabaseNotFoundError error = new DatabaseNotFoundError();
            error.setVisible(true);
        }finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
            
      }
    
 
 
 */
 
     public void populateTableBySelection(int selection){
   
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
       
        
        try {
            if(selection==1){
            
            con.rs =  con.st.executeQuery("SELECT crs_cheque_list.id AS 'id', customer_name, cheque_no, "
                    + "cheque_amount, customer_location, cheque_type, bank_name, cheque_duedate, status_name, status from crs_cheque_list INNER JOIN crs_status_list "
                    + "ON crs_cheque_list.status = crs_status_list.id "
                    + "where crs_cheque_list.status="+selection+" OR crs_cheque_list.status=7 order by crs_cheque_list.cheque_duedate DESC");
           
          
            }
            else{
            con.rs =  con.st.executeQuery("SELECT crs_cheque_list.id AS 'id', customer_name, cheque_no, "
                    + "cheque_amount, customer_location, cheque_type, bank_name, cheque_duedate, status_name, status from crs_cheque_list INNER JOIN crs_status_list "
                    + "ON crs_cheque_list.status = crs_status_list.id "
                    + "where crs_cheque_list.status="+selection+" order by crs_cheque_list.cheque_duedate DESC");
            }
          
            
            
            while(con.rs.next()){
            int id = con.rs.getInt("id");
            String name = con.rs.getString("customer_name");
            String location = con.rs.getString("customer_location");
            String chq_no = con.rs.getString("cheque_no");
            String chq_amount = con.rs.getString("cheque_amount");
            String chq_type = con.rs.getString("cheque_type");
            String bank = con.rs.getString("bank_name");
            String due_date = con.rs.getString("cheque_duedate");
            String status = con.rs.getString("status_name");
            
            int status1 = con.rs.getInt("status");
            if(status1==1 || status1==2 || status1==7){
            
           LocalDate today = LocalDate.now();
           Date  date1 = new SimpleDateFormat("d-MMM-yyyy", Locale.ENGLISH).parse(due_date);
           LocalDate date2 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
           Period p = Period.between(today,date2);
           int year = p.getYears();
           int month = p.getMonths();
           int day = p.getDays();
           if(day<=3 && day>=0 && month==0 && year==0){
               
               
              status = status+"  ("+day+" Day)"; 
              
              if(day==0){
              status = status+"  (Today)"; 
              
              }
               
           }}
            
            
            
            model.addRow(new Object[]{id, name, chq_no, chq_type, chq_amount, bank, due_date, status});
            
            }
            int row_count = jTable1.getRowCount();
            
            if(row_count<21){
            int remaining = 21 - row_count;
            for(int i=1;i<=remaining;i++){
             model.addRow(new Object[]{""});
                }
                }
            
          } catch (Exception ex) {
             System.out.println(ex);
        }finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
            
      }
     
     public void setActivityLabel(){
     
     try {
            con.rs =  con.st.executeQuery("SELECT name FROM crs_history ORDER BY id DESC LIMIT 1 ");
           
            while(con.rs.next()){
             String activity = con.rs.getString("name");
             jLabel8.setText(activity);
            }
            
     }catch(Exception e){
     
     }finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
     
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
             jLabel1.setForeground(Color.CYAN);
        jLabel1.setText("Please Select Record First!");
        }
          
        
    }
    
    
    public void clearCheque(){
        jLabel1.setForeground(Color.CYAN);
        jLabel1.setText("Comments");
        int row = jTable1.getSelectedRow();
        String value_id = (jTable1.getModel().getValueAt(row, 0).toString());
        int x = Integer.parseInt(value_id);
        idget = x;
        
        MyConnection con = new MyConnection();
        int status = 0;
        try {
            con.rs =  con.st.executeQuery("SELECT status from crs_cheque_list "
                    + " where id="+ApplicationIndex.idget+"");
           
            while(con.rs.next()){
              status = con.rs.getInt("status");
            }
            
        }catch(Exception e){}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
    
        if(status==2 ||status==5){
        ChequeCleared start = new ChequeCleared();
        start.setVisible(true);
            }
        
        else {
        jLabel1.setForeground(Color.orange);
        jLabel1.setText("This Cheque Can not be Update As Cleared");
        }
    
    
    }
    public void forwardCheque(){
     jLabel1.setForeground(Color.CYAN);
        jLabel1.setText("Comments");
        int row = jTable1.getSelectedRow();
        String value_id = (jTable1.getModel().getValueAt(row, 0).toString());
        int x = Integer.parseInt(value_id);
        idget = x;
        
        MyConnection con = new MyConnection();
        int status = 0;
        try {
            con.rs =  con.st.executeQuery("SELECT status from crs_cheque_list "
                    + " where id="+ApplicationIndex.idget+"");
           
            while(con.rs.next()){
              status = con.rs.getInt("status");
            }
            
        }catch(Exception e){}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
    
        if(status==1 ||status==2 || status==5 || status==7){
        ChequeForward start = new ChequeForward();
        start.setVisible(true);
            }
        
        else {
        jLabel1.setForeground(Color.orange);
        jLabel1.setText("This Cheque Can not Forward");
        }
    
    
    
    
    }
    
    public void extendCheque(){
    jLabel1.setForeground(Color.CYAN);
        jLabel1.setText("Comments");
        int row = jTable1.getSelectedRow();
        String value_id = (jTable1.getModel().getValueAt(row, 0).toString());
        int x = Integer.parseInt(value_id);
        idget = x;
        
        MyConnection con = new MyConnection();
        int status = 0;
        try {
            con.rs =  con.st.executeQuery("SELECT status from crs_cheque_list "
                    + " where id="+ApplicationIndex.idget+"");
           
            while(con.rs.next()){
              status = con.rs.getInt("status");
            }
            
        }catch(Exception e){}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
    
        if(status==2 || status==5 || status==1 || status==7){
        ChequeExtend start = new ChequeExtend();
        start.setVisible(true);
            }
        
        else {
        jLabel1.setForeground(Color.orange);
        jLabel1.setText("This Cheque Can not be Update as Extend Date");
        }
    
    
    
    
    
    }
    
    public void returneCheque(){
    jLabel1.setForeground(Color.CYAN);
        jLabel1.setText("Comments");
        int row = jTable1.getSelectedRow();
        String value_id = (jTable1.getModel().getValueAt(row, 0).toString());
        int x = Integer.parseInt(value_id);
        idget = x;
        
        MyConnection con = new MyConnection();
        int status = 0;
        try {
            con.rs =  con.st.executeQuery("SELECT status from crs_cheque_list "
                    + " where id="+ApplicationIndex.idget+"");
           
            while(con.rs.next()){
              status = con.rs.getInt("status");
            }
            
        }catch(Exception e){}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
    
        if(status==2 || status==5 || status==1 || status==7){
        ChequeReturned start = new ChequeReturned();
        start.setVisible(true);
            }
        
        else {
        jLabel1.setForeground(Color.orange);
        jLabel1.setText("This Cheque Can not be Update as Returned");
        }
    
    
    
    }
    public void dishonourCheque(){
    jLabel1.setForeground(Color.CYAN);
        jLabel1.setText("Comments");
        int row = jTable1.getSelectedRow();
        String value_id = (jTable1.getModel().getValueAt(row, 0).toString());
        int x = Integer.parseInt(value_id);
        idget = x;
        
        MyConnection con = new MyConnection();
        int status = 0;
        try {
            con.rs =  con.st.executeQuery("SELECT status from crs_cheque_list "
                    + " where id="+ApplicationIndex.idget+"");
           
            while(con.rs.next()){
              status = con.rs.getInt("status");
            }
            
        }catch(Exception e){}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
    
        if(status==2 || status==4 || status==1 || status==7){
        ChequeDishonoured start = new ChequeDishonoured();
        start.setVisible(true);
            }
        
        else {
        jLabel1.setForeground(Color.orange);
        jLabel1.setText("This Cheque Can not be Update as Dishonoured");
        }
    
    
    
    
    }
    
    
   public void editCheque(){
    jLabel1.setForeground(Color.CYAN);
        jLabel1.setText("Comments");
        int row = jTable1.getSelectedRow();
        String value_id = (jTable1.getModel().getValueAt(row, 0).toString());
        int x = Integer.parseInt(value_id);
        idget = x;
        
        MyConnection con = new MyConnection();
        int status = 0;
        try {
            con.rs =  con.st.executeQuery("SELECT status from crs_cheque_list "
                    + " where id="+ApplicationIndex.idget+"");
           
            while(con.rs.next()){
              status = con.rs.getInt("status");
            }
            
        }catch(Exception e){}finally{ try { con.rs.close(); con.st.close();} catch (SQLException ex) { }}
    
        
        EditCheque start = new EditCheque();
        start.setVisible(true);
           
    
   
   
   
   }
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cheque Records & Remainder Application");
        setPreferredSize(new java.awt.Dimension(1120, 670));
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cheque List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 16), new java.awt.Color(204, 204, 204))); // NOI18N
        jPanel1.setLayout(null);

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
            jTable1.getColumnModel().getColumn(1).setMinWidth(130);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(130);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(130);
            jTable1.getColumnModel().getColumn(7).setMinWidth(110);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(110);
        }

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 60, 930, 440);

        jButton8.setBackground(new java.awt.Color(51, 51, 51));
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/windows/plus_icon.png"))); // NOI18N
        jButton8.setToolTipText("Add New CHQ");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton8);
        jButton8.setBounds(880, 30, 60, 30);

        jButton14.setBackground(new java.awt.Color(51, 51, 51));
        jButton14.setForeground(new java.awt.Color(255, 255, 255));
        jButton14.setText("All CHQs");
        jButton14.setToolTipText("See List of All CHQs");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton14);
        jButton14.setBounds(10, 30, 110, 30);

        jButton15.setBackground(new java.awt.Color(51, 51, 51));
        jButton15.setForeground(new java.awt.Color(255, 255, 255));
        jButton15.setText("Post Dated");
        jButton15.setToolTipText("See Post Dated CHQs");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton15);
        jButton15.setBounds(110, 30, 110, 30);

        jButton16.setBackground(new java.awt.Color(51, 51, 51));
        jButton16.setForeground(new java.awt.Color(255, 255, 255));
        jButton16.setText("Forwarded");
        jButton16.setToolTipText("See Forwarded CHQs");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton16);
        jButton16.setBounds(610, 30, 110, 30);

        jButton17.setBackground(new java.awt.Color(51, 51, 51));
        jButton17.setForeground(new java.awt.Color(255, 255, 255));
        jButton17.setText("Returned");
        jButton17.setToolTipText("See Returned CHQs");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton17);
        jButton17.setBounds(310, 30, 110, 30);

        jButton18.setBackground(new java.awt.Color(51, 51, 51));
        jButton18.setForeground(new java.awt.Color(255, 255, 255));
        jButton18.setText("Extended");
        jButton18.setToolTipText("See Extended Date CHQs");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton18);
        jButton18.setBounds(410, 30, 110, 30);

        jButton20.setBackground(new java.awt.Color(51, 51, 51));
        jButton20.setForeground(new java.awt.Color(255, 255, 255));
        jButton20.setText("Dishonoured");
        jButton20.setToolTipText("See Dishonoured CHQs");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton20);
        jButton20.setBounds(510, 30, 110, 30);

        jButton21.setBackground(new java.awt.Color(51, 51, 51));
        jButton21.setForeground(new java.awt.Color(255, 255, 255));
        jButton21.setText("Unpresented");
        jButton21.setToolTipText("See Pending CHQs");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton21);
        jButton21.setBounds(210, 30, 110, 30);

        jButton22.setBackground(new java.awt.Color(51, 51, 51));
        jButton22.setForeground(new java.awt.Color(255, 255, 255));
        jButton22.setText("Cleared");
        jButton22.setToolTipText("See Cleared CHQs");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton22);
        jButton22.setBounds(710, 30, 110, 30);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(170, 100, 950, 510);

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 16), new java.awt.Color(153, 204, 255))); // NOI18N
        jPanel2.setLayout(null);

        jButton5.setBackground(new java.awt.Color(51, 51, 51));
        jButton5.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton5.setForeground(new java.awt.Color(204, 204, 204));
        jButton5.setText("Quit");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5);
        jButton5.setBounds(680, 30, 100, 50);

        jButton9.setBackground(new java.awt.Color(51, 51, 51));
        jButton9.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton9.setForeground(new java.awt.Color(204, 204, 204));
        jButton9.setText(" Alerts");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton9);
        jButton9.setBounds(10, 30, 110, 50);

        jButton11.setBackground(new java.awt.Color(51, 51, 51));
        jButton11.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton11.setForeground(new java.awt.Color(204, 204, 204));
        jButton11.setText("Preferences");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton11);
        jButton11.setBounds(220, 30, 130, 50);

        jButton12.setBackground(new java.awt.Color(51, 51, 51));
        jButton12.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton12.setForeground(new java.awt.Color(204, 204, 204));
        jButton12.setText("History");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton12);
        jButton12.setBounds(120, 30, 100, 50);

        jButton13.setBackground(new java.awt.Color(51, 51, 51));
        jButton13.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton13.setForeground(new java.awt.Color(204, 204, 204));
        jButton13.setText("Search");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton13);
        jButton13.setBounds(470, 30, 110, 50);

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setLayout(null);

        jLabel4.setFont(new java.awt.Font("Heiti TC", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 255));
        jLabel4.setToolTipText("Company");
        jPanel5.add(jLabel4);
        jLabel4.setBounds(30, 10, 230, 21);

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setLayout(null);

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 17)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 255, 255));
        jLabel3.setText("Superior Traders");
        jPanel6.add(jLabel3);
        jLabel3.setBounds(970, 60, 120, 21);

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 255, 255));
        jLabel6.setText("Superior Traders");
        jPanel6.add(jLabel6);
        jLabel6.setBounds(30, 10, 150, 21);

        jPanel5.add(jPanel6);
        jPanel6.setBounds(890, 30, 210, 40);

        jPanel2.add(jPanel5);
        jPanel5.setBounds(830, 40, 260, 40);

        jLabel5.setFont(new java.awt.Font("Heiti TC", 0, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 255));
        jLabel5.setText("Unknown");
        jLabel5.setToolTipText("User ");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(850, 10, 230, 30);

        jButton24.setBackground(new java.awt.Color(51, 51, 51));
        jButton24.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton24.setForeground(new java.awt.Color(204, 204, 204));
        jButton24.setText("Calender");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton24);
        jButton24.setBounds(350, 30, 120, 50);

        jButton25.setBackground(new java.awt.Color(51, 51, 51));
        jButton25.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton25.setForeground(new java.awt.Color(204, 204, 204));
        jButton25.setText("About");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton25);
        jButton25.setBounds(580, 30, 100, 50);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 0, 1120, 100);

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Lucida Grande", 0, 16), new java.awt.Color(204, 204, 204))); // NOI18N
        jPanel3.setLayout(null);

        jPanel7.setBackground(new java.awt.Color(102, 102, 102));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setLayout(null);

        jButton7.setBackground(new java.awt.Color(51, 51, 51));
        jButton7.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("CHQ Returned");
        jButton7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton7);
        jButton7.setBounds(10, 170, 130, 30);

        jButton6.setBackground(new java.awt.Color(51, 51, 51));
        jButton6.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("CHQ Extend");
        jButton6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton6);
        jButton6.setBounds(10, 120, 130, 30);

        jButton4.setBackground(new java.awt.Color(51, 51, 51));
        jButton4.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("CHQ Forward");
        jButton4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton4);
        jButton4.setBounds(10, 70, 130, 30);

        jButton2.setBackground(new java.awt.Color(51, 51, 51));
        jButton2.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("CHQ Cleared");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton2);
        jButton2.setBounds(10, 20, 130, 30);

        jButton19.setBackground(new java.awt.Color(51, 51, 51));
        jButton19.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton19.setForeground(new java.awt.Color(255, 255, 255));
        jButton19.setText("Dishonoured");
        jButton19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton19);
        jButton19.setBounds(10, 220, 130, 30);

        jPanel3.add(jPanel7);
        jPanel7.setBounds(10, 230, 150, 270);

        jButton1.setBackground(new java.awt.Color(51, 51, 51));
        jButton1.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton1.setForeground(new java.awt.Color(204, 204, 204));
        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);
        jButton1.setBounds(20, 20, 130, 70);

        jButton23.setBackground(new java.awt.Color(51, 51, 51));
        jButton23.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton23.setForeground(new java.awt.Color(204, 204, 204));
        jButton23.setText("See Detail");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton23);
        jButton23.setBounds(20, 120, 130, 40);

        jButton10.setBackground(new java.awt.Color(51, 51, 51));
        jButton10.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jButton10.setForeground(new java.awt.Color(204, 204, 204));
        jButton10.setText("Edit Detail");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton10);
        jButton10.setBounds(20, 170, 130, 40);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 100, 170, 510);

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setLayout(null);

        jLabel1.setBackground(new java.awt.Color(153, 204, 255));
        jLabel1.setFont(new java.awt.Font("Heiti TC", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/windows/go-into.png"))); // NOI18N
        jLabel1.setText("Comments");
        jPanel4.add(jLabel1);
        jLabel1.setBounds(740, 10, 370, 20);

        jLabel8.setBackground(new java.awt.Color(153, 204, 255));
        jLabel8.setFont(new java.awt.Font("Heiti TC", 0, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/windows/activity.png"))); // NOI18N
        jLabel8.setText("Your Last Activity");
        jLabel8.setToolTipText("Your Last Activity");
        jPanel4.add(jLabel8);
        jLabel8.setBounds(10, 10, 670, 22);

        getContentPane().add(jPanel4);
        jPanel4.setBounds(0, 610, 1120, 40);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        Preferences p = new Preferences();
        p.setVisible(true);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        History h = new History();
        h.setVisible(true);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        Search s = new Search();
        s.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
       
       
       AddNew add = new AddNew();
       add.setVisible(true);
       
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        populateTable();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
       populateTableBySelection(1);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
         populateTableBySelection(4);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
         populateTableBySelection(6);
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
       populateTableBySelection(7);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setActivityLabel();       
        populateTable();
        jButton14.requestFocusInWindow();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        setActivityLabel(); 
        populateTableBySelection(5);
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
       setActivityLabel();
        populateTableBySelection(2);
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
       setActivityLabel();
        populateTableBySelection(3);
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
jButton23.setMnemonic(KeyEvent.VK_D);
        setActivityLabel();
        jLabel1.setText("");
        openDetail();
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        Calender c = new Calender();
        c.setVisible(true);
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setActivityLabel();
        clearCheque();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        setActivityLabel();
        forwardCheque();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
      setActivityLabel();
        extendCheque();
        
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        returneCheque();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
         setActivityLabel();
         
    }//GEN-LAST:event_formMouseEntered

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        dishonourCheque();
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
       editCheque();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        About a = new About();
        a.setVisible(true);
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        OtherAlerts alert = new OtherAlerts();
        alert.setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

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
            java.util.logging.Logger.getLogger(ApplicationIndex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ApplicationIndex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ApplicationIndex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ApplicationIndex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new ApplicationIndex().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
