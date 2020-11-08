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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author HP_RYZEN
 */
public class PreparationDetailDAO implements CRUD_FULL<PreparationDetail>{
    
    public static void main(String[] args) {
        Connection conn = new Conexion().getConnection();
        PreparationDetailDAO dao = new PreparationDetailDAO();
        
        System.out.println(dao.listDetailOfPreparation(conn,1));
    }
    
    private final String table = "preparation_details";

    @Override
    public ArrayList<PreparationDetail> list(Connection conn) {
        PreparedStatement ps = null;
        ResultSet rs = null;
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
    public PreparationDetail read(Connection conn, Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
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
    public boolean create(Connection conn, PreparationDetail entity) {
        boolean status = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
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
            status = rows==1;
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
    public boolean update(Connection conn, PreparationDetail entity) {
        boolean status = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "update "+table+" set amount=?, preparation_id=?, food_id=?, factor_unit_id=? where id=?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, entity.getAmount());
            ps.setInt(2, entity.getPreparation().getId());
            ps.setInt(3, entity.getFood().getId());
            ps.setInt(4, entity.getFactorUnit().getId());
            ps.setInt(5, entity.getId());
            int rows  = ps.executeUpdate();
            status = rows==1;
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
    public boolean delete(Connection conn, Integer id) {
        boolean status = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "delete from "+table+" where id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int rows  = ps.executeUpdate();
            status = rows==1;
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
    
    public boolean createMultiple(Connection conn, ArrayList<PreparationDetail> list) {
        boolean status = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String values = String.join(",",Collections.nCopies(list.size(), "(?,?,?,?)"));
        String sql = "insert into "+table+" (amount, preparation_id, food_id, factor_unit_id) values "+values;
        try {
            ps = conn.prepareStatement(sql);
            int index = 1;
            for (PreparationDetail entity : list) {
                ps.setDouble(index++, entity.getAmount());
                ps.setInt(index++, entity.getPreparation().getId());
                ps.setInt(index++, entity.getFood().getId());
                ps.setInt(index++, entity.getFactorUnit().getId());
            }
            int rows  = ps.executeUpdate();
            status = list.size()==rows;
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
    
    public boolean deleteByPreparationId(Connection conn, Integer preparationId) {
        boolean status = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "delete from "+table+" where preparation_id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, preparationId);
            int rows  = ps.executeUpdate();
            System.out.println(rows);
            status = rows==1;
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
    
    public ArrayList<PreparationDetail> listDetailOfPreparation(Connection conn, Integer preparationId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select pd.preparation_id,\n" +
        "pd.id, pd.amount,\n" +
        "f.id as food_id, f.descrip as food_descrip,\n" +
        "fu.id as factor_unit_id, fu.descrip as factor_unit_descrip,\n" +
        "ut.id as unit_type_id, ut.descrip as unit_type_descrip\n" +
        "from preparation_details as pd\n" +
        "left join foods as f on f.id = pd.food_id\n" +
        "left join factor_units as fu on fu.id = pd.factor_unit_id\n" +
        "left join unit_types as ut on ut.id = fu.unit_type_id\n" +
        "where pd.preparation_id=?";
        
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
                        rs.getString("factor_unit_descrip"),
                        new Food(rs.getInt("food_id"), rs.getString("food_descrip")),
                        new UnitType(rs.getInt("unit_type_id"), rs.getString("unit_type_descrip") )
                ));
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
}
