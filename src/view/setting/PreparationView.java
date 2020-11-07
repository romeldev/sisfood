/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.setting;

import bo.CompanyBO;
import bo.PreparationBO;
import bo.PreparationTypeBO;
import common.Validator;
import entity.Company;
import entity.Food;
import entity.FoodType;
import entity.Preparation;
import entity.PreparationDetail;
import entity.PreparationType;
import java.awt.Cursor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import view.assets.PreparationDetailTM;
import view.assets.PreparationTM;
import view.assets.TableRender;

/**
 *
 * @author HP_RYZEN
 */
public class PreparationView extends javax.swing.JFrame {

    /**
     * Creates new form Preparation
     */
    PreparationBO preparationBO = new PreparationBO();
    CompanyBO companyBO = new CompanyBO();
    PreparationTypeBO preparationTypeBO = new PreparationTypeBO();
    ArrayList<Preparation> preparations = new ArrayList<>();
    ArrayList<PreparationType> preparationTypes = new ArrayList<>();
    ArrayList<Company> companies = new ArrayList<>();
    Preparation preparation = new Preparation();
    
    PreparationTM preparationTM = new PreparationTM();
    PreparationDetailTM preparationDetailTM = new PreparationDetailTM();
    
    SearchFood searchFood;
    
    public PreparationView() {
        initComponents();
        this.setLocationRelativeTo(null);
        txtId.setEnabled(false);
        
        clearForm();
        initCbxCompany(cbxCompany);
        initCbxPreparationType(cbxPreparationType);
        initTablePreparation();
        initTablePreparationDetail();
        searchPreparation();
        
        searchFood = new SearchFood(this, true);
        searchFood.setPreparationView(this);
    }
    
    private void initCbxCompany(JComboBox cbx){
        cbx.removeAllItems();
        companies = companyBO.list();
        companies.add(0, new Company(0, "Seleccione"));
        for (Company company : companies) {
            cbx.addItem(company);
        }
        if( companies.size() > 1) cbx.setSelectedIndex(1);

        cbx.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    searchPreparation();
                }
            }
        });
    }
    
    private void initCbxPreparationType(JComboBox cbx){
        cbx.removeAllItems();
        preparationTypes = preparationTypeBO.list();
        preparationTypes.add(0, new PreparationType(0, "Seleccione"));
        for (PreparationType preparationType : preparationTypes) {
            cbx.addItem(preparationType);
        }
        
        if( preparationTypes.size() > 1) cbx.setSelectedIndex(1);
        cbx.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    searchPreparation();
                }
            }
        });
    }
    
    private void initTablePreparation(){
        tblPreparations.setDefaultRenderer(Object.class, new TableRender());
        tblPreparations.setModel(preparationTM);
        tblPreparations.setRowHeight(30);
        tblPreparations.getColumnModel().getColumn(0).setMaxWidth(50);
        tblPreparations.getColumnModel().getColumn(2).setMaxWidth(30);
    }
    
    private void initTablePreparationDetail(){
        tblPreparationDetail.setDefaultRenderer(Object.class, new TableRender());
        tblPreparationDetail.setModel(preparationDetailTM);
        tblPreparationDetail.setRowHeight(30);
        tblPreparationDetail.getColumnModel().getColumn(3).setMaxWidth(50);
        tblPreparationDetail.getColumnModel().getColumn(4).setMaxWidth(30);
        tblPreparationDetail.getColumnModel().getColumn(5).setMaxWidth(30);
    }
    
    private void searchPreparation(){
        int companyId = ((Company) cbxCompany.getSelectedItem()).getId();
        int preparationTypeId = ((PreparationType) cbxPreparationType.getSelectedItem()).getId();
        
        preparations = preparationBO.search( companyId, preparationTypeId ,txtSearchPreparation.getText());
        preparationTM.setData(preparations);
        preparationTM.fireTableDataChanged();
    }
    
    private void clearForm(){
        preparation = new Preparation();
        txtId.setText(null);
        txtDescrip.setText(null);
        
        btnDelete.setVisible(false);
        preparationDetailTM.getData().clear();
        preparationDetailTM.fireTableDataChanged();
        tblPreparations.getSelectionModel().clearSelection();
    }
    
    private void editForm(){
        preparation.setPreparationDetails(preparationBO.getPreparationDetails(preparation.getId()));
        txtId.setText(preparation.getId()+"");
        txtDescrip.setText(preparation.getDescrip());
        
        for (int i = 0; i < preparationTypes.size(); i++) {
            if( preparation.getPreparationType().getId() == preparationTypes.get(i).getId()){
                cbxPreparationType.setSelectedIndex(i);
                break;
            }
        }
        preparationDetailTM.setData(preparation.getPreparationDetails());
        preparationDetailTM.fireTableDataChanged();
        btnDelete.setVisible(true);
    }
    
    
    private int indexDetailEdit = -1;
    
    public void addPreparationDetail( PreparationDetail item){
        
        Food food = new Food();
        food.setId( item.getFood().getId());
        food.setDescrip( item.getFood().getDescrip());
        
        item = new PreparationDetail( 
            item.getId(), 
            preparation, 
            food,
            item.getFactorUnit(), 
            item.getAmount()
        );
        
        
        
        
        if( indexDetailEdit == -1){
            preparation.getPreparationDetails().add(item);
        }else{
            preparation.getPreparationDetails().set(indexDetailEdit, item);
        }

        
        preparationDetailTM.setData(preparation.getPreparationDetails());
        preparationDetailTM.fireTableDataChanged();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPreparaciones = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPreparations = new javax.swing.JTable();
        txtSearchPreparation = new javax.swing.JTextField();
        pnlPreparacion = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPreparationDetail = new javax.swing.JTable();
        txtDescrip = new javax.swing.JTextField();
        btnAddFood = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        txtId = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbxCompany = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cbxPreparationType = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlPreparaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de preparaciones"));

        tblPreparations.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblPreparations.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tblPreparationsMouseMoved(evt);
            }
        });
        tblPreparations.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPreparationsMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tblPreparationsMouseExited(evt);
            }
        });
        jScrollPane1.setViewportView(tblPreparations);

        txtSearchPreparation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchPreparationActionPerformed(evt);
            }
        });
        txtSearchPreparation.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchPreparationKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout pnlPreparacionesLayout = new javax.swing.GroupLayout(pnlPreparaciones);
        pnlPreparaciones.setLayout(pnlPreparacionesLayout);
        pnlPreparacionesLayout.setHorizontalGroup(
            pnlPreparacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPreparacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPreparacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                    .addComponent(txtSearchPreparation, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlPreparacionesLayout.setVerticalGroup(
            pnlPreparacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPreparacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearchPreparation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlPreparacion.setBorder(javax.swing.BorderFactory.createTitledBorder("Preparacion"));

        jLabel2.setText("Descripcion*");

        tblPreparationDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblPreparationDetail.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tblPreparationDetailMouseMoved(evt);
            }
        });
        tblPreparationDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPreparationDetailMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tblPreparationDetailMouseExited(evt);
            }
        });
        jScrollPane2.setViewportView(tblPreparationDetail);

        txtDescrip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripActionPerformed(evt);
            }
        });

        btnAddFood.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/btn_add.png"))); // NOI18N
        btnAddFood.setText("ALIMENTO");
        btnAddFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFoodActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/btn_save.png"))); // NOI18N
        btnSave.setToolTipText("Guardar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/btn_clear.png"))); // NOI18N
        btnCancel.setToolTipText("Limpiar");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/btn_delete.png"))); // NOI18N
        btnDelete.setToolTipText("Eliminar");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPreparacionLayout = new javax.swing.GroupLayout(pnlPreparacion);
        pnlPreparacion.setLayout(pnlPreparacionLayout);
        pnlPreparacionLayout.setHorizontalGroup(
            pnlPreparacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPreparacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPreparacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPreparacionLayout.createSequentialGroup()
                        .addGap(0, 333, Short.MAX_VALUE)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave))
                    .addComponent(txtDescrip, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
                    .addGroup(pnlPreparacionLayout.createSequentialGroup()
                        .addGroup(pnlPreparacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddFood)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlPreparacionLayout.setVerticalGroup(
            pnlPreparacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPreparacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPreparacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtId, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDescrip, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAddFood, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(pnlPreparacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel4.setText("EMPRESA");

        cbxCompany.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxCompanyItemStateChanged(evt);
            }
        });

        jLabel3.setText("CATEGORIA");

        cbxPreparationType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPreparationTypeItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlPreparacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxPreparationType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxCompany, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addComponent(pnlPreparaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxPreparationType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(pnlPreparacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(pnlPreparaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchPreparationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchPreparationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchPreparationActionPerformed

    private void txtDescripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripActionPerformed

    private void btnAddFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFoodActionPerformed
        indexDetailEdit = -1;
        searchFood.open(null);
    }//GEN-LAST:event_btnAddFoodActionPerformed

    public boolean validator()
    {
        int companyId = ((Company)cbxCompany.getSelectedItem()).getId();
        int preparationTypeId = ((PreparationType)cbxPreparationType.getSelectedItem()).getId();
        
        if( companyId==0){
            JOptionPane.showMessageDialog(this, "Seleccione una compania");
            cbxCompany.requestFocus();
            return false;
        }
        
        if( preparationTypeId==0){
            JOptionPane.showMessageDialog(this, "Seleccione un tipo de preparacion");
            cbxPreparationType.requestFocus();
            return false;
        }
        
        if( !Validator.isRequired(txtDescrip.getText())){
            JOptionPane.showMessageDialog(this, "Ingrese una descripcion");
            txtDescrip.requestFocus();
            return false;
        }
        
        if( preparation.getPreparationDetails().size()==0){
            JOptionPane.showMessageDialog(this, "Agrege al menos un alimento");
            btnAddFood.requestFocus();
            return false;
        }

        return true;
    }
    
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        //validar datos
        
        if (!validator()) return;
        
        preparation.setDescrip( txtDescrip.getText() );
        preparation.setPreparationType( ((PreparationType)cbxPreparationType.getSelectedItem()) );
        preparation.setCompany(((Company)cbxCompany.getSelectedItem()) );
        preparationBO.save(preparation);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        clearForm();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtSearchPreparationKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchPreparationKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            searchPreparation();
        }
    }//GEN-LAST:event_txtSearchPreparationKeyPressed

    private void tblPreparationsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPreparationsMouseClicked
        // TODO add your handling code here:
        int col = tblPreparations.columnAtPoint(evt.getPoint());
        int row = tblPreparations.rowAtPoint(evt.getPoint());

        if( row < tblPreparations.getRowCount() && row >-1 && col < tblPreparations.getColumnCount() && col > -1 ){
            Object value = tblPreparations.getValueAt(row, col);
            if ( value instanceof JButton){
                JButton btn = (JButton) value;
                preparation = preparations.get(row);
                if(btn.getName().equals("EDIT")){
                    editForm();
                }
            }
        }
    }//GEN-LAST:event_tblPreparationsMouseClicked

    private void cbxCompanyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCompanyItemStateChanged

    }//GEN-LAST:event_cbxCompanyItemStateChanged

    private void cbxPreparationTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPreparationTypeItemStateChanged

    }//GEN-LAST:event_cbxPreparationTypeItemStateChanged

    private void tblPreparationDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPreparationDetailMouseClicked
        int col = tblPreparationDetail.columnAtPoint(evt.getPoint());
        int row = tblPreparationDetail.rowAtPoint(evt.getPoint());

        if( row < tblPreparationDetail.getRowCount() && row >-1 && col < tblPreparationDetail.getColumnCount() && col > -1 ){
            Object value = tblPreparationDetail.getValueAt(row, col);
            if ( value instanceof JButton){
                JButton btn = (JButton) value;
                if(btn.getName().equals("DELETE")){
                    preparation.getPreparationDetails().remove(row);
                    preparationDetailTM.setData(preparation.getPreparationDetails());
                    preparationDetailTM.fireTableDataChanged();
                }
                
                if(btn.getName().equals("EDIT")){
                    indexDetailEdit = row;
                    PreparationDetail pd = preparation.getPreparationDetails().get(row);
                    searchFood.open(pd);
                }
            }
        }
    }//GEN-LAST:event_tblPreparationDetailMouseClicked

    private void tblPreparationsMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPreparationsMouseMoved
        if( tblPreparations.columnAtPoint(evt.getPoint()) > 1){
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }else{
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_tblPreparationsMouseMoved

    private void tblPreparationsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPreparationsMouseExited
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tblPreparationsMouseExited

    private void tblPreparationDetailMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPreparationDetailMouseMoved
        if( tblPreparationDetail.columnAtPoint(evt.getPoint()) > 3){
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }else{
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_tblPreparationDetailMouseMoved

    private void tblPreparationDetailMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPreparationDetailMouseExited
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tblPreparationDetailMouseExited

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
            java.util.logging.Logger.getLogger(PreparationView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PreparationView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PreparationView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PreparationView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PreparationView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddFood;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbxCompany;
    private javax.swing.JComboBox<String> cbxPreparationType;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnlPreparacion;
    private javax.swing.JPanel pnlPreparaciones;
    private javax.swing.JTable tblPreparationDetail;
    private javax.swing.JTable tblPreparations;
    private javax.swing.JTextField txtDescrip;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtSearchPreparation;
    // End of variables declaration//GEN-END:variables
}
