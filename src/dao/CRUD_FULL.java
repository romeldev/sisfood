/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Company;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author HP_RYZEN
 */
public interface CRUD_FULL <T> {
    
    public ArrayList<T> list(Connection conn);
    public T read(Connection conn, Integer id);
    public boolean create(Connection conn, T entity);
    public boolean update(Connection conn, T entity);
    public boolean delete(Connection conn, Integer id);
}
