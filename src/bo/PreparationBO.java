/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import dao.Conexion;
import dao.FactorUnitDAO;
import dao.PreparationDAO;
import dao.PreparationDetailDAO;
import entity.Preparation;
import entity.PreparationDetail;
import java.sql.Connection;
/**
 *
 * @author HP_RYZEN
 */
public class PreparationBO {
    
    private PreparationDAO preparationDAO = new PreparationDAO();
    private PreparationDetailDAO preparationDetailDAO = new PreparationDetailDAO();
    private FactorUnitDAO factorUnitDAO = new FactorUnitDAO();
    
    public ArrayList<Preparation> list()
    {
        Connection conn = Conexion.getConnection();
        ArrayList<Preparation> list = preparationDAO.list(conn);
        return list;
    }
    
    public ArrayList<Preparation> search(int companyId, int preparationTypeId, String search)
    {
        Connection conn = Conexion.getConnection();
        ArrayList<Preparation> list = preparationDAO.search(conn, companyId, preparationTypeId, search);
        return list;
    }
    
    public boolean update( Preparation item){
        Connection conn = Conexion.getConnection();
        return preparationDAO.update(conn, item);
    }
    
    public boolean create( Preparation item ) {
        Connection conn = Conexion.getConnection();
        return preparationDAO.create(conn, item);
    }
    
    public boolean delete( Preparation item ) {
        boolean detailDeleted = preparationDetailDAO.deleteByPreparationId(Conexion.getConnection(), item.getId());
        
        boolean itemDeleted = preparationDAO.delete(Conexion.getConnection(), item.getId());

        
        System.out.println("item deleted: "+ itemDeleted);
        System.out.println("detail deleted: "+ detailDeleted);
        return true;
    }
    
    public boolean save( Preparation preparation){
        if( preparation.getId()==0 ){
            create(preparation);
        }else{
            update(preparation);
        }
        boolean detailInserted = preparationDetailDAO.createMultiple(Conexion.getConnection(), preparation.getPreparationDetails());
        return true;
    }
    
    public ArrayList<PreparationDetail> getPreparationDetails(int preparationId)
    {
        Connection conn = Conexion.getConnection();
        ArrayList<PreparationDetail> list = preparationDetailDAO.listDetailOfPreparation(conn, preparationId);
        return list;
    }
}
