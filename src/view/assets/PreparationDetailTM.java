/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.assets;

import common.Helper;
import entity.PreparationDetail;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;


/**
 *
 * @author HP_RYZEN
 */
public class PreparationDetailTM extends AbstractTableModel {

    private String[] columnNames = { "Alimento", "Tipo Medida", "Medida", "Cantidad", "M", "E" };
    private ArrayList<PreparationDetail> data = new ArrayList<>();
    
    private Class[] columnClass = { String.class, Object.class, String.class, Double.class, JButton.class };

    public void setData(ArrayList<PreparationDetail> data) {
        this.data = data;
    }
    
    public ArrayList<PreparationDetail> getData() {
        return this.data;
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0){
            return data.get(rowIndex).getFood().getDescrip();
        }else if (columnIndex == 1){
            return data.get(rowIndex).getFactorUnit().getUnitType().getDescrip();
        }else if (columnIndex == 2){
            return data.get(rowIndex).getFactorUnit().getDescrip();
        }else if (columnIndex == 3){
            return data.get(rowIndex).getAmount();
        }else if( columnIndex == 4) {
            JButton btn = new JButton(Helper.icon("btn_edit"));
            btn.setBackground( new Color( 0, 0, 0) );
            btn.setName("EDIT");
            return btn;
        }else if( columnIndex == 5) {
            JButton btn = new JButton(Helper.icon("btn_delete"));
            btn.setBackground( new Color( 0, 0, 0) );
            btn.setName("DELETE");
            return btn;
        }
        return null;
    }

}
