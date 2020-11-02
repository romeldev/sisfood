/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import common.Helper;
import dao.CompositionDAO;
import dao.Conexion;
import entity.Composicion;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author HP_RYZEN
 */
public class ComposicionBO {
    
    private CompositionDAO composicionDAO = new CompositionDAO();
    
    
    public ArrayList<Composicion> list()
    {
        Connection conn = Conexion.getConnection();
        ArrayList<Composicion> list = null;
        try {
            list = Helper.castList(composicionDAO.list(conn));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list;
    }
    
    public boolean update( Object item){
        Connection conn = Conexion.getConnection();
        return composicionDAO.update(conn, item);
    }
    
    public boolean create( Object item ) {
        Connection conn = Conexion.getConnection();
        return composicionDAO.create(conn, item);
    }
    
    public boolean delete( Object item ) {
        Connection conn = Conexion.getConnection();
        return composicionDAO.delete(conn, item);
    }

}
