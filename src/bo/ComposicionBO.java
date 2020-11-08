/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import dao.CompositionDAO;
import dao.DB;
import entity.Composition;
import java.util.ArrayList;
/**
 *
 * @author HP_RYZEN
 */
public class ComposicionBO {
    
    private CompositionDAO composicionDAO = new CompositionDAO();
    
    public ArrayList<Composition> list(){
        return composicionDAO.list(DB.conn(), true);
    }
    
    public boolean update( Composition item){
        return composicionDAO.update(DB.conn(), item)==1;
    }
    
    public boolean create( Composition item ) {
        return composicionDAO.create(DB.conn(), item)==1;
    }
    
    public boolean delete( Composition item ) {
        return composicionDAO.delete(DB.conn(), item.getId(), true)==1;
    }

}
