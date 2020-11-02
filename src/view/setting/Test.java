/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.setting;

import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author HP_RYZEN
 */
public class Test {
    public static void main(String[] args) {
        
        JSONObject object = new JSONObject();
        object.put("name", "romel");
        object.put("code", 123);
        System.out.println(object.keySet());
        
        JSONArray array = new JSONArray();
        array.add(object);
        
        System.out.println(array.get(0));
//        JOptionPane.showMessageDialog(null, "Mensaje de error" );
//        JOptionPane.showMessageDialog(null, "Mensaje de error", "Alerta", JOptionPane.ERROR_MESSAGE );
//        JOptionPane.showMessageDialog(null, "Mensaje de informacion", "Alerta", JOptionPane.INFORMATION_MESSAGE);
//        JOptionPane.showMessageDialog(null, "Mensaje de alerta", "Alerta", JOptionPane.WARNING_MESSAGE);
//        JOptionPane.showMessageDialog(null, "Mensaje de pregunta", "Alerta", JOptionPane.QUESTION_MESSAGE);
//        JOptionPane.showMessageDialog(null, "Mensaje de plano", "Alerta", JOptionPane.PLAIN_MESSAGE);
    }
}
