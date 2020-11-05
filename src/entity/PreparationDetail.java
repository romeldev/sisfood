/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author user
 */
public class PreparationDetail {
    
    private int id;
    private Preparation preparation;
    private Food food;
    private FactorUnit factorUnit;
    private double amount;

    public PreparationDetail() {
    }

    public PreparationDetail(int id) {
        this.id = id;
    }

    public PreparationDetail(int id, Preparation preparation, Food food, FactorUnit factorUnit, double amount) {
        this.id = id;
        this.preparation = preparation;
        this.food = food;
        this.factorUnit = factorUnit;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Preparation getPreparation() {
        return preparation;
    }

    public void setPreparation(Preparation preparation) {
        this.preparation = preparation;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public FactorUnit getFactorUnit() {
        return factorUnit;
    }

    public void setFactorUnit(FactorUnit factorUnit) {
        this.factorUnit = factorUnit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PreparationDetail{" + "id=" + id + ", preparation=" + preparation + ", food=" + food + ", factorUnit=" + factorUnit + ", amount=" + amount + '}';
    }
}
