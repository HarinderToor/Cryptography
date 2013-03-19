/*
 * Brute Force Class
 * Cryptography Assignment 2
 */
package assignment2;

import java.util.Arrays;

/**
 *
 * @author Harinder
 */
public class BruteForce {

    // Two arrays; for character set and potential guesses
    private char[] charSetArray;
    private char[] charGuessArray;

    // Create and fill charsArray on Object creation
    public BruteForce(char[] characterSet, int guessLength) {
        charSetArray = characterSet;
        charGuessArray = new char[guessLength];
        Arrays.fill(charGuessArray, charSetArray[0]);
    }

    // Increment each min value and store it in the charsGuessArray
    public void Guessing() {
        int index = charGuessArray.length - 1;
        while (index >= 0) {
            if (charGuessArray[index] == charSetArray[charSetArray.length - 1]) {
                if (index == 0) {
                    charGuessArray = new char[charGuessArray.length + 1];
                    Arrays.fill(charGuessArray, charSetArray[0]);
                    break;
                } else {
                    charGuessArray[index] = charSetArray[0];
                    index--;
                }
            } else {
                charGuessArray[index] = charSetArray[Arrays.binarySearch(charSetArray, charGuessArray[index]) + 1];
                break;
            }
        }
    }

    @Override
    public String toString() {
        return String.valueOf(charGuessArray);
    }
}