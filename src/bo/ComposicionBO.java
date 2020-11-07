/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import common.Helper;
import dao.CompositionDAO;
import dao.Conexion;
import entity.Composition;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author HP_RYZEN
 */
public class ComposicionBO {
    
    private CompositionDAO composicionDAO = new CompositionDAO();
    
    
    public ArrayList<Composition> list()
    {
        Connection conn = Conexion.getConnection();
        ArrayList<Composition> list = composicionDAO.list(conn);
        return list;
    }
    
    public boolean update( Composition item){
        Connection conn = Conexion.getConnection();
        return composicionDAO.update(conn, item);
    }
    
    public boolean create( Composition item ) {
        Connection conn = Conexion.getConnection();
        return composicionDAO.create(conn, item);
    }
    
    public boolean delete( Composition item ) {
        Connection conn = Conexion.getConnection();
        return composicionDAO.delete(conn, item.getId());
    }

}
