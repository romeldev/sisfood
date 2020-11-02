/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.setting;

import bo.CompanyBO;
import common.Helper;
import common.Validator;
import entity.Company;
import java.awt.Cursor;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import view.assets.CompanyTM;
import view.assets.TableRender;

/**
 *
 * @author HP_RYZEN
 */
public class CompanyView extends javax.swing.JFrame {

    /**
     * Creates new form ComposicionView
     */
    CompanyBO companyBO = new CompanyBO();
    ArrayList<Company> list = new ArrayList<>();
    Company item = new Company();
    CompanyTM tblModel = new CompanyTM();
    
    public CompanyView() {
        initComponents();
        txtId.setEnabled(false);
        this.setLocationRelativeTo(null);
        tblList.getTableHeader().setReorderingAllowed(false) ;
        tblList.setModel(tblModel);
        tblList.setDefaultRenderer(Object.class, new TableRender());
        tblList.setRowHeight(30);
        fillList();
        tblList.getColumnModel().getColumn(0).setMaxWidth(50);
        tblList.getColumnModel().getColumn(2).setMaxWidth(30);
        tblList.getColumnModel().getColumn(3).setMaxWidth(30);
    }
    
    public void fillList()
    {
        list = companyBO.list();
        tblModel.setData(list);
        tblModel.fireTableDataChanged();
    }
    
    public boolean validator()
    {
        if( !Validator.isRequired(txtDescrip.getText())){
            JOptionPane.showMessageDialog(this, "Ingrese una descripcion");
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

        jPanel1 = new javax.swing.JPanel();
        txtId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtDescrip = new javax.swing.JTextField();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Item"));

        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });

        jLabel2.setText("Descripcion*");

        txtDescrip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripActionPerformed(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/btn_clear.png"))); // NOI18N
        btnCancel.setText("LIMPIAR");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/btn_save.png"))); // NOI18N
        btnSave.setText("GUARDAR");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescrip)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCancel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDescrip, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista"));

        tblList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblList.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tblListMouseMoved(evt);
            }
        });
        tblList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblListMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tblListMouseExited(evt);
            }
        });
        jScrollPane1.setViewportView(tblList);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clearForm()
    {
        item = new Company();
        txtId.setText("");
        txtDescrip.setText("");
        btnSave.setText("GUARDAR");
        btnSave.setIcon(Helper.icon("btn_save"));
    }
    
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void txtDescripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripActionPerformed

    private void tblListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListMouseClicked
        int col = tblList.columnAtPoint(evt.getPoint());
        int row = tblList.rowAtPoint(evt.getPoint());

        if( row < tblList.getRowCount() && row >-1 && col < tblList.getColumnCount() && col > -1 ){
            Object value = tblList.getValueAt(row, col);
            if ( value instanceof JButton){
                ((JButton)value).doClick();
                JButton btn = (JButton) value;
                item = list.get(row);
                
                if(btn.getName().equals("EDIT")){
                    txtId.setText(item.getId()+"");
                    txtDescrip.setText(item.getDescrip());
                    btnSave.setText("ACTUALIZAR");
                    btnSave.setIcon(Helper.icon("btn_update"));
                }
                
                if(btn.getName().equals("DELETE")){
                    int confirm = JOptionPane.showConfirmDialog(this, "Eliminar "+item.getDescrip()+"?", "Alerta", JOptionPane.YES_OPTION);
                    if( confirm == JOptionPane.YES_OPTION){
                        if( companyBO.delete(item) ){
                            fillList();
                            JOptionPane.showMessageDialog(this, item.getDescrip()+" eliminado!");
                            clearForm();
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_tblListMouseClicked

    private void tblListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListMouseEntered
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tblListMouseEntered

    private void tblListMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListMouseMoved
        if( tblList.columnAtPoint(evt.getPoint()) > 1){
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }else{
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_tblListMouseMoved

    private void tblListMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListMouseExited
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tblListMouseExited

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        int id = txtId.getText().equals("")? 0: Integer.parseInt(txtId.getText());
        item.setDescrip(txtDescrip.getText());
        item.setId(id);
        
        boolean success = false;
        String message = item.getDescrip();
        
        if(!validator() ) return;
        
        if( id == 0){
            success = companyBO.create( item );
            message += " guardado!";
        }else {
            success = companyBO.update( item );
            message += " actualizado!";
        }
        if( success ){
            fillList();
            clearForm();
            JOptionPane.showMessageDialog(this, message);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

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
            java.util.logging.Logger.getLogger(CompanyView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CompanyView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CompanyView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CompanyView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CompanyView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblList;
    private javax.swing.JTextField txtDescrip;
    private javax.swing.JTextField txtId;
    // End of variables declaration//GEN-END:variables
}
