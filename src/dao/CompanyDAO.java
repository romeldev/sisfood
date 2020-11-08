/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Company;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author HP_RYZEN
 */
public class CompanyDAO implements CRUD_FULL<Company>{
    
    private final String table = "companies";
    
    public boolean softdelete = true;

    @Override
    public ArrayList<Company> list(Connection conn, boolean soft) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from "+table;
        if( soft ) sql += " where deleted_at is null";
        ArrayList<Company> list = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Company item = new Company();
                item.setId(rs.getInt("id"));
                item.setDescrip(rs.getString("descrip"));
                list.add(item);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            try {
                DbUtils.close(rs);
                DbUtils.close(ps);
                DbUtils.close(conn);
            } catch (SQLException ex) {
                Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

    @Override
    public Company read(Connection conn, Integer id, boolean soft) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from "+table+" where id=?";
        if( soft ) sql += " and deleted_at is null";
        Company item = new Company();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                item.setId(rs.getInt("id"));
                item.setDescrip(rs.getString("descrip"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            try {
                DbUtils.close(rs);
                DbUtils.close(ps);
                DbUtils.close(conn);
            } catch (SQLException ex) {
                Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return item;
    }

    @Override
    public Integer create(Connection conn, Company entity) {
        Integer status = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "insert into "+table+" (descrip) values (?)";
        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entity.getDescrip());
            status  = ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if( rs.next()) entity.setId(rs.getInt(1));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            try {
                DbUtils.close(rs);
                DbUtils.close(ps);
                DbUtils.close(conn);
            } catch (SQLException ex) {
                Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return status;
    }

    @Override
    public Integer update(Connection conn, Company entity) {
        Integer status = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "update "+table+" set descrip=? where id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getDescrip());
            ps.setInt(2, entity.getId());
            status  = ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            try {
                DbUtils.close(rs);
                DbUtils.close(ps);
                DbUtils.close(conn);
            } catch (SQLException ex) {
                Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return status;
    }

    @Override
    public Integer delete(Connection conn, Integer id, boolean soft) {
        Integer status = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "delete from "+table+" where id=?";
        if( soft ) sql = "update "+table+" set deleted_at=CURRENT_TIMESTAMP where id=?";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            status  = ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            try {
                DbUtils.close(rs);
                DbUtils.close(ps);
                DbUtils.close(conn);
            } catch (SQLException ex) {
                Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return status;
    }
}
