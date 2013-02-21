/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

/* 
 * BruteForce Class
 * Cryptography Assignment 2
 * @author Harinder
 */
import java.util.Arrays;

public class BruteForce {

  private char[] charSet;
  private char[] charGuess;

  public BruteForce(char[] characterSet, int guessLength) {
    charSet = characterSet;
    charGuess = new char[guessLength];
    Arrays.fill(charGuess, charSet[0]);
  }

  public void Guessing() {
    int index = charGuess.length - 1;
    while (index >= 0) {
      if (charGuess[index] == charSet[charSet.length - 1]) {
        if (index == 0) {
          charGuess = new char[charGuess.length + 1];
          Arrays.fill(charGuess, charSet[0]);
          break;
        } else {
          charGuess[index] = charSet[0];
          index--;
        }
      } else {
        charGuess[index] = charSet[Arrays.binarySearch(charSet, charGuess[index]) + 1];
        break;
      }
    }
  }

    @Override
  public String toString() {
    return String.valueOf(charGuess);
  }
}