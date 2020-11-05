/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Company;
import entity.Preparation;
import entity.PreparationType;
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
public class PreparationDAO implements CRUD_FULL<Preparation>{
    
    public static void main(String[] args) {
        Connection conn = new Conexion().getConnection();
        PreparationDAO dao = new PreparationDAO();
        
        Preparation item = new Preparation();
        item.setId(31);
        item.setDescrip("romel editado");
        item.setCompany(new Company(1));
        item.setPreparationType(new PreparationType(1));
        
        System.out.println(dao.delete(conn, item.getId()));
        System.out.println(item);
    }
    
    
    private final String table = "preparations";

    @Override
    public ArrayList<Preparation> list(Connection conn) {
        PreparedStatement ps;
        ResultSet rs;
        String sql = "select * from "+table;
        ArrayList<Preparation> list = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                Preparation item = new Preparation();
                item.setId(rs.getInt("id"));
                item.setDescrip(rs.getString("descrip"));
                item.setPreparationType(new PreparationType(rs.getInt("preparation_type_id")));
                item.setCompany(new Company(rs.getInt("preparation_type_id")));
                list.add(item);
            }
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return list;
    }

    @Override
    public Preparation read(Connection conn, Integer id) {
        PreparedStatement ps;
        ResultSet rs;
        String sql = "select * from "+table+" where id=?";
        Preparation item = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            item = new Preparation();
            if (rs.next()) {
                item.setId(rs.getInt("id"));
                item.setDescrip(rs.getString("descrip"));
            }
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return item;
    }

    @Override
    public boolean create(Connection conn, Preparation entity) {
        boolean status = false;
        PreparedStatement ps;
        ResultSet rs;
        String sql = "insert into "+table+" (descrip, preparation_type_id, company_id) values (?,?,?)";
        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entity.getDescrip());
            ps.setInt(2, entity.getPreparationType().getId());
            ps.setInt(3, entity.getCompany().getId());
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
    public boolean update(Connection conn, Preparation entity) {
        boolean status = false;
        PreparedStatement ps;
        String sql = "update "+table+" set descrip=?, preparation_type_id=?, company_id=? where id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getDescrip());
            ps.setInt(2, entity.getPreparationType().getId());
            ps.setInt(3, entity.getCompany().getId());
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
}
