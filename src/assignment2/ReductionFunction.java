/*
 * ReductionFunction Class
 * Cryptography Assignment 2
 */
package assignment2;

import java.math.BigInteger;

/**
 *
 * @author Harinder
 */
public class ReductionFunction {

    public static String alphabetSet = "abcdefghijklmnopqrstuvwxyz";
    public static String begin = "aaaaaa";
    public static String end = "zzzzzz";

    // Base idea taken from lecture 17
    public static String reductionFunction(byte[] hash, int reductionNumber) {
        int[] start = new int[6];
        // Add the beginning string into the start array
        long beginValue = toNumber(begin.toCharArray());
        int index = 5;
        while (beginValue > 0) {
            start[index--] = (int) (beginValue % alphabetSet.length());
            beginValue /= alphabetSet.length();
        }
        //calculate how many strings there are from aaaaaa to zzzzzz        
        long stringLength = toNumber(end.toCharArray()) - toNumber(begin.toCharArray());
        // convert hash to 128bit integer
        BigInteger value = new BigInteger(hash);
        // Add in the reduction number to have different functions
        value = value.add(BigInteger.valueOf(reductionNumber));
        // Mod by the length of the block so we have values from 0 to blockLength
        value = value.mod(BigInteger.valueOf(stringLength));
        // Convert back to long for easier calculations
        long newValue = value.longValue();
        // Do a divide/mod loop to pull out individual letters
        for (int i = 0; i < 6; i++) {
            int offset = (int) (newValue % alphabetSet.length());
            newValue = newValue / alphabetSet.length();
            // Add to start array
            start[5 - i] += offset;
            // Check if we have to "carry" to the next significant bit
            if (start[5 - i] >= alphabetSet.length()) {
                start[5 - i] -= alphabetSet.length();
                start[5 - i - 1]++;
            }
        }
        // Generate the output string
        String output = "";
        for (int i = 0; i < 6; i++) {
            output += alphabetSet.charAt(start[i]);
        }
        return output;
    }

    public static long toNumber(char[] value) {
        long number = 0;
        for (int i = value.length - 1; i >= 0; i--) {
            number *= alphabetSet.length();
            number += alphabetSet.indexOf(value[i]);
        }
        return number;
    }

    public static String reductionFunction(byte[] hash) {
        return reductionFunction(hash, 0);
    }
}