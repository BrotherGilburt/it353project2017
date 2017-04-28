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
}
