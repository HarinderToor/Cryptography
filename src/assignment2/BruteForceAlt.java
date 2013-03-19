/*
 * Alternative Brute Force Class
 * Cryptography Assignment 2
 */
package assignment2;

import java.util.Arrays;

/**
 *
 * @author Harinder
 */
public class BruteForceAlt {

    final int startChar;
    final int endChar;
    final int stringLength;
    private final int[] charsArray;

    // Create and fill charsArray on Object creation
    public BruteForceAlt(char min, char max, int len) {
        this.startChar = min;
        this.endChar = max;
        this.stringLength = len;

        charsArray = new int[stringLength + 1];
        Arrays.fill(charsArray, 1, charsArray.length, min);
    }

    // Increment each min value to store it in the charsArray
    private void increment() {
        for (int i = charsArray.length - 1; i >= 0; i--) {
            if (charsArray[i] < endChar) {
                charsArray[i]++;
                return;
            }
            charsArray[i] = startChar;
        }
    }

    // Print out all charsArray values
    private void print() {
        for (int i = 1; i < charsArray.length; i++) {
            System.out.print((char) charsArray[i]);
        }
        System.out.println();
    }

    public void run() {
        while (charsArray[0] == 0) {
            print();
            increment();
        }
    }

    public static void main(String[] args) {
        new BruteForceAlt('a', 'z', 6).run();
    }
}