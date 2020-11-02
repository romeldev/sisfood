/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author HP_RYZEN
 */
public class MyLog {
    
    private final static Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public static void setup() {
        LogManager.getLogManager().reset();
        logr.setLevel(Level.ALL);
        
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.SEVERE);
        logr.addHandler(ch);
        
        try {
            boolean append = true;
            FileHandler fh = new FileHandler("myLooger.log", append);
            fh.setLevel(Level.FINE);
            logr.addHandler(fh);
            
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  
        
        } catch (IOException | SecurityException e) {
            logr.log(Level.SEVERE, "File logger not working.", e);
        }
    }
    
    public static void main(String[] args) {
        MyLog.setup();
        logr.fine("Hola mundo");
    }
}
