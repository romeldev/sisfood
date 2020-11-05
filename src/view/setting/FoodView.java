/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.setting;

import bo.FactorUnitBO;
import bo.FoodBO;
import bo.FoodTypeBO;
import bo.NutrientBO;
import bo.UnitTypeBO;
import common.Helper;
import common.Validator;
import entity.FactorUnit;
import entity.Food;
import entity.FoodType;
import entity.Nutrient;
import entity.UnitType;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import view.assets.CompanyTM;
import view.assets.FoodSingleTM;
import view.assets.TableRender;

/**
 *
 * @author HP_RYZEN
 */
public class FoodView extends javax.swing.JFrame {

    public String author = "romel";
    String CERO_DECIMAL = ".0";

    FoodBO foodBO = new FoodBO();
    FoodTypeBO foodTypeBO = new FoodTypeBO();
    UnitTypeBO unitTypeBO = new UnitTypeBO();
    FactorUnitBO factorUnitBO = new FactorUnitBO();
    NutrientBO nutrientBO = new NutrientBO();
    
    CompanyTM tblModel = new CompanyTM();
    
    Food food = new Food();
    ArrayList<UnitType> units = new ArrayList<>();
    ArrayList<Food> foods = new ArrayList<>();
    ArrayList<HashMap<String, String>> nutrients = Nutrient.Items();
    ArrayList<FoodType> foodTypes = new ArrayList<>();
    
    FoodSingleTM tblMdlFoods = new FoodSingleTM();

    
    public FoodView() {
        initComponents();
        this.setTitle("Tabla de composicion");
        txtId.setEnabled(false);
        this.setLocationRelativeTo(null);

        
        clearForm();
        
        fillCbxType(cbxFoodType);
        fillCbxTypeSearch(cbxFoodTypeSearch);
        initTblFoods();
        initTblUnits();
        initTblNutrients();
        
        searchFood();

    }
    //OK
    public void fillCbxType(JComboBox cbx){
        cbx.removeAllItems();
        foodTypes = foodTypeBO.list();
        foodTypes.add(0, new FoodType(0, "Seleccione", null));
        for (FoodType foodType : foodTypes) {
            cbx.addItem(foodType);
        }
    }
    //OK
    public void fillCbxTypeSearch(JComboBox cbx){
        cbx.removeAllItems();
        ArrayList<FoodType> foodTypes = foodTypeBO.list();
        cbx.addItem(new FoodType(0, "Todos", null));
        for (FoodType foodType : foodTypes) {
            cbx.addItem(foodType);
        }
    }
    //OK
    public void initTblFoods(){
        tblFoods.setDefaultRenderer(Object.class, new TableRender());
        tblFoods.setModel(tblMdlFoods);
        tblFoods.setRowHeight(30);
        tblFoods.getColumnModel().getColumn(0).setMaxWidth(50);
        tblFoods.getColumnModel().getColumn(2).setMaxWidth(30);
        tblFoods.getTableHeader().setReorderingAllowed(false);
    }
    //OK
    public void initTblUnits(){
        
        String[] headers = new String[]{"Tipo Unidad", "Unidad", "F.Conversion"};
        
        DefaultTableModel tblMdlUnits = new DefaultTableModel(null, headers) {
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        tblUnits.setModel(tblMdlUnits);
        tblUnits.setDefaultRenderer(Object.class, new TableRender());
        tblUnits.setRowHeight(30);
        
        units = unitTypeBO.list();
        for (UnitType unit : units) {
            tblMdlUnits.addRow(new String[]{
                unit.getDescrip(), "", CERO_DECIMAL
            });
        }
        tblUnits.getTableHeader().setReorderingAllowed(false);
    }
    //OK
    public void initTblNutrients(){
        
        txtNetWeight.setText(CERO_DECIMAL);
        txtGrams.setText(CERO_DECIMAL);
        Helper.TextFieldDecimal(txtNetWeight);
        Helper.TextFieldDecimal(txtGrams);
        
        String[] headers = new String[]{"Nombre", "Valor"};
        
        DefaultTableModel tblMdlUnits = new DefaultTableModel(null, headers) {
            @Override
            public boolean isCellEditable(int row, int column) {
               return column>0;
            }
        };
        tblNutrients.setModel(tblMdlUnits);
        tblNutrients.setDefaultRenderer(Object.class, new TableRender());
        tblNutrients.setRowHeight(30);
        for (HashMap<String, String> nutrient : nutrients) {
            String column = nutrient.keySet().toArray()[0].toString();
            tblMdlUnits.addRow(new String[]{
                nutrient.get(column), CERO_DECIMAL
            });
        }
        tblNutrients.getTableHeader().setReorderingAllowed(false);
    }
    //OK
    public void searchFood() {
        int foodTypeId = ((FoodType) cbxFoodTypeSearch.getSelectedItem()).getId();
        foods = foodBO.search("descrip", txtSearch.getText(), foodTypeId);
        tblMdlFoods.setData(foods);
        tblMdlFoods.fireTableDataChanged();
    }
    
    //OK
    public void editForm(){
        tblUnits.getSelectionModel().clearSelection();
        tblNutrients.getSelectionModel().clearSelection();
        int id = food.getId();
        food.setFactorUnits(factorUnitBO.listByFoodId(id));
        food.setNutrients(nutrientBO.findByFoodId(id));
        
        txtId.setText(food.getId()+"");
        txtDescrip.setText(food.getDescrip());
        

        int index = 0;
        cbxFoodType.setSelectedIndex(index);
        for (FoodType foodType : foodTypes) {
            if( foodType.getId() == food.getFoodType().getId()){
                cbxFoodType.setSelectedIndex(index);
                break;
            }
            index++;
        }
        
        btnSave.setIcon(id==0?Helper.icon("btn_save"):Helper.icon("btn_edit"));
        btnSave.setToolTipText(id==0? "Guardar" :"Actualizar");
        btnDelete.setVisible(true);
        
        // fill table units
        for (FactorUnit factorUnit : food.getFactorUnits()) {
            int tableRow = 0;
            for (UnitType unit : units) {
                if( factorUnit.getUnitType().getId() == unit.getId()) break;
                tableRow++;
            }
            tblUnits.setValueAt(factorUnit.getDescrip(), tableRow, 1);
            tblUnits.setValueAt(factorUnit.getFactor(), tableRow, 2);
        }
        
        txtNetWeight.setText(food.getNutrients().get("net_weight"));
        txtGrams.setText(food.getNutrients().get("grams"));
        String column, column2;
        for (Object objColumn : food.getNutrients().keySet().toArray()) {
            column = objColumn.toString();
            int tableRow = 0;
            boolean found = false;
            
            if( !column.equals("net_weight") && !column.equals("grams") && !column.equals("food_id")){
                for (HashMap nutrient : nutrients) {
                    column2 = nutrient.keySet().toArray()[0].toString();
                    if( column.equals(column2)) break;
                    tableRow++;
                }
                tblNutrients.setValueAt(food.getNutrients().get(column), tableRow, 1);
            }
        }
    }
    
    //OK
    public void clearForm(){
        food = new Food();
        tblUnits.getSelectionModel().clearSelection();
        tblNutrients.getSelectionModel().clearSelection();
        btnDelete.setVisible(false);
        btnSave.setToolTipText("Guardar");
        btnSave.setIcon(Helper.icon("btn_save"));
        txtId.setText(null);
        txtDescrip.setText(null);
        txtNetWeight.setText(CERO_DECIMAL);
        txtGrams.setText(CERO_DECIMAL);
        cbxFoodType.setSelectedIndex(0);
        Helper.TextFieldDecimal(txtNetWeight);
        Helper.TextFieldDecimal(txtGrams);
        
        for (int i = 0; i < tblNutrients.getRowCount(); i++) {
            tblNutrients.setValueAt(CERO_DECIMAL, i, 1);
        }
        
        for (int i = 0; i < tblUnits.getRowCount(); i++) {
            tblUnits.setValueAt(null, i, 1);
            tblUnits.setValueAt(CERO_DECIMAL, i, 2);
        }
    }
    

    public boolean validator()
    {
        int foodTypeID = ((FoodType)cbxFoodType.getSelectedItem()).getId();
        
        if( foodTypeID==0){
            JOptionPane.showMessageDialog(this, "Seleccione un tipo de comida");
            cbxFoodType.requestFocus();
            return false;
        }
        
        if( !Validator.isRequired(txtDescrip.getText())){
            JOptionPane.showMessageDialog(this, "Ingrese una descripcion");
            txtDescrip.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlNutrients = new javax.swing.JPanel();
        txtNetWeight = new javax.swing.JTextField();
        txtGrams = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblNutrients = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtDescrip = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cbxFoodType = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblUnits = new javax.swing.JTable();
        pnlInsumos = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblFoods = new javax.swing.JTable();
        cbxFoodTypeSearch = new javax.swing.JComboBox<>();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        txtId = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlNutrients.setBorder(javax.swing.BorderFactory.createTitledBorder("Nutrientes"));

        txtNetWeight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNetWeightActionPerformed(evt);
            }
        });
        txtNetWeight.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNetWeightKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNetWeightKeyTyped(evt);
            }
        });

        txtGrams.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGramsActionPerformed(evt);
            }
        });

        jLabel5.setText("Gramos*");

        jLabel4.setText("Peso Neto*");

        tblNutrients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblNutrients.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tblNutrientsKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(tblNutrients);

        javax.swing.GroupLayout pnlNutrientsLayout = new javax.swing.GroupLayout(pnlNutrients);
        pnlNutrients.setLayout(pnlNutrientsLayout);
        pnlNutrientsLayout.setHorizontalGroup(
            pnlNutrientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNutrientsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNutrientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNutrientsLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNetWeight, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNutrientsLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtGrams, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlNutrientsLayout.setVerticalGroup(
            pnlNutrientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNutrientsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNutrientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNetWeight, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlNutrientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGrams, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Food"));

        jLabel2.setText("Descripcion*");

        txtDescrip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripActionPerformed(evt);
            }
        });

        jLabel3.setText("Tipo*");

        cbxFoodType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxFoodType, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDescrip)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxFoodType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDescrip, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Unidades"));

        tblUnits.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tblUnits);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlInsumos.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de alimentos"));

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });

        tblFoods.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblFoods.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tblFoodsMouseMoved(evt);
            }
        });
        tblFoods.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFoodsMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tblFoodsMouseExited(evt);
            }
        });
        jScrollPane2.setViewportView(tblFoods);

        cbxFoodTypeSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxFoodTypeSearch.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxFoodTypeSearchItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout pnlInsumosLayout = new javax.swing.GroupLayout(pnlInsumos);
        pnlInsumos.setLayout(pnlInsumosLayout);
        pnlInsumosLayout.setHorizontalGroup(
            pnlInsumosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInsumosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInsumosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxFoodTypeSearch, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlInsumosLayout.setVerticalGroup(
            pnlInsumosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInsumosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbxFoodTypeSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/btn_delete.png"))); // NOI18N
        btnDelete.setToolTipText("Eliminar");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/btn_clear.png"))); // NOI18N
        btnCancel.setToolTipText("Limpiar");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/btn_save.png"))); // NOI18N
        btnSave.setToolTipText("Guardar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(pnlNutrients, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlInsumos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlInsumos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlNutrients, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(11, 11, 11))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        clearForm();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        tblUnits.getSelectionModel().clearSelection();
        tblNutrients.getSelectionModel().clearSelection();
        
        if( !validator()) return;
        
        int id = txtId.getText().equals("")? 0: Integer.parseInt(txtId.getText());
        boolean success = false;
        
        txtId.setText(food.getId()+"");
        food.setId(id);
        food.setFoodType((FoodType)cbxFoodType.getSelectedItem());
        food.setDescrip(txtDescrip.getText());
        
        HashMap<String, String> foodNutrients = new HashMap<>();
        foodNutrients.put("food_id", food.getId()+"");
        foodNutrients.put("net_weight", txtNetWeight.getText());
        foodNutrients.put("grams", txtGrams.getText());
        int rowNut = 0;
        for (HashMap<String, String> nutrient : nutrients) {
            String column = nutrient.keySet().toArray()[0].toString();
            String value = tblNutrients.getValueAt(rowNut, 1).toString();
            foodNutrients.put(column, value);
            rowNut++;
        }
        food.setNutrients(foodNutrients);
        
        
        ArrayList<FactorUnit> foodUnits = new ArrayList<>();
        int rowUni = 0;
        for (UnitType unit : units) {
            String descrip = tblUnits.getValueAt(rowUni, 1).toString();
            double factor = 0;
            try {
                factor = Double.parseDouble(tblUnits.getValueAt(rowUni, 2).toString());
            } catch (Exception e) {}
            
            FactorUnit foodUnit = new FactorUnit();
            foodUnit.setFood(food);
            foodUnit.setUnitType(unit);
            foodUnit.setDescrip(descrip);
            foodUnit.setFactor(factor);
            foodUnits.add(foodUnit);
            rowUni++;
        }
        food.setFactorUnits(foodUnits);
        if( foodBO.save(food) ){
            searchFood();
            JOptionPane.showMessageDialog(this, "Datos guardados", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this, "Ocurrio un error al almacenar los datos", "Mensaje", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnSaveActionPerformed

    public void getFillFood()
    {
        HashMap<String, String> item = new HashMap<>();
        FoodType foodType = (FoodType) cbxFoodTypeSearch.getSelectedItem();
        
        item.put("id", txtId.getText());
        item.put("food_type_id", foodType.getId()+"");
        item.put("descrip", txtDescrip.getText());
        item.put("net_weight", txtNetWeight.getText());
        item.put("grams", txtGrams.getText());
        for (Component component : pnlNutrients.getComponents()) {
            JPanel pnlItem = (JPanel) component;
            JTextField input = (JTextField) pnlItem.getComponents()[1];
            item.put(input.getName(), input.getText());
            System.out.println(item);
        }
    }
    private void txtNetWeightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNetWeightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNetWeightActionPerformed

    private void txtGramsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGramsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGramsActionPerformed

    private void txtNetWeightKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNetWeightKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNetWeightKeyPressed

    private void txtNetWeightKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNetWeightKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNetWeightKeyTyped

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if( foodBO.delete(food)){
            JOptionPane.showMessageDialog(this, "Alimento eliminado!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            searchFood();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtDescripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            searchFood();
        }
    }//GEN-LAST:event_txtSearchKeyPressed

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtSearchKeyTyped

    private void cbxFoodTypeSearchItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxFoodTypeSearchItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            searchFood();
        }
    }//GEN-LAST:event_cbxFoodTypeSearchItemStateChanged

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchKeyReleased

    private void tblFoodsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFoodsMouseExited
        // TODO add your handling code here:
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tblFoodsMouseExited

    private void tblFoodsMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFoodsMouseMoved
        if( tblFoods.columnAtPoint(evt.getPoint()) > 1){
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }else{
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_tblFoodsMouseMoved

    private void tblFoodsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFoodsMouseClicked
        // TODO add your handling code here:
        int col = tblFoods.columnAtPoint(evt.getPoint());
        int row = tblFoods.rowAtPoint(evt.getPoint());

        if( row < tblFoods.getRowCount() && row >-1 && col < tblFoods.getColumnCount() && col > -1 ){
            Object value = tblFoods.getValueAt(row, col);
            if ( value instanceof JButton){
                JButton btn = (JButton) value;
                food = foods.get(row);
                
                if(btn.getName().equals("EDIT")){
                    editForm();
                }
            }
        }
    }//GEN-LAST:event_tblFoodsMouseClicked

    private void tblNutrientsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblNutrientsKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_tblNutrientsKeyTyped

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
                if ("Window".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FoodView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FoodView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FoodView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FoodView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new FoodView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbxFoodType;
    private javax.swing.JComboBox<String> cbxFoodTypeSearch;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel pnlInsumos;
    private javax.swing.JPanel pnlNutrients;
    private javax.swing.JTable tblFoods;
    private javax.swing.JTable tblNutrients;
    private javax.swing.JTable tblUnits;
    public javax.swing.JTextField txtDescrip;
    private javax.swing.JTextField txtGrams;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNetWeight;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
