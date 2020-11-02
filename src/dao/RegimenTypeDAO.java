/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Company;
import entity.RegimenType;
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
public class RegimenTypeDAO implements CRUD{

    private RegimenType entity;
    private String table = "regimen_types";
    
    @Override
    public ArrayList<Object> list(Connection conn) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select rt.*, c.descrip as company_descrip from "+table +" as rt ";
        sql += "left join companies as c on c.id = rt.company_id";
        
        ArrayList<Object> list = null;
        
        try {
            list = new ArrayList<>();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                RegimenType item = new RegimenType();
                item.setId( rs.getInt("id"));
                item.setDescrip(rs.getString("descrip"));
                item.setCompany(new Company(
                    rs.getInt("company_id"),
                    rs.getString("company_descrip")
                ));
                list.add(item);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return list;
    }
    
    public ArrayList<Object> listByCompanyId(Connection conn, int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select rt.*, c.descrip as company_descrip from "+table +" as rt";
        sql += " left join companies as c on c.id = rt.company_id";
        sql += " where rt.company_id=?";
        ArrayList<Object> list = null;
        
        try {
            list = new ArrayList<>();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                RegimenType item = new RegimenType();
                item.setId( rs.getInt("id"));
                item.setDescrip(rs.getString("descrip"));
                item.setCompany(new Company(
                    rs.getInt("company_id"),
                    rs.getString("company_descrip")
                ));
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
        String sql = "select rt.*, c.descrip as company_descrip from "+table +" as rt";
        sql += "left join companies as c on c.id = rt.company_id where rt.id=?";
        RegimenType item = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                item = new RegimenType();
                item.setId( rs.getInt("id"));
                item.setDescrip(rs.getString("descrip"));
                item.setCompany(new Company(
                    rs.getInt("company_id"),
                    rs.getString("company_descrip")
                ));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return item;
    }

    @Override
    public boolean create(Connection conn, Object object) {
        entity = (RegimenType)object;
        boolean status = false;
        PreparedStatement ps = null;
        String sql = "insert into "+table+" (descrip, company_id) ";
        sql += "values(?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getDescrip());
            ps.setInt(2, entity.getCompany().getId());
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
        entity = (RegimenType)object;
        boolean status = false;
        PreparedStatement ps = null;
        String sql = "update "+table+" set descrip=?, company_id=? where id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getDescrip());
            ps.setInt(2, entity.getCompany().getId());
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
        int id = (object instanceof Integer)? (Integer)object: ((RegimenType)object).getId();
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
