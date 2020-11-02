/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import common.Helper;
import dao.RegimenTypeDAO;
import dao.Conexion;
import entity.RegimenType;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author HP_RYZEN
 */
public class RegimenTypeBO {
    
    private RegimenTypeDAO regimenTypeDAO = new RegimenTypeDAO();
    
    public ArrayList<RegimenType> list()
    {
        Connection conn = Conexion.getConnection();
        ArrayList<RegimenType> list = null;
        try {
            list = Helper.castList(regimenTypeDAO.list(conn));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list;
    }
    
    public ArrayList<RegimenType> listByCompanyId(int id)
    {
        Connection conn = Conexion.getConnection();
        ArrayList<RegimenType> list = null;
        try {
            list = Helper.castList(regimenTypeDAO.listByCompanyId(conn, id));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list;
    }
    
    public boolean update( Object item){
        Connection conn = Conexion.getConnection();
        return regimenTypeDAO.update(conn, item);
    }
    
    public boolean create( Object item ) {
        Connection conn = Conexion.getConnection();
        return regimenTypeDAO.create(conn, item);
    }
    
    public boolean delete( Object item ) {
        Connection conn = Conexion.getConnection();
        return regimenTypeDAO.delete(conn, item);
    }
}
