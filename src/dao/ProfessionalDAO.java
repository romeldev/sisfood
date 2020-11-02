/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Professional;
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
public class ProfessionalDAO implements CRUD{

    private Professional entity;
    
    private String table = "professionals";
    
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
                Professional item = new Professional();
                item.setId( rs.getInt("id"));
                item.setFullname(rs.getString("fullname"));
                item.setProfession(rs.getString("profession"));
                item.setCode(rs.getString("code"));
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
        Professional item = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                item = new Professional();
                item.setId( rs.getInt("id"));
                item.setFullname(rs.getString("fullname"));
                item.setProfession(rs.getString("profession"));
                item.setCode(rs.getString("code"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return item;
    }

    @Override
    public boolean create(Connection conn, Object object) {
        entity = (Professional)object;
        boolean status = false;
        PreparedStatement ps = null;
        String sql = "insert into "+table+" (fullname, profession, code) ";
        sql += "values(?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getFullname());
            ps.setString(2, entity.getProfession());
            ps.setString(3, entity.getCode());
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
        entity = (Professional)object;
        boolean status = false;
        PreparedStatement ps = null;
        String sql = "update "+table+" set fullname=?, profession=?, code=? where id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getFullname());
            ps.setString(2, entity.getProfession());
            ps.setString(3, entity.getCode());
            ps.setInt(4, entity.getId());
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
        int id = (object instanceof Integer)? (Integer)object: ((Professional)object).getId();
        boolean status = false;
        PreparedStatement ps = null;
        String sql = "delete from "+table+" where id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            ps.close();
            status = true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return status;
    }
}
