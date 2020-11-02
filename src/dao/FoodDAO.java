/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Company;
import entity.Food;
import entity.FoodType;
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
public class FoodDAO implements CRUD_FULL<Food>{
    public static void main(String[] args) {
        Connection conn = Conexion.getConnection();
        FoodDAO dao = new FoodDAO();
        Food item = new Food(22, "romel editado", new FoodType(2));
        System.out.println(dao.delete(conn, item.getId()));
        System.out.println(item);
    }
    private String table = "foods";
    
    @Override
    public ArrayList<Food> list(Connection conn) {
        PreparedStatement ps;
        ResultSet rs;
        String sql = "select * from "+table;
        ArrayList<Food> list = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                Food item = new Food();
                item.setId(rs.getInt("id"));
                item.setDescrip(rs.getString("descrip"));
                item.setFoodType(new FoodType(rs.getInt("food_type_id")));
                list.add(item);
            }
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return list;
    }

    @Override
    public Food read(Connection conn, Integer id) {
        PreparedStatement ps;
        ResultSet rs;
        String sql = "select * from "+table;
        Food item = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            item = new Food();
            if (rs.next()) {
                item.setId(rs.getInt("id"));
                item.setDescrip(rs.getString("descrip"));
                item.setFoodType(new FoodType(rs.getInt("food_type_id")));
            }
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return item;
    }

    @Override
    public boolean create(Connection conn, Food entity) {
        boolean status = false;
        PreparedStatement ps;
        ResultSet rs;
        String sql = "insert into "+table+" (descrip, food_type_id) values (?,?)";
        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entity.getDescrip());
            ps.setInt(2, entity.getFoodType().getId());
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
    public boolean update(Connection conn, Food entity) {
        boolean status = false;
        PreparedStatement ps;
        String sql = "update "+table+" set descrip=?, food_type_id=? where id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getDescrip());
            ps.setInt(2, entity.getFoodType().getId());
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
    
    public ArrayList<Food> search(Connection conn, String column, String search, int foodTypeId) {
        PreparedStatement ps;
        ResultSet rs;
        String sql = "select * from "+table+" where "+column+" like '%"+search+"%'";
        if( foodTypeId>0 ) sql += " and food_type_id="+foodTypeId;
        ArrayList<Food> list = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                Food item = new Food();
                item.setId(rs.getInt("id"));
                item.setDescrip(rs.getString("descrip"));
                item.setFoodType(new FoodType(rs.getInt("food_type_id")));
                list.add(item);
            }
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return list;
    }
}
