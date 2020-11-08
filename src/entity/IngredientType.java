/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author HP_RYZEN
 */
public class IngredientType {
    
    private int id;
    private String descrip;
    private String abrev;

    public IngredientType() {
    }

    public IngredientType(int id) {
        this.id = id;
    }
    
    public IngredientType(int id, String descrip, String abrev) {
        this.id = id;
        this.descrip = descrip;
        this.abrev = abrev;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getAbrev() {
        return abrev;
    }

    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }

    @Override
    public String toString() {
        return descrip;
    }
}
