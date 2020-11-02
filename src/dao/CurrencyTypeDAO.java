/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.CurrencyType;
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
public class CurrencyTypeDAO implements CRUD{

    private CurrencyType entity;
    
    private String table = "currency_types";
    
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
                CurrencyType item = new CurrencyType();
                item.setId( rs.getInt("id"));
                item.setDescrip(rs.getString("descrip"));
                item.setSymbol(rs.getString("symbol"));
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
        CurrencyType item = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                item = new CurrencyType();
                item.setId( rs.getInt("id"));
                item.setDescrip(rs.getString("descrip"));
                item.setSymbol(rs.getString("symbol"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return item;
    }

    @Override
    public boolean create(Connection conn, Object object) {
        entity = (CurrencyType)object;
        boolean status = false;
        PreparedStatement ps = null;
        String sql = "insert into "+table+" (descrip, symbol) ";
        sql += "values(?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getDescrip());
            ps.setString(2, entity.getSymbol());
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
        entity = (CurrencyType)object;
        boolean status = false;
        PreparedStatement ps = null;
        String sql = "update "+table+" set descrip=?, symbol=? where id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getDescrip());
            ps.setString(2, entity.getSymbol());
            ps.setInt(3, entity.getId());
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
        entity = (CurrencyType)object;
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
