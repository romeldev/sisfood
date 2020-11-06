/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FactorUnit;
import entity.Food;
import entity.Preparation;
import entity.PreparationDetail;
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
public class PreparationDetailDAO implements CRUD_FULL<PreparationDetail>{
    
    public static void main(String[] args) {
        Connection conn = new Conexion().getConnection();
        PreparationDetailDAO dao = new PreparationDetailDAO();
        
        System.out.println(dao.listDetailOfPreparation(conn, 5).size());
    }
    
    private final String table = "preparation_details";

    @Override
    public ArrayList<PreparationDetail> list(Connection conn) {
        PreparedStatement ps;
        ResultSet rs;
        String sql = "select * from "+table;
        ArrayList<PreparationDetail> list = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                PreparationDetail item = new PreparationDetail();
                item.setId(rs.getInt("id"));
                item.setAmount(rs.getDouble("amount"));
                item.setPreparation( new Preparation(rs.getInt("preparation_id")) );
                item.setFood( new Food(rs.getInt("food_id")) );
                item.setFactorUnit( new FactorUnit(rs.getInt("factor_unit_id")) );
                list.add(item);
            }
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return list;
    }

    @Override
    public PreparationDetail read(Connection conn, Integer id) {
        PreparedStatement ps;
        ResultSet rs;
        String sql = "select * from "+table+" where id=?";
        PreparationDetail item = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            item = new PreparationDetail();
            if (rs.next()) {
                item.setId(rs.getInt("id"));
                item.setAmount(rs.getDouble("amount"));
                item.setPreparation( new Preparation(rs.getInt("preparation_id")) );
                System.out.println(item.getPreparation());
                item.setFood( new Food(rs.getInt("food_id")) );
                item.setFactorUnit( new FactorUnit(rs.getInt("food_id")) );
            }
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return item;
    }

    @Override
    public boolean create(Connection conn, PreparationDetail entity) {
        boolean status = false;
        PreparedStatement ps;
        ResultSet rs;
        String sql = "insert into "+table+" (amount, preparation_id, food_id, factor_unit_id) values (?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, entity.getAmount());
            ps.setInt(2, entity.getPreparation().getId());
            ps.setInt(3, entity.getFood().getId());
            ps.setInt(4, entity.getFactorUnit().getId());
            int rows  = ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if( rs.next()) entity.setId(rs.getInt(1));
            ps.close();
            status = true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return status;
    }

    @Override
    public boolean update(Connection conn, PreparationDetail entity) {
        boolean status = false;
        PreparedStatement ps;
        String sql = "update "+table+" set amount=?, preparation_id=?, food_id=?, factor_unit_id=? where id=?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, entity.getAmount());
            ps.setInt(2, entity.getPreparation().getId());
            ps.setInt(3, entity.getFood().getId());
            ps.setInt(4, entity.getFactorUnit().getId());
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
        PreparedStatement ps;
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
    
    
    public boolean createMultiple(Connection conn, ArrayList<PreparationDetail> list) {
        boolean status = false;
        PreparedStatement ps;
        ResultSet rs;
        
        String values = String.join(",",Collections.nCopies(list.size(), "(?,?,?,?)"));
        String sql = "insert into "+table+" (amount, preparation_id, food_id, factor_unit_id) values "+values;
        try {
            ps = conn.prepareStatement(sql);
            int index = 1;
            for (PreparationDetail entity : list) {
                System.out.println(entity.getAmount());
                ps.setDouble(index++, entity.getAmount());
                ps.setInt(index++, entity.getPreparation().getId());
                ps.setInt(index++, entity.getFood().getId());
                ps.setInt(index++, entity.getFactorUnit().getId());
            }
            System.out.println(ps);
            int rows  = ps.executeUpdate();
            System.out.println(rows);
            ps.close();
            status = true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return status;
    }
    
    
    public boolean deleteByPreparationId(Connection conn, Integer preparationId) {
        boolean status = false;
        PreparedStatement ps;
        String sql = "delete from "+table+" where preparation_id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, preparationId);
            ps.execute();
            ps.close();
            status = true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return status;
    }
    
    
    public ArrayList<PreparationDetail> listDetailOfPreparation(Connection conn, Integer preparationId) {
        PreparedStatement ps;
        ResultSet rs;
        String sql = "select pd.*,";
        sql += " f.descrip as food_descrip, f.food_type_id, ";
        sql += " fu.descrip as fu_descrip, fu.factor as fu_factor, fu.food_id as fu_food_id, fu.unit_type_id as fu_unit_type_id,";
        sql += " ut.descrip as ut_unit_type_descrip";
        sql += " from preparation_details as pd";
        sql += " left join foods as f on f.id = pd.food_id";
        sql += " left join factor_units as fu on fu.id = pd.factor_unit_id";
        sql += " left join unit_types as ut on ut.id = fu.unit_type_id";
        sql += " where pd.preparation_id = ?";
        
        System.out.println(sql);
        
        ArrayList<PreparationDetail> list = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, preparationId);
            rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                PreparationDetail item = new PreparationDetail();
                item.setId(rs.getInt("id"));
                item.setAmount(rs.getDouble("amount"));
                item.setPreparation( new Preparation(rs.getInt("preparation_id")) );
                item.setFood( new Food(rs.getInt("food_id"), rs.getString("food_descrip")));
                item.setFactorUnit( new FactorUnit(
                        rs.getInt("factor_unit_id"),
                        rs.getString("fu_descrip"),
                        rs.getDouble("fu_factor"),
                        new Food(rs.getInt("fu_food_id")),
                        new UnitType(rs.getInt("fu_unit_type_id"), rs.getString("ut_unit_type_descrip") )
                ));
                list.add(item);
            }
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return list;
    }
}
