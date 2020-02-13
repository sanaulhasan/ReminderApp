/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remainder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author sanaulhassan
 */
public class TableColumnBold extends DefaultTableCellRenderer {
    
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
            
        c.setBackground(new java.awt.Color(240,240,240));
        }
        else{
        c.setBackground(new java.awt.Color(217,217,217));
        }
          if(isSelected) {
         // c.setBackground(new java.awt.Color(50,108,174));
             c.setBackground(new java.awt.Color(100,100,100));
          }     
         
        return c;
    }
}