/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author HP_RYZEN
 */
public class Food {
    
    private int id;
    private String descrip;
    private FoodType foodType;
    private ArrayList<FactorUnit> factorUnits;
    private HashMap<String, String> nutrients;

    public Food() {
    }

    public Food(int id, String descrip) {
        this.id = id;
        this.descrip = descrip;
    }
    
    
    
    public Food(int id, String descrip, FoodType foodType) {
        this.id = id;
        this.descrip = descrip;
        this.foodType = foodType;
    }

    public Food(String descrip, FoodType foodType) {
        this.descrip = descrip;
        this.foodType = foodType;
    }
    
    public Food(int id) {
        this.id = id;
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

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public ArrayList<FactorUnit> getFactorUnits() {
        return factorUnits;
    }

    public void setFactorUnits(ArrayList<FactorUnit> factorUnits) {
        this.factorUnits = factorUnits;
    }

    public HashMap<String, String> getNutrients() {
        return nutrients;
    }

    public void setNutrients(HashMap<String, String> nutrients) {
        this.nutrients = nutrients;
    }

    @Override
    public String toString() {
        return "Food{" + "id=" + id + ", descrip=" + descrip + ", foodType=" + foodType + ", factorUnits=" + factorUnits + ", nutrients=" + nutrients + '}';
    }
}
