/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author user
 */
public class Query {
    
    public static void main(String[] args) {
        JSONArray data = Query.execute("select id, descrip from foods");
        
        
        for (Object object : data) {
            System.out.println(object);
        }
        
//        System.out.println(data);
    }
    
    public static JSONArray execute( String query ){
        Connection conn = Conexion.getConnection();
        
        JSONArray data = new JSONArray();
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData meta;
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            
            meta = rs.getMetaData();
            int columCount = meta.getColumnCount();
            
            while (rs.next()) {
                JSONObject item = new JSONObject();
                for (int i = 1; i <= columCount; i++ ) {
                    String columName = meta.getColumnName(i);
                    item.put(columName, rs.getString(columName));
                    data.add(item);
                }
            }
            
//            rs.close();
//            ps.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        return data;
    }
            
            
}
