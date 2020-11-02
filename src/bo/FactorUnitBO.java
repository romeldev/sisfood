/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import common.Helper;
import dao.FactorUnitDAO;
import dao.Conexion;
import entity.FactorUnit;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author HP_RYZEN
 */
public class FactorUnitBO {
    
    private FactorUnitDAO factorUnitDAO = new FactorUnitDAO();
    
    public ArrayList<FactorUnit> listByFoodId( int foodId )
    {
        Connection conn = Conexion.getConnection();
        ArrayList<FactorUnit> list = Helper.castList(factorUnitDAO.listByFoodId(conn, foodId));
        return list;
    }
}
