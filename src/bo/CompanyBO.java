/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import common.Helper;
import dao.CompanyDAO;
import dao.Conexion;
import dao.DB;
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
    
    public ArrayList<Company> list(){
        return companyDAO.list(DB.conn(), true);
    }
    
    public boolean create( Company item ){
        return companyDAO.create(DB.conn(), item)==1;
    }
    
    public boolean update( Company item){
        return companyDAO.update(DB.conn(), item)==1;
    }
    
    public boolean delete( Company item ) {
        return companyDAO.delete(DB.conn(), item.getId(), true)==1;
    }
}
