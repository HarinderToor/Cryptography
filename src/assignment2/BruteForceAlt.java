/*
 * Brute Force Alternative Class
 * Cryptography Assignment 2
 * @author Harinder
 */
package assignment2;

import java.util.Arrays;

public class BruteForceAlt {

    final int startChar;
    final int endChar;
    final int stringLength;
    private final int[] chars;

    public BruteForceAlt(char min, char max, int len) {
        this.startChar = min;
        this.endChar = max;
        this.stringLength = len;

        chars = new int[stringLength + 1];
        Arrays.fill(chars, 1, chars.length, min);
    }

    private void increment() {
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] < endChar) {
                chars[i]++;
                return;
            }
            chars[i] = startChar;
        }
    }

    private void print() {
        for (int i = 1; i < chars.length; i++) {
            System.out.print((char) chars[i]);
        }
        System.out.println();
    }

    public void run() {
        while (chars[0] == 0) {
            print();
            increment();
        }
    }

    public static void main(String[] args) {
        new BruteForceAlt('a', 'z', 6).run();
    }
}