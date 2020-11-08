/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import dao.CurrencyTypeDAO;
import dao.DB;
import entity.CurrencyType;
import java.util.ArrayList;
/**
 *
 * @author HP_RYZEN
 */
public class CurrencyTypeBO {
    
    private CurrencyTypeDAO currencyTypeDAO = new CurrencyTypeDAO();
    
    public ArrayList<CurrencyType> list()
    {
        return currencyTypeDAO.list(DB.conn(), true);
    }
    
    public boolean update( CurrencyType item){
        return currencyTypeDAO.update(DB.conn(), item)==1;
    }
    
    public boolean create( CurrencyType item ) {
        return currencyTypeDAO.create(DB.conn(), item)==1;
    }
    
    public boolean delete( CurrencyType item ) {
        return currencyTypeDAO.delete(DB.conn(), item.getId(), true)==1;
    }
}
