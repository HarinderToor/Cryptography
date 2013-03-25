/*
 * Main Class
 * Cryptography Assignment 2
 */
package assignment2;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 *
 * @author Harinder
 */
public class Main {

    final String SALT = "uwe.ac.uk";
    final char[] characterSet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    HashMap userDetails = new HashMap();
    long startTime, endTime;

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Main P = new Main();
        Scanner scan = new Scanner(System.in);
        String menuSelection, user = null, password = null, saltString = null;
        char mChoice;

        P.inputMenu();
        menuSelection = scan.nextLine().toUpperCase();
        mChoice = menuSelection.charAt(0);

        while (mChoice != 'X') {

            switch (mChoice) {
                case 'N':
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
                    P.FindPassword(user, password, saltString);
                    break;

                case 'A':
                    // Find Password
                    P.FindAltPassword(user, password);
                    break;
                case 'R':
                    // Use Rainbow Table
                    // P.FindAltPassword(user, password);
                    break;
                case 'X':
                    // Exit
                    System.exit(0);

                default:
                    System.out.println("\nInvalid input choice. Please try again\n");

            };
            //P.inputMenu();
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
        password = Utility.SHA1(password);

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
            if (userDetails.get(existingUser) == Utility.SHA1(password)) {
            }
        }
    }

    public void ViewAllUsers(String user, String password) {
        Iterator iterate = userDetails.keySet().iterator();
        while (iterate.hasNext()) {

            String key = iterate.next().toString();
            Object keys = userDetails.get(key);
            System.out.println(key + "\n" + keys);
        }
    }

    public void FindAltPassword(String user, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a SHA-1 code: ");
        password = scan.nextLine();
        char[] characterSet = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    }

    public void FindPassword(String user, String password, String saltString) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a SHA-1 code: ");
        password = scan.nextLine();

//        System.out.println("Please enter Salt String");
//        saltString = scan.nextLine();

        BruteForce bruteForce = new BruteForce(characterSet, 1);
        startTimer();

        // Start the attempt with an empty string
        String hashAttempt = "";
        String attempt = bruteForce.toString();
        
        // Recover the password and record the time taken
        while (true) {
            if (hashAttempt.equals(password)) {
                System.out.println("Your password has been found!\n It is: " + attempt);
                endTimer();
                calculateTime();
                break;
            }
            attempt = bruteForce.toString();
            hashAttempt = Utility.SHA1(SALT + attempt);
            //System.out.println(hashAttempt);
            bruteForce.Guessing();
        }
    }

    private void startTimer() {
        startTime = System.currentTimeMillis();
    }

    private void endTimer() {
        endTime = System.currentTimeMillis();
    }

    private void calculateTime() {
        long elapsed = (endTime - startTime);
        if (elapsed < 1000) {
            System.out.println("Time taken to solve was: " + elapsed / 1000 + " milliseconds.");
        } else {
            System.out.println("Time taken to solve was: " + elapsed / 1000 + " seconds.");
        }
    }

    private void inputMenu() {
        System.out.println("\n INPUT MENU\n");
        System.out.println("R\t Register New User");
        System.out.println("L\t Login\n");
        System.out.println("V\t View all users\n");
        System.out.println("F\t Find a Password\n");
        System.out.println("Fa\t Find a Password with a different method\n");
        System.out.println("R\t Find a Password using Rainbow Tables");        
        System.out.println("X\t Exit\n");
        System.out.println("Enter menu choice, X to exit: ");
    }
}