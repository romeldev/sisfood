/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import dao.DB;
import dao.ProfessionalDAO;
import entity.Professional;
import java.util.ArrayList;
/**
 *
 * @author HP_RYZEN
 */
public class ProfessionalBO {
    
    private ProfessionalDAO professionalDAO = new ProfessionalDAO();
    
    public ArrayList<Professional> list(){
        return professionalDAO.list( DB.conn(), true);
    }
    
    public boolean update( Professional item){
        return professionalDAO.update(DB.conn(), item)==1;
    }
    
    public boolean create( Professional item ) {
        return professionalDAO.create(DB.conn(), item)==1;
    }
    
    public boolean delete( Professional item ) {
        return professionalDAO.delete(DB.conn(), item.getId(), true)==1;
    }
}
