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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author HP_RYZEN
 */
public class CurrencyTypeDAO implements CRUD_FULL<CurrencyType>{

    private String table = "currency_types";
    
    @Override
    public ArrayList<CurrencyType> list(Connection conn, boolean soft) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from "+table;
        if( soft ) sql += " where deleted_at is null";
        ArrayList<CurrencyType> list = new ArrayList<>();
        
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                CurrencyType item = new CurrencyType();
                item.setId( rs.getInt("id"));
                item.setDescrip(rs.getString("descrip"));
                item.setSymbol(rs.getString("symbol"));
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
    public CurrencyType read(Connection conn, Integer id, boolean soft) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from "+table+" where id=?";
        if( soft ) sql += " and deleted_at is null";
        CurrencyType item = new CurrencyType();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                item.setId( rs.getInt("id"));
                item.setDescrip(rs.getString("descrip"));
                item.setSymbol(rs.getString("symbol"));
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
    public Integer create(Connection conn, CurrencyType entity) {
        Integer status = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "insert into "+table+" (descrip, symbol) ";
        sql += "values(?,?)";
        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entity.getDescrip());
            ps.setString(2, entity.getSymbol());
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
    public Integer update(Connection conn, CurrencyType entity) {
        Integer status = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "update "+table+" set descrip=?, symbol=? where id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getDescrip());
            ps.setString(2, entity.getSymbol());
            ps.setInt(3, entity.getId());
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
