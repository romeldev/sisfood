/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author HP_RYZEN
 */
public class Validator {
    
    public static boolean isRequired(String value)
    {
        return value.trim().length()>0;
    }
    
    public static boolean isInteger(String value)
    {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    public static boolean isNumeric(String value)
    {
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
  
    // greater than
    public static boolean gt(String value, String flag)
    {
        try {
            Double valueNum = Double.parseDouble(value);
            Double flagNum = Double.parseDouble(flag);
            return valueNum > flagNum;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isDate(String value, String format)
    {
        format = format!=null? "dd-MM-yyyy HH:mm:ss:ms": format;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(value.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
}
