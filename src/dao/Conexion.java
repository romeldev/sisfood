/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author HP_RYZEN
 */
public class Conexion {
    
    private static Connection conn = null;
    private static String username = "root";
    private static String password = "";
    private static String host = "localhost";
    private static String port = "3306";
//    private static String database = "hospital";
    private static String database = "sisfood";
    
    private static String url = "";
    
    
    public static Connection getConnection()
    {
        return Conexion.getConnection(true);
    }
    
    public static Connection getConnection(boolean commit)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            url = "jdbc:mysql://"+host+"/"+database+"?serverTimezone=America/Lima&allowMultiQueries=true";
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(commit);
//            if( conn != null){
//                System.out.println("Connection success!");
//            }else{
//                System.err.println("Connection fail!");
//            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Conection error "+e.getMessage());
        }
        return conn;
    }
    
    public void close()
    {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error conneciton close: "+e.getMessage());
        }
    }
    
    
    public static void main(String[] args) {
        Conexion cox = new Conexion();
        cox.close();
        Connection conn = Conexion.getConnection();
    }
}
