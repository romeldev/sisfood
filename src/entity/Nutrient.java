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
public class Nutrient {
    
    public static String[] Columns()
    {
        ArrayList<HashMap<String, String>> items = Nutrient.Items();
        String[] columns = new String[items.size()+3];
        columns[0] = "food_id";
        columns[1] = "net_weight";
        columns[2] = "grams";
        int index = 3;
        for (HashMap<String, String> item : items){
            columns[index] = item.keySet().toArray()[0].toString();
            index++;
        }
        return columns;
    }
    
    public static ArrayList<HashMap<String, String>> Items()
    {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        HashMap<String, String> item = null;
        item = new HashMap<>(); item.put("energy_kcal", "Energia (kcal)"); list.add(item);
        item = new HashMap<>(); item.put("water_g", "Agua (g)"); list.add(item);
        item = new HashMap<>(); item.put("proteinins_g", "Proteninas (g)"); list.add(item);
        item = new HashMap<>(); item.put("total_fat_g", "Grasa Total (g)"); list.add(item);
        item = new HashMap<>(); item.put("total_carbohydrates_g", "Carbohidratos (g)"); list.add(item);
        item = new HashMap<>(); item.put("raw_fiber_g", "Fibra Cruda (g)"); list.add(item);
        item = new HashMap<>(); item.put("dietary_fiber_g", "Fibra Dietetica (g)"); list.add(item);
        item = new HashMap<>(); item.put("calcium_mg", "Calcio (mg)"); list.add(item);
        item = new HashMap<>(); item.put("match_mg", "Fosforo (mg)"); list.add(item);
        item = new HashMap<>(); item.put("zinc_mg", "Zinc (mg)"); list.add(item);
        item = new HashMap<>(); item.put("iron_mg", "Hierro (mg)"); list.add(item);
        item = new HashMap<>(); item.put("carotene_ug", "Carotano (ug)"); list.add(item);
        item = new HashMap<>(); item.put("retinol_ug", "Retinol (ug)"); list.add(item);
        item = new HashMap<>(); item.put("vitamin_a_ug", "Vitamina A (ug)"); list.add(item);
        item = new HashMap<>(); item.put("thiamine_mg", "Tiamina (mg)"); list.add(item);
        item = new HashMap<>(); item.put("reboflavin_mg", "Reboflavina (mg)"); list.add(item);
        item = new HashMap<>(); item.put("niacin_mg", "Niacina (mg)"); list.add(item);
        item = new HashMap<>(); item.put("vitamin_c_mg", "Vitamina C (mg)"); list.add(item);
        item = new HashMap<>(); item.put("asct_g", "Asct (g)"); list.add(item);
        item = new HashMap<>(); item.put("sodium_mg", "Sodio (mg)"); list.add(item);
        item = new HashMap<>(); item.put("cholesterol_mg", "Colesterol (mg)"); list.add(item);
        item = new HashMap<>(); item.put("potassium_mg", "Potasio (mg)"); list.add(item);
        item = new HashMap<>(); item.put("omega3", "Omega3"); list.add(item);
        item = new HashMap<>(); item.put("omega6", "Omega6"); list.add(item);
        item = new HashMap<>(); item.put("saturated_fat_g", "Grasa Saturada (g)"); list.add(item);
        item = new HashMap<>(); item.put("trans_fat_g", "Grasa Trans (g)"); list.add(item);
        item = new HashMap<>(); item.put("magnetium_mg", "Magnecio (mg)"); list.add(item);
        item = new HashMap<>(); item.put("iodine_mg", "Yodo (mg)"); list.add(item);
        item = new HashMap<>(); item.put("selenium_ug", "Selenio (ug)"); list.add(item);
        item = new HashMap<>(); item.put("folic_acid_ug", "Acido Folico (ug)"); list.add(item);
        item = new HashMap<>(); item.put("vitamin_d_ug", "Vitamina D (ug)"); list.add(item);
        item = new HashMap<>(); item.put("vitamin_b1_mg", "Vitamina B1 (mg)"); list.add(item);
        item = new HashMap<>(); item.put("vitamin_b2_mg", "Vitamina B2 (mg)"); list.add(item);
        item = new HashMap<>(); item.put("b12_vitamin_mg", "Vitamina B12 (mg)"); list.add(item);
        item = new HashMap<>(); item.put("copper_ug", "Cobre (ug)"); list.add(item);
        item = new HashMap<>(); item.put("mercury_ug", "Mercurio (ug)"); list.add(item);
        item = new HashMap<>(); item.put("lead_ug", "Plomo (ug)"); list.add(item);
        item = new HashMap<>(); item.put("aluminum_mg", "Aluminio (mg)"); list.add(item);
        item = new HashMap<>(); item.put("cadmium_ug", "Cadmio (ug)"); list.add(item);
        item = new HashMap<>(); item.put("arsenic_ug", "Arsenico (ug)"); list.add(item);
        item = new HashMap<>(); item.put("dha_mg", "Dha (mg)"); list.add(item);
        item = new HashMap<>(); item.put("ara_mg", "Ara (mg)"); list.add(item);
        return list;
    }
    
    public static void main(String[] args) {
        for (String column : Nutrient.Columns()) {
            System.out.println(column);
        }
    }
}
