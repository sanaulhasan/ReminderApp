
package remainder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class RecordHaveNearDate extends DefaultTableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        
        
        JLabel c = (JLabel) 
      super.getTableCellRendererComponent(table, value,
                                          isSelected, hasFocus,
                                          row, column);
      
        c.setFont(new Font("Lucida Grande", Font.BOLD, 12));
        
        
        
        
        
        
        if(row%2==0){
            
        c.setBackground(new java.awt.Color(253,253,253));
        }
        else{
        c.setBackground(new java.awt.Color(230,230,230));
        
        }
          if(isSelected) {
         // c.setBackground(new java.awt.Color(50,108,174));
             c.setBackground(new java.awt.Color(100,100,100));
          }     
         
        return c;
    }
}