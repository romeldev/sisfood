/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import dao.IngredientTypeDAO;
import dao.DB;
import entity.IngredientType;
import java.util.ArrayList;

/**
 *
 * @author HP_RYZEN
 */
public class IngredientTypeBO {
    
    private IngredientTypeDAO foodTypeDAO = new IngredientTypeDAO();
    
    public ArrayList<IngredientType> list(){
        return foodTypeDAO.list(DB.conn(), true);
    }
    
    public boolean update( IngredientType item){
        return foodTypeDAO.update(DB.conn(), item)==1;
    }
    
    public boolean create( IngredientType item ) {
        return foodTypeDAO.create(DB.conn(), item)==1;
    }
    
    public boolean delete( IngredientType item ) {
        return foodTypeDAO.delete(DB.conn(), item.getId(), true)==1;
    }
}
