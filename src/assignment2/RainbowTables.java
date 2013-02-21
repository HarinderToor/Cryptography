/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

/**
 *
 * @author Harinder
 */
public class RainbowTables {

    int Hash;
    String Password;
    
    private static int min = Integer.parseInt("100", 36);
    private static int range = Integer.parseInt("zzz", 36) - min;

    public static String shortHash(String sha) {
        return Integer.toString(min + (sha.hashCode() & 0x7FFFFFFF) % range, 36);
    }
}
