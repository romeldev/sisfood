/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

/**
 *
 * @author HP_RYZEN
 */
public class Helper {
    
    public static <T> ArrayList<T> castList(ArrayList list) {
        return list;
    }
    
    public static ImageIcon icon( String name)
    {
        Helper helper = new Helper();
        return helper.getIcon(name);
    }
    
    public ImageIcon getIcon( String name )
    {
        return new ImageIcon( getClass().getResource("/resources/icons/"+name+".png"));
    }
    
    public static void TextFieldDecimal( JTextField input)
    {
        input.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char car = e.getKeyChar();
                    
                    if (((car < '0') || (car > '9')) && car!='.') {
                        e.consume();
                    }
                    if(car==KeyEvent.VK_DECIMAL || car==KeyEvent.VK_PERIOD){
                        String field = input.getText(); //get the string in textField
                        int index = field.indexOf("."); //find the index of dot(.) or decimal point
                        if(!(index==-1)){ //if there is any
                            e.consume(); //consume the keytyped. this prevents the keytyped from appearing on the textfield;
                        }
                    }
                    
                }
                @Override
                public void keyPressed(KeyEvent e) {
                }
                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
    }
    
    public static int CountCharacter(String string, char find) {  
        int count = 0;
        for(int i = 0; i < string.length(); i++) {    
            if(string.charAt(i)==find){
                count++;    
            }
        }
        return count;
    }
    
    
    public static String JoinArray(String delimiter, Object[] elements){
        String text = "";
        for (Object element : elements) text += element.toString()+",";
        if( text.length() > 1) text = text.substring(0, text.length()-1);
        return text;
    }
}
