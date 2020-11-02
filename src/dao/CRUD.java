/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author HP_RYZEN
 */
public interface CRUD {
    public ArrayList<Object> list(Connection conn);
    public Object read(Connection conn, Object object);
    public boolean create(Connection conn, Object object);
    public boolean update(Connection conn, Object object);
    public boolean delete(Connection conn, Object object);
}
