/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.assets;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;



/**
 *
 * @author HP_RYZEN
 */
public class TableRender extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if( value instanceof JButton){
            JButton component = (JButton) value;
            component.setOpaque(false);
            return component;
        }
        
        if( value instanceof JComboBox){
            JComboBox component = (JComboBox) value;
            return component;
        }
        
        
        if( value instanceof Component){
            Component btn = (Component) value;
            return btn;
        }
        
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
    }
 
    
}
