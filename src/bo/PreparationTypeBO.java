/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import dao.PreparationTypeDAO;
import dao.DB;
import entity.PreparationType;
import java.util.ArrayList;

/**
 *
 * @author HP_RYZEN
 */
public class PreparationTypeBO {
    
    private PreparationTypeDAO preparationTypeDAO = new PreparationTypeDAO();
    
    public ArrayList<PreparationType> list(){
        return preparationTypeDAO.list(DB.conn(), true);
    }
    
    public boolean update( PreparationType item){
        return preparationTypeDAO.update(DB.conn(), item)==1;
    }
    
    public boolean create( PreparationType item ) {
        return preparationTypeDAO.create(DB.conn(), item)==1;
    }
    
    public boolean delete( PreparationType item ) {
        System.out.println(item.getId());
        return preparationTypeDAO.delete(DB.conn(), item.getId(), true)==1;
    }
}
