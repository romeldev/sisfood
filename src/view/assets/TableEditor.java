/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.assets;

import entity.FactorUnit;
import entity.PreparationDetail;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;



/**
 *
 * @author HP_RYZEN
 */
public class TableEditor implements TableCellEditor {

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (column == 1)
        {
            JComboBox cbx = new JComboBox();
            
            PreparationDetailTM preparationDetailTM = (PreparationDetailTM) table.getModel();
            PreparationDetail preparationDetail = preparationDetailTM.getData().get(row);
            
            ArrayList<FactorUnit> factorUnits =  preparationDetail.getFood().getFactorUnits();
            for (int i = 0; i < factorUnits.size(); i++) {
                cbx.addItem(factorUnits.get(i).getUnitType().getDescrip());
            }
            
            cbx.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        FactorUnit FactorUnit = factorUnits.get( cbx.getSelectedIndex() );
                        preparationDetailTM.getData().get(row).setFactorUnit(FactorUnit);
                                
                        preparationDetailTM.fireTableDataChanged();
                    }
                }
            });
                
            return cbx;
        }
                
        return null;
//        throw new UnsupportedOperationException("1 Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getCellEditorValue() {
        throw new UnsupportedOperationException("2 Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return true;
//        throw new UnsupportedOperationException("3 Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
//        throw new UnsupportedOperationException("4 Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean stopCellEditing() {
        return true;
//        throw new UnsupportedOperationException("5 Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cancelCellEditing() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



    


    
}
