/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.assets;

import common.Helper;
import entity.Food;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;


/**
 *
 * @author HP_RYZEN
 */
public class FoodSingleTM extends AbstractTableModel {

    private String[] columnNames = { "ID", "DESCRIPCION" , "M" };
    private ArrayList<Food> data = new ArrayList<>();

    public void setData(ArrayList<Food> data) {
        this.data = data;
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
        if( columnIndex== 0){
            return data.get(rowIndex).getId();
        }else if (columnIndex == 1){
            return data.get(rowIndex).getDescrip();
        }else if( columnIndex == 2) {
            JButton btn = new JButton(Helper.icon("btn_edit"));
            btn.setBackground( new Color( 0, 0, 0) );
            btn.setName("EDIT");
            return btn;
        }
        return null;
    }

}
