/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Nutrient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author HP_RYZEN
 */
public class NutrientDAO implements CRUD {

    private HashMap<String, String> entity;

    private String table = "nutrients";

    private String[] nutrientColumns = Nutrient.Columns();

    @Override
    public ArrayList<Object> list(Connection conn) {
        Statement st = null;
        ResultSet rs = null;
        String sql = "select * from " + table;
        ArrayList<Object> list = null;

        try {
            list = new ArrayList<>();
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                HashMap<String, String> item = new HashMap<>();
                for (String column : nutrientColumns) {
                    item.put(column, rs.getString(column));
                }
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
        String sql = "select * from " + table + " where id=?";
        HashMap<String, String> item = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            item = new HashMap<String, String>();
            if (rs.next()) {
                for (String column : nutrientColumns) {
                    item.put(column, rs.getString(column));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return item;
    }

    @Override
    public boolean create(Connection conn, Object object) {
        entity = (HashMap<String, String>) object;
        boolean status = false;
        PreparedStatement ps = null;
        
        String columns = String.join(",", nutrientColumns);
        String values = String.join(",",Collections.nCopies(nutrientColumns.length, "?"));
        String sql = "insert into " + table + " ("+columns+") values("+values+")";
        try {
            ps = conn.prepareStatement(sql);
            int index = 1;
            for (String column : nutrientColumns) {
                ps.setString(index, entity.get(column));
                index++;
            }
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
        entity = (HashMap<String, String>) object;
        boolean status = false;
        PreparedStatement ps = null;
        
        String values = "";
        for (String column : nutrientColumns) values += column+"=?,";
        values = values.substring(0, values.length()-1);
        
        String sql = "update " + table + " set "+values+" where id=?";
        try {
            ps = conn.prepareStatement(sql);
            
            int index = 1;
            for (String column : nutrientColumns) {
                ps.setString(index, entity.get(column));
                index++;
            }
            ps.setInt(index, Integer.parseInt(entity.get("id")));
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
        int id = 0;
        if( object instanceof Integer ){
            id = (Integer) object;
        }else{
            HashMap<String, String> entity = (HashMap<String, String>) object;
            id = Integer.parseInt( entity.get("id") );
        }
        boolean status = false;
        PreparedStatement ps = null;
        String sql = "delete from " + table + " where id=?";
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
    
    
    public boolean deleteByFoodId(Connection conn, int foodId ) {

        boolean status = false;
        PreparedStatement ps = null;
        String sql = "delete from " + table + " where food_id=?";
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

    public Object findByFoodId(Connection conn, int foodId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from " + table + " where food_id=?";
        HashMap<String, String> item = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, foodId);
            rs = ps.executeQuery();
            item = new HashMap<>();
            while (rs.next()) {
                for (String column : nutrientColumns) {
                    item.put(column, rs.getString(column));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return item;
    }
    
    
    public boolean updateByFoodId(Connection conn, Object object) {
        entity = (HashMap<String, String>) object;
        boolean status = false;
        PreparedStatement ps = null;
        
        String values = "";
        for (String column : nutrientColumns) values += column+"=?,";
        values = values.substring(0, values.length()-1);
        
        String sql = "update " + table + " set "+values+" where food_id=?";
        try {
            ps = conn.prepareStatement(sql);
            
            int index = 1;
            for (String column : nutrientColumns) {
                ps.setString(index, entity.get(column));
                index++;
            }
            ps.setInt(index, Integer.parseInt(entity.get("food_id")));
            ps.execute();
            ps.close();
            status = true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return status;
    }

}
