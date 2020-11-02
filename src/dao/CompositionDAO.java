/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Composicion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author HP_RYZEN
 */
public class CompositionDAO implements CRUD{

    private Composicion entity;
    
    private String table = "compositions";
    
    @Override
    public ArrayList<Object> list(Connection conn) {
        Statement st = null;
        ResultSet rs = null;
        String sql = "select * from "+table;
        ArrayList<Object> list = null;
        
        try {
            list = new ArrayList<>();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Composicion item = new Composicion();
                item.setId( rs.getInt("id"));
                item.setDescrip(rs.getString("descrip"));
                list.add(item);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return list;
    }

    @Override
    public Object read(Connection conn, Object object) {
        int id = (int) object;
        PreparedStatement ps;
        ResultSet rs;
        String sql = "select * from "+table+" where id=?";
        Composicion item = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                item = new Composicion();
                item.setId( rs.getInt("id"));
                item.setDescrip(rs.getString("descrip"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return item;
    }

    @Override
    public boolean create(Connection conn, Object object) {
        entity = (Composicion)object;
        boolean status = false;
        PreparedStatement ps = null;
        String sql = "insert into "+table+" (descrip) ";
        sql += "values(?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getDescrip());
            ps.execute();
            ps.close();
            status = true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return status;
    }

    @Override
    public boolean update(Connection conn, Object object) {
        entity = (Composicion)object;
        boolean status = false;
        PreparedStatement ps = null;
        String sql = "update "+table+" set descrip=? where id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getDescrip());
            ps.setInt(2, entity.getId());
            ps.execute();
            ps.close();
            status = true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return status;
    }

    @Override
    public boolean delete(Connection conn, Object object) {
        entity = (Composicion)object;
        boolean status = false;
        PreparedStatement ps = null;
        String sql = "delete from "+table+" where id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, entity.getId());
            ps.execute();
            ps.close();
            status = true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return status;
    }
    
}
