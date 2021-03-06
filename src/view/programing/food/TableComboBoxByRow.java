/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.programing.food;

/**
 *
 * @author user
 */
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
 
public class TableComboBoxByRow extends JPanel
{
    List<String[]> editorData = new ArrayList<String[]>(3);
 
    public TableComboBoxByRow()
    {
        setLayout( new BorderLayout() );
 
        // Create the editorData to be used for each row
 
        editorData.add( new String[]{ "Red", "Blue", "Green" } );
        editorData.add( new String[]{ "Circle", "Square", "Triangle" } );
        editorData.add( new String[]{ "Apple", "Orange", "Banana" } );
 
        //  Create the table with default data
 
        Object[][] data =
        {
            {"Color", "Red"},
            {"Shape", "Square"},
            {"Fruit", "Banana"},
            {"Plain", "Text"}
        };
        String[] columnNames = {"Type","Value"};
 
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model)
        {
            //  Determine editor to be used by row
                public TableCellEditor getCellEditor(int row, int column)
            {
                int modelColumn = convertColumnIndexToModel( column );
 
                if (modelColumn == 1 && row < 3)
                {
                    JComboBox<String> comboBox1 = new JComboBox<String>( editorData.get(row));
                    return new DefaultCellEditor( comboBox1 );
                }
                else
                    return super.getCellEditor(row, column);
            }
        };
 
        JScrollPane scrollPane = new JScrollPane( table );
        add( scrollPane );
    }
 
    private static void createAndShowUI()
    {
        JFrame frame = new JFrame("Table Combo Box by Row");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add( new TableComboBoxByRow() );
        frame.setSize(200, 200);
        frame.setLocationByPlatform( true );
        frame.setVisible( true );
    }
 
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowUI();
            }
        });
    }
}
