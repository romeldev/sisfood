/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;

/**
 *
 * @author HP_RYZEN
 */
public class Preparation {
    
    private int id;
    
    private String descrip="";
    
    private PreparationType preparationType;
    
    private Company company;
    
    private ArrayList<PreparationDetail> preparationDetails;

    public Preparation() {
    }

    
    public Preparation(int id) {
        this.id = id;
    }

    public Preparation(int id, String descrip, PreparationType preparationType, Company company) {
        this.id = id;
        this.descrip = descrip;
        this.preparationType = preparationType;
        this.company = company;
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

    public PreparationType getPreparationType() {
        return preparationType;
    }

    public void setPreparationType(PreparationType preparationType) {
        this.preparationType = preparationType;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ArrayList<PreparationDetail> getPreparationDetails() {
        return preparationDetails;
    }

    public void setPreparationDetails(ArrayList<PreparationDetail> preparationDetails) {
        this.preparationDetails = preparationDetails;
    }
    

    @Override
    public String toString() {
        return "Preparation{" + "id=" + id + ", descrip=" + descrip + ", preparationType=" + preparationType + ", company=" + company + '}';
    }
}
