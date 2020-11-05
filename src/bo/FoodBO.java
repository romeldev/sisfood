/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import dao.FoodDAO;
import dao.Conexion;
import dao.FactorUnitDAO;
import dao.NutrientDAO;
import entity.Food;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author HP_RYZEN
 */
public class FoodBO {
    
    private FoodDAO foodDAO = new FoodDAO();
    private FactorUnitDAO factorUnitDAO = new FactorUnitDAO();
    private NutrientDAO nutrientDAO = new NutrientDAO();
    
    public ArrayList<Food> list()
    {
        Connection conn = Conexion.getConnection();
        ArrayList<Food> list = foodDAO.list(conn);
        return list;
    }
    
    public ArrayList<Food> search(String column, String search, int foodTypeId)
    {
        Connection conn = Conexion.getConnection();
        ArrayList<Food> list = foodDAO.search(conn, column, search, foodTypeId);
        return list;
    }
    
    public ArrayList<Food> searchWithNutrients(String column, String search, int foodTypeId)
    {
        Connection conn = Conexion.getConnection();
        ArrayList<Food> list = foodDAO.searchWithNutrients(conn, column, search, foodTypeId);
        return list;
    }
    
    public boolean update( Food item){
        Connection conn = Conexion.getConnection();
        return foodDAO.update(conn, item);
    }
    
    public boolean create( Food item ) {
        Connection conn = Conexion.getConnection();
        return foodDAO.create(conn, item);
    }
    
    public boolean delete( Food item ) {
        Connection conn = Conexion.getConnection(false);
        boolean status = false;
        try {
            if(  factorUnitDAO.deleteByFoodId(conn, item.getId()) ){
                if( nutrientDAO.deleteByFoodId(conn, item.getId()) ){
                    if( foodDAO.delete(conn, item.getId()) ){
                        conn.commit();
                        status = true;
                    }else{ conn.rollback();}
                }else { conn.rollback(); }
            }else{ conn.rollback(); }
            
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(FoodBO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return status;
    }
    

    
    public boolean save(Food food)
    {
        Connection conn = Conexion.getConnection(false);
        boolean status = false;
        try {
            if( food.getId()== 0){
                if( foodDAO.create(conn, food)){
                    food.getFactorUnits().forEach((factorUnit) -> factorUnit.setFood(food));
                    if( factorUnitDAO.createMultiple(conn, food.getFactorUnits())){
                        food.getNutrients().put("food_id", food.getId()+"");
                        if( nutrientDAO.create(conn, food.getNutrients()) ){
                            conn.commit();
                            status = true;
                        }else{ conn.rollback(); }
                    }else{ conn.rollback(); }
                }else{ conn.rollback(); }
            }else{
                if( foodDAO.update(conn, food) ){
                    if( factorUnitDAO.updateMultiple(conn, food.getFactorUnits()) ){
                        if( nutrientDAO.updateByFoodId(conn, food.getNutrients()) ){
                            conn.commit();
                            status = true;
                        }else { conn.rollback(); }
                    }else{ conn.rollback(); }
                }else{ conn.rollback(); }
            }
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(FoodBO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return status;
    }
}
