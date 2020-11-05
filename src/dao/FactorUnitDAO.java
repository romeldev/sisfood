/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FactorUnit;
import entity.Food;
import entity.UnitType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

/**
 *
 * @author HP_RYZEN
 */
public class FactorUnitDAO implements CRUD_FULL<FactorUnit>{

    
    private String table = "factor_units";
    
    @Override
    public ArrayList<FactorUnit> list(Connection conn) {
        Statement st = null;
        ResultSet rs = null;
        String sql = "select * from "+table;
        ArrayList<FactorUnit> list = null;
        
        try {
            list = new ArrayList<>();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                FactorUnit item = new FactorUnit();
                item.setId(rs.getInt("id"));
                item.setDescrip(rs.getString("descrip"));
                item.setFactor(rs.getDouble("factor"));
                item.setFood( new Food( rs.getInt("food_id")));
                item.setUnitType( new UnitType(rs.getInt("unit_type_id"), null));
                list.add(item);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return list;
    }

    @Override
    public FactorUnit read(Connection conn, Integer id) {
        PreparedStatement ps;
        ResultSet rs;
        String sql = "select * from "+table+" where id=?";
        FactorUnit item = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            item = new FactorUnit();
            if (rs.next()) {
                item.setId(rs.getInt("id"));
                item.setDescrip(rs.getString("descrip"));
                item.setFactor(rs.getDouble("factor"));
                item.setFood( new Food( rs.getInt("food_id")));
                item.setUnitType( new UnitType(rs.getInt("unit_type_id"), null));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return item;
    }

    @Override
    public boolean create(Connection conn, FactorUnit entity) {
        boolean status = false;
        PreparedStatement ps = null;
        String sql = "insert into "+table+" (food_id, unit_type_id, descrip, factor) ";
        sql += "values(?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, entity.getFood().getId());
            ps.setInt(2, entity.getUnitType().getId());
            ps.setString(3, entity.getDescrip());
            ps.setDouble(4, entity.getFactor());
            ps.execute();
            ps.close();
            status = true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return status;
    }

    @Override
    public boolean update(Connection conn, FactorUnit entity) {
        boolean status = false;
        PreparedStatement ps = null;
        String sql = "update "+table+" set food_id=?,unit_type_id=?,descrip=?,factor=? where id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, entity.getFood().getId());
            ps.setInt(2, entity.getUnitType().getId());
            ps.setString(3, entity.getDescrip());
            ps.setDouble(4, entity.getFactor());
            ps.setInt(5, entity.getId());
            ps.execute();
            ps.close();
            status = true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return status;
    }

    @Override
    public boolean delete(Connection conn, Integer id) {
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
    
    public ArrayList<FactorUnit> listByFoodId(Connection conn, int foodId) {
        PreparedStatement ps;
        ResultSet rs;
        String sql = "select fu.*, ut.descrip unit_type_descrip from "+table+" as fu";
        sql += " left join unit_types as ut on ut.id = fu.unit_type_id";
        sql += " where fu.food_id=?";
        ArrayList<FactorUnit> list = null;
        
        try {
            list = new ArrayList<>();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, foodId);
            rs = ps.executeQuery();
            while (rs.next()) {
                FactorUnit item = new FactorUnit();
                item.setId(rs.getInt("id"));
                item.setDescrip(rs.getString("descrip"));
                item.setFactor(rs.getDouble("factor"));
                item.setFood( new Food( rs.getInt("food_id")));
                item.setUnitType( new UnitType(rs.getInt("unit_type_id"), rs.getString("unit_type_descrip")));
                list.add(item);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return list;
    }

    public boolean createMultiple(Connection conn, ArrayList<FactorUnit> list) {
        boolean status = false;
        PreparedStatement ps = null;

        String values = String.join(",",Collections.nCopies(list.size(), "(?,?,?,?)"));
        String sql = "insert into "+table+" (food_id, unit_type_id, descrip, factor) values "+values;
        try {
            ps = conn.prepareStatement(sql);
            int index = 1;
            for (FactorUnit item : list) {
                ps.setInt(index++, item.getFood().getId());
                ps.setInt(index++, item.getUnitType().getId());
                ps.setString(index++, item.getDescrip());
                ps.setDouble(index++, item.getFactor());
            }
            ps.execute();
            ps.close();
            status = true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return status;
    }
    
    public boolean updateMultiple(Connection conn, ArrayList<FactorUnit> list) {
        boolean status = false;
        PreparedStatement ps = null;

        String sql = "update "+table+" set descrip=?,factor=? ";
        sql += "where food_id=? and unit_type_id=?;";
        sql = String.join("\n", Collections.nCopies(list.size(), sql));
        try {
            ps = conn.prepareStatement(sql);
            int index = 1;
            for (FactorUnit item : list) {
                ps.setString(index++, item.getDescrip());
                ps.setDouble(index++, item.getFactor());
                ps.setInt(index++, item.getFood().getId());
                ps.setInt(index++, item.getUnitType().getId());
            }
            ps.execute();
            ps.close();
            status = true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return status;
    }
    
    public boolean deleteByFoodId(Connection conn, int foodId) {
        boolean status = false;
        PreparedStatement ps = null;
        String sql = "delete from "+table+" where food_id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, foodId);
            ps.execute();
            ps.close();
            status = true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return status;
    }
}
