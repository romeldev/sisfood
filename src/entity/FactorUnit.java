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
public class FactorUnit {
    
    private int id;
    private Food food;
    private UnitType unitType;
    private String descrip;
    private Double factor;

    public FactorUnit() {
    }

    public FactorUnit(int id, Food food, UnitType unitType, String descrip, Double factor) {
        this.id = id;
        this.food = food;
        this.unitType = unitType;
        this.descrip = descrip;
        this.factor = factor;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public Double getFactor() {
        return factor;
    }

    public void setFactor(Double factor) {
        this.factor = factor;
    }

    @Override
    public String toString() {
        return "FactorUnit{" + "id=" + id + ", food=" + food.getDescrip() + ", unitType=" + unitType.getDescrip() + ", descrip=" + descrip + ", factor=" + factor + '}';
    }
}
