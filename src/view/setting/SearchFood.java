/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.setting;

import bo.FactorUnitBO;
import bo.FoodTypeBO;
import bo.FoodBO;
import entity.FactorUnit;
import entity.Food;
import entity.FoodType;
import entity.Nutrient;
import entity.Preparation;
import entity.PreparationDetail;
import entity.UnitType;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP_RYZEN
 */
public class SearchFood extends javax.swing.JDialog {

    /**
     * Creates new form SearchFood
     */
    
    ArrayList<HashMap<String, String>> nutrients = Nutrient.Items();
    
    FoodTypeBO  foodTypeBO = new FoodTypeBO();
    FactorUnitBO factorUnitBO = new FactorUnitBO();
    
    FoodBO foodBO = new FoodBO();
    
    ArrayList<Food> foods = new ArrayList<>();
    ArrayList<Food> foodResults = new ArrayList<>();
    
    ArrayList<FoodType> foodTypes = new ArrayList<>();

    Food food = new Food();
    
    PreparationView preparationView;
    
    PreparationDetail preparationDetail = new PreparationDetail();
    
    public SearchFood(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setTitle("Buscar Valor Nutricional de Alimentos");
        this.setLocationRelativeTo(null);
        txtId.setEditable(false);
        txtDescrip.setEditable(false);
        txtFactorUnit.setEditable(false);
        initFoodType(cbxFoodType);
        fillCbxSearchBy(cbxSearchBy);
        initTable();
        getFoods();
        searchLocal();
    }

    public void setPreparationView(PreparationView preparationView) {
        this.preparationView = preparationView;
    }
    
    public void open( PreparationDetail preparationDetail )
    {
        tblResults.getSelectionModel().clearSelection();

        if( preparationDetail==null){
            this.preparationDetail = new PreparationDetail();
            this.preparationDetail.setAmount(0.0);
            Food food = new Food();
            food.setFactorUnits(new ArrayList<>() );
            this.preparationDetail.setFood(food);
        }else{
            preparationDetail.getFood().setFactorUnits( factorUnitBO.listByFoodId(preparationDetail.getFood().getId()) );
            
            this.preparationDetail = preparationDetail;
        }
        txtId.setText( this.preparationDetail.getFood().getId()+"" );
        txtDescrip.setText( this.preparationDetail.getFood().getDescrip() );
        txtAmount.setText( this.preparationDetail.getAmount()+"" );
        txtFactorUnit.setText(null);

        fillCbxUnitType(cbxUnitType);
        this.setVisible(true);
    }
    
    public void getFoods()
    {
        foods = foodBO.listWithNutrients();
    }
    
    public void searchLocal()
    {
        foodResults.clear();

        String search = txtSearch.getText().trim();
        String columnSearch = cbxSearchBy.getSelectedItem().toString();
        int foodTypeId = ((FoodType) cbxFoodType.getSelectedItem()).getId();
        
        if( columnSearch.equals("Descripcion")){
            for (Food food : foods) {
                if( foodTypeId==0 ){
                    if( food.getDescrip().toUpperCase().matches(".*"+search.toUpperCase()+".*")){
                        foodResults.add(food);
                    }
                }else{
                    if( food.getFoodType().getId()==foodTypeId && food.getDescrip().toUpperCase().matches(".*"+search.toUpperCase()+".*")){
                        foodResults.add(food);
                    }
                }
                
            }
        }else{
            for (Food food : foods) {
                // indentify name of column nutrient
                for (HashMap<String, String> nutrient : nutrients) {
                    String columnN = nutrient.keySet().toArray()[0].toString();
                    if( nutrient.get(columnN).equals(columnSearch)){
                        columnSearch = columnN;
                        break;
                    }
                }
                if( food.getNutrients().get(columnSearch).toUpperCase().matches(".*"+search+".*")){
                    foodResults.add(food);
                }
            }
        }
        
        
        DefaultTableModel tblModel = (DefaultTableModel) tblResults.getModel();
        tblModel.setRowCount(0);
        
        String[] row = new String[nutrients.size()+1];
        int rowCount = 0;
        for (Food food : foodResults) {
            row[0] = food.getDescrip();
            for (int i = 0; i < nutrients.size(); i++) {
                String columnN = nutrients.get(i).keySet().toArray()[0].toString();
                row[i+1] = food.getNutrients().get(columnN);
            }
            tblModel.insertRow(rowCount, row);
            rowCount++;
        }
        tblResults.setModel(tblModel);
    }

    public void search()
    {
        FoodType foodType = (FoodType) cbxFoodType.getSelectedItem();
        int foodTypeId = foodType.getId();
        String columnSearch = cbxSearchBy.getSelectedItem().toString();
        String column = "unknown";
        if( columnSearch.equals("Descripcion")){
            column = "descrip";
        }else{
            for (HashMap<String, String> nutrient : nutrients) {
                String columnN = nutrient.keySet().toArray()[0].toString();
                if( nutrient.get(columnN).equals(columnSearch)){
                    column = columnN;
                    break;
                }
            }
        }
        
        String search = txtSearch.getText();
        foods = foodBO.searchWithNutrients(column, search, foodTypeId);
        
        DefaultTableModel tblModel = (DefaultTableModel) tblResults.getModel();
        tblModel.setRowCount(0);
        
        String[] row = new String[nutrients.size()+1];
        int rowCount = 0;
        for (Food food : foods) {
            row[0] = food.getDescrip();
            for (int i = 0; i < nutrients.size(); i++) {
                String columnN = nutrients.get(i).keySet().toArray()[0].toString();
                row[i+1] = food.getNutrients().get(columnN);
            }
            tblModel.insertRow(rowCount, row);
            rowCount++;
        }
        tblResults.setModel(tblModel);
    }
    
    public void initFoodType(JComboBox cbx){
        cbx.removeAllItems();
        foodTypes = foodTypeBO.list();
        cbx.addItem(new FoodType(0, "Todos", null));
        for (FoodType foodType : foodTypes)  cbx.addItem(foodType);
    }
    
    public void fillCbxSearchBy(JComboBox cbx){
        cbx.removeAllItems();
        cbx.addItem("Descripcion");
        for (HashMap<String, String> nutrient : nutrients) {
            String column = nutrient.keySet().toArray()[0].toString();
            cbx.addItem(nutrient.get(column));
        }
    }
    
    public void fillCbxUnitType(JComboBox cbx){
        cbx.removeAllItems();
        for (int i = 0; i < preparationDetail.getFood().getFactorUnits().size(); i++) {
            cbx.addItem(preparationDetail.getFood().getFactorUnits().get(i).getUnitType());
        }
    }
    
    public void initTable()
    {
        String[] headers = new String[nutrients.size()+1];
        
        headers[0] = "Descripcion";
        for (int i = 0; i < nutrients.size(); i++) {
            String column = nutrients.get(i).keySet().toArray()[0].toString();
            headers[i+1] = nutrients.get(i).get(column);
        }
        
        
        DefaultTableModel tblModel = new DefaultTableModel(null, headers) {
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        tblResults.setModel(tblModel);
        tblResults.getColumnModel().getColumn(0).setMinWidth(300);
        tblResults.getTableHeader().setReorderingAllowed(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSearchFood = new javax.swing.JPanel();
        cbxFoodType = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbxSearchBy = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResults = new javax.swing.JTable();
        pnlFood = new javax.swing.JPanel();
        cbxUnitType = new javax.swing.JComboBox<>();
        txtFactorUnit = new javax.swing.JTextField();
        txtAmount = new javax.swing.JTextField();
        txtDescrip = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        btnAccept = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setPreferredSize(new java.awt.Dimension(1000, 500));

        pnlSearchFood.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar Alimento"));

        cbxFoodType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Tipo");

        jLabel2.setText("Parametro");

        cbxSearchBy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
        });

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/btn_search.png"))); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        tblResults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblResults.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblResults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblResultsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblResults);

        javax.swing.GroupLayout pnlSearchFoodLayout = new javax.swing.GroupLayout(pnlSearchFood);
        pnlSearchFood.setLayout(pnlSearchFoodLayout);
        pnlSearchFoodLayout.setHorizontalGroup(
            pnlSearchFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchFoodLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSearchFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(pnlSearchFoodLayout.createSequentialGroup()
                        .addGroup(pnlSearchFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxFoodType, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSearchFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlSearchFoodLayout.createSequentialGroup()
                                .addComponent(cbxSearchBy, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSearch)))))
                .addContainerGap())
        );
        pnlSearchFoodLayout.setVerticalGroup(
            pnlSearchFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSearchFoodLayout.createSequentialGroup()
                .addGroup(pnlSearchFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSearchFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlSearchFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbxFoodType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxSearchBy, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlFood.setBorder(javax.swing.BorderFactory.createTitledBorder("Alimento"));

        cbxUnitType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxUnitType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxUnitTypeItemStateChanged(evt);
            }
        });

        txtFactorUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFactorUnitActionPerformed(evt);
            }
        });
        txtFactorUnit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFactorUnitKeyPressed(evt);
            }
        });

        txtAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAmountActionPerformed(evt);
            }
        });
        txtAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAmountKeyPressed(evt);
            }
        });

        txtDescrip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripActionPerformed(evt);
            }
        });
        txtDescrip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescripKeyPressed(evt);
            }
        });

        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });
        txtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIdKeyPressed(evt);
            }
        });

        btnAccept.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/btn_save.png"))); // NOI18N
        btnAccept.setText("ACEPTAR");
        btnAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFoodLayout = new javax.swing.GroupLayout(pnlFood);
        pnlFood.setLayout(pnlFoodLayout);
        pnlFoodLayout.setHorizontalGroup(
            pnlFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFoodLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlFoodLayout.createSequentialGroup()
                        .addComponent(cbxUnitType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(txtFactorUnit))
                    .addGroup(pnlFoodLayout.createSequentialGroup()
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtDescrip)))
                .addGap(18, 18, 18)
                .addGroup(pnlFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                    .addComponent(btnAccept, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlFoodLayout.setVerticalGroup(
            pnlFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFoodLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescrip, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(pnlFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFactorUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxUnitType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAccept, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlSearchFood, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlFood, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(pnlFood, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlSearchFood, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblResultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblResultsMouseClicked
        if( evt.getClickCount() == 2){
            food = foods.get(tblResults.getSelectedRow());
            food.setFactorUnits( factorUnitBO.listByFoodId(food.getId()) );
            
            txtId.setText(food.getId()+"");
            txtDescrip.setText(food.getDescrip());
            txtAmount.setText("1.0");
            
            preparationDetail.setFood(food);
            fillCbxUnitType(cbxUnitType);
        }
    }//GEN-LAST:event_tblResultsMouseClicked

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchLocal();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            search();
        }
    }//GEN-LAST:event_txtSearchKeyPressed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtDescripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripActionPerformed

    private void txtDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripKeyPressed

    private void txtFactorUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFactorUnitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFactorUnitActionPerformed

    private void txtFactorUnitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFactorUnitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFactorUnitKeyPressed

    private void txtAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAmountActionPerformed

    private void txtAmountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAmountKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAmountKeyPressed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void txtIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdKeyPressed

    private void btnAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptActionPerformed
        if( preparationView!=null ){
            preparationDetail.setId(0);
            preparationDetail.setAmount( Double.parseDouble(txtAmount.getText()) );
            preparationDetail.setPreparation( new Preparation() );
            preparationDetail.setFood( food );
            preparationView.addPreparationDetail(preparationDetail);
            this.setVisible(false);
        }
    }//GEN-LAST:event_btnAcceptActionPerformed

    private void cbxUnitTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxUnitTypeItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            int unitTypeId = ((UnitType) cbxUnitType.getSelectedItem()).getId();
            txtFactorUnit.setText("");
            for (int i = 0; i < preparationDetail.getFood().getFactorUnits().size(); i++) {
                if( preparationDetail.getFood().getFactorUnits().get(i).getUnitType().getId() == unitTypeId ){
                    preparationDetail.setFactorUnit(preparationDetail.getFood().getFactorUnits().get(i));
                    txtFactorUnit.setText( preparationDetail.getFactorUnit().getDescrip() );
                    break;
                }
            }
        }
    }//GEN-LAST:event_cbxUnitTypeItemStateChanged

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SearchFood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchFood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchFood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchFood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SearchFood dialog = new SearchFood(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccept;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cbxFoodType;
    private javax.swing.JComboBox<String> cbxSearchBy;
    private javax.swing.JComboBox<String> cbxUnitType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlFood;
    private javax.swing.JPanel pnlSearchFood;
    private javax.swing.JTable tblResults;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtDescrip;
    private javax.swing.JTextField txtFactorUnit;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
