/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import common.Helper;
import dao.CurrencyTypeDAO;
import dao.Conexion;
import entity.CurrencyType;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author HP_RYZEN
 */
public class CurrencyTypeBO {
    
    private CurrencyTypeDAO currencyTypeDAO = new CurrencyTypeDAO();
    
    public ArrayList<CurrencyType> list()
    {
        Connection conn = Conexion.getConnection();
        ArrayList<CurrencyType> list = null;
        try {
            list = Helper.castList(currencyTypeDAO.list(conn));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list;
    }
    
    public boolean update( Object item){
        Connection conn = Conexion.getConnection();
        return currencyTypeDAO.update(conn, item);
    }
    
    public boolean create( Object item ) {
        Connection conn = Conexion.getConnection();
        return currencyTypeDAO.create(conn, item);
    }
    
    public boolean delete( Object item ) {
        Connection conn = Conexion.getConnection();
        return currencyTypeDAO.delete(conn, item);
    }
}
