/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import common.Helper;
import dao.CompanyDAO;
import dao.Conexion;
import entity.Company;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author HP_RYZEN
 */
public class CompanyBO {
    
    private CompanyDAO companyDAO = new CompanyDAO();
    
    public ArrayList<Company> list()
    {
        Connection conn = Conexion.getConnection();
        ArrayList<Company> list = companyDAO.list(conn);
        return list;
    }
    
    public boolean create( Company item ) {
        Connection conn = Conexion.getConnection();
        return companyDAO.create(conn, item);
    }
    
    public boolean update( Company item){
        Connection conn = Conexion.getConnection();
        return companyDAO.update(conn, item);
    }
    
    public boolean delete( Company item ) {
        Connection conn = Conexion.getConnection();
        return companyDAO.delete(conn, item.getId());
    }
}
