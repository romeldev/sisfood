/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import common.Helper;
import dao.PreparationTypeDAO;
import dao.Conexion;
import entity.PreparationType;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author HP_RYZEN
 */
public class PreparationTypeBO {
    
    private PreparationTypeDAO preparationTypeDAO = new PreparationTypeDAO();
    
    public ArrayList<PreparationType> list()
    {
        Connection conn = Conexion.getConnection();
        ArrayList<PreparationType> list = null;
        try {
            list = Helper.castList(preparationTypeDAO.list(conn));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list;
    }
    
    public boolean update( PreparationType item){
        Connection conn = Conexion.getConnection();
        return preparationTypeDAO.update(conn, item);
    }
    
    public boolean create( PreparationType item ) {
        Connection conn = Conexion.getConnection();
        return preparationTypeDAO.create(conn, item);
    }
    
    public boolean delete( PreparationType item ) {
        Connection conn = Conexion.getConnection();
        return preparationTypeDAO.delete(conn, item.getId());
    }
}
