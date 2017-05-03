/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

/**
 *
 * @author Perry
 */
public class Validator {
    
    /**
     * Confirms that passed in text is an integer.
     * @param text
     * @return True if an integer, false otherwise.
     */
    public static boolean isPositiveInteger(String text) {
        if (text.isEmpty()) return false;
        for (int i = 0; i < text.length(); i++) {
            if ((int)text.charAt(i) < 48 || (int)text.charAt(i) > 57)
                return false;
        }
        return true;
    }
    
    /**
     * Confirms a passed in integer is within a specified range (inclusive).
     * @param num
     * @param min
     * @param max
     * @return 
     */
    public static boolean isInRange(int num, int min, int max) {
        return (num >= min) && (num <= max);
    }
    
    /**
     * Confirms the length of a passed in String is within a specified range (inclusive).
     * @param text
     * @param min
     * @param max
     * @return 
     */
    public static boolean isInRange(String text, int min, int max) {
        return (text.length() >= min) && (text.length() <= max);
    }
    
    /**
     * Confirms that passed in text is formatted properly as an email.
     * @param text
     * @return 
     */
    public static boolean isEmail(String text) {
        //To be added.
        
        return false;
    }
}
