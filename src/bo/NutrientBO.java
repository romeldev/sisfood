/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import dao.NutrientDAO;
import dao.Conexion;
import java.sql.Connection;
import java.util.HashMap;

/**
 *
 * @author HP_RYZEN
 */
public class NutrientBO {
    
    private NutrientDAO nutrientDAO = new NutrientDAO();
    
    public HashMap<String, String> findByFoodId( int foodId )
    {
        Connection conn = Conexion.getConnection();
        HashMap<String, String> item = (HashMap<String, String> ) nutrientDAO.findByFoodId(conn, foodId);
        return item;
    }
}
