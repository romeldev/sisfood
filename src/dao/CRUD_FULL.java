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
    
    public ArrayList<T> list(Connection conn, boolean soft);
    public T read(Connection conn, Integer id, boolean soft);
    public Integer create(Connection conn, T entity);
    public Integer update(Connection conn, T entity);
    public Integer delete(Connection conn, Integer id, boolean soft);
}
