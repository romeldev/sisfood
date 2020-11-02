/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import common.Helper;
import dao.UnitTypeDAO;
import dao.Conexion;
import entity.UnitType;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author HP_RYZEN
 */
public class UnitTypeBO {
    
    private UnitTypeDAO unitTypeDAO = new UnitTypeDAO();
    
    public ArrayList<UnitType> list()
    {
        Connection conn = Conexion.getConnection();
        ArrayList<UnitType> list = null;
        try {
            list = Helper.castList(unitTypeDAO.list(conn));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list;
    }
    
    public boolean update( Object item){
        Connection conn = Conexion.getConnection();
        return unitTypeDAO.update(conn, item);
    }
    
    public boolean create( Object item ) {
        Connection conn = Conexion.getConnection();
        return unitTypeDAO.create(conn, item);
    }
    
    public boolean delete( Object item ) {
        Connection conn = Conexion.getConnection();
        return unitTypeDAO.delete(conn, item);
    }
}
