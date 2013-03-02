/*
 * Main Class
 * Cryptography Assignment 2
 * @author Harinder
 */
package assignment2;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author Harinder
 */
public class Main {

  HashMap userDetails = new HashMap();
  long startTime, timeElapsed;

  public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    Main P = new Main();
    Scanner scan = new Scanner(System.in);
    String menuSelection, user = null, password = null;
    char mChoice;

    P.inputMenu();
    menuSelection = scan.nextLine().toUpperCase();
    mChoice = menuSelection.charAt(0);

    while (mChoice != 'X') {

      switch (mChoice) {
        case 'R':
          // Register new user
          P.Register(user, password);
          break;

        case 'L':
          // User Login
          P.Login(user, password);
          break;

        case 'V':
          // View all users
          P.ViewAllUsers(user, password);
          break;

        case 'F':
          // Find Password
          P.FindPassword(user, password);
          break;

        case 'X':
          // Exit
          System.exit(0);

        default:
          System.out.println("\nInvalid input choice. Please try again\n");

      };
      P.inputMenu();
      menuSelection = scan.nextLine().toUpperCase();
      mChoice = menuSelection.charAt(0);
    }
  }

  public void Register(String user, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {

    Scanner scan = new Scanner(System.in);
    System.out.println("Please enter a login ID: ");
    user = scan.nextLine();

    System.out.println("Please enter a password: ");
    password = scan.nextLine();
    password = SHA1(password);

    System.out.println(password);

    Object newUser = userDetails.get(user);
    if (newUser == null) {
      userDetails.put(user, password);
      System.out.println("User created!");
    } else {
      System.out.println("Error: Username already taken! Please Try Again.");
    }
  }

  public void Login(String user, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    Scanner scan = new Scanner(System.in);
    System.out.println("Please enter your login ID: ");
    user = scan.nextLine();

    System.out.println("Please enter your password: ");
    password = scan.nextLine();

    Object existingUser = userDetails.get(user);
    if (existingUser != null) {
      if (userDetails.get(existingUser) == SHA1(password)) {
      }
    }
  }

  public void ViewAllUsers(String user, String password) {
    Iterator it = userDetails.keySet().iterator();
    while (it.hasNext()) {

      String key = it.next().toString();
      Object keys = userDetails.get(key);
      System.out.println(key + "\n" + keys);
    }
  }

  public void FindPassword(String user, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {

    Scanner scan = new Scanner(System.in);
    System.out.println("Please enter a SHA-1 code: ");
    password = scan.nextLine();

    char[] characterSet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    BruteForce bruteForce = new BruteForce(characterSet, 1);

    startTimer();

    // Start the attempt with an empty string but with all available characters
    String hashAttempt = "";
    String attempt = bruteForce.toString();

    // Recover the password and record the time taken
    while (true) {
      if (hashAttempt.equals(password)) {
        System.out.println("Your password has been found! It is: " + attempt);
        calculateTime();
        break;
      }
      attempt = bruteForce.toString();
      hashAttempt = SHA1(attempt);
      bruteForce.Guessing();
    }
  }

  private static String convertToHex(byte[] data) {
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < data.length; i++) {
      int halfbyte = (data[i] >>> 4) & 0x0F;
      int two_halfs = 0;
      do {
        if ((0 <= halfbyte) && (halfbyte <= 9)) {
          buf.append((char) ('0' + halfbyte));
        } else {
          buf.append((char) ('a' + (halfbyte - 10)));
        }
        halfbyte = data[i] & 0x0F;
      } while (two_halfs++ < 1);
    }
    return buf.toString();
  }

  public static String SHA1(String text)
          throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest md;
    md = MessageDigest.getInstance("SHA-1");
    byte[] sha1hash = new byte[40];
    md.update(text.getBytes("iso-8859-1"), 0, text.length());
    sha1hash = md.digest();
    return convertToHex(sha1hash);
  }

  public void startTimer() {
    startTime = System.currentTimeMillis();
  }

  public void calculateTime() {
    timeElapsed = System.currentTimeMillis() - startTime;
    if (timeElapsed == 0) {
      System.out.println("Time taken to solve was: " + timeElapsed + " milliseconds.");
    } else {
      System.out.println("Time taken to solve was: " + timeElapsed/1000 + " seconds.");
    }
  }

  private void inputMenu() {
    System.out.println("\n INPUT MENU\n");
    System.out.println("R\t Register New User");
    System.out.println("L\t Login\n");
    System.out.println("V\t View all users\n");
    System.out.println("F\t Find a Password\n");
    System.out.println("X\t Exit\n");
    System.out.println("Enter menu choice, X to exit: ");
  }
}