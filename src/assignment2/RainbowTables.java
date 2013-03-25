/*
 * RainbowTables Class
 * Cryptography Assignment 2
 */
package assignment2;

import java.security.*;
import java.util.*;

/**
 *
 * @author Harinder
 */
public class RainbowTables {

    // The Rainbow Table is stored in a HashMap
    private HashMap RainbowTable;
    private int chainLength;
    private int chainCount;
    private String startingPlaintext;
    private Random randomGenerator;
    Utility tSpace;

    public RainbowTables(String startingPlaintext) {
        RainbowTable = new HashMap();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA1");
            String tempPlaintext = startingPlaintext;
            // Generate the Rainbow Table with 10000 chains
            for (int i = 0; i < 10000; i++) {
                // Each chain contain 1000 pairs of Plaintext and Hash
                for (int j = 0; j < 1000; j++) {
                    algorithm.reset();
                    algorithm.update((tempPlaintext + i).getBytes());
                    byte msgDigest[] = algorithm.digest();
                    String value = ReductionFunction.reductionFunction(msgDigest);

                    if (value.charAt(0) > 'z' || value.charAt(0) < 'a') {
                        throw new RuntimeException();
                    }
                    if (j == 999) {
                        // Add first plaintext of chain along with the last hash
                        RainbowTable.put((tempPlaintext), msgDigest);
                    }
                }
                // Generate a random length for the new plaintext
                int plaintextLength = 6;

                // Generate a new starting plaintext for the next chain
                tempPlaintext = Utility.generateRandomPlaintext(plaintextLength);
            }
        } catch (NoSuchAlgorithmException e) {
        }
    }

    public HashMap getRainbowTable() {
        return RainbowTable;
    }

    public String getPlaintext(String hash) throws NoSuchAlgorithmException {
        // Store unique plaintexts
        HashSet visitedPlaintexts = new HashSet();
        visitedPlaintexts.add(hash);

        byte msgDigest[] = null;
        MessageDigest algorithm = MessageDigest.getInstance("SHA1");
        boolean found = false;

        Set set = RainbowTable.entrySet();
        // Get an iterator
        Iterator i = set.iterator();

        String foundValue = null;
        // Store hash in a new variable to avoid it getting modified
        String newHash = hash;
        int count = 0;
        while (!found) {
            while (i.hasNext()) {
                Map.Entry chain = (Map.Entry) i.next();
                // Check if the hash corresponds to on the last hashes of each chain
                if (chain.getValue().equals(newHash)) {
                    // Store the first plaintext of the chain
                    String value = (String) chain.getKey();
                    // Using the reduction function to find the right plaintext from the chain
                    for (int j = 0; j < chainLength; j++) {
                        algorithm.reset();
                        algorithm.update(value.getBytes());
                        msgDigest = algorithm.digest();
                        //System.out.println(getHex(msgDigest) + "--------------->"+value);
                        if (Utility.getHex(msgDigest).equals(hash)) {
                            found = true;
                            foundValue = value;
                            break;
                        }
                        value = ReductionFunction.reductionFunction(msgDigest, count);
                    }
                }
            }
            // Hash wasn't found among last hashes from the chains
            if (!found) {
                // Reduce the hash
                if (count >= chainLength) {
                    count = 0;
                }
                String reducedHash = ReductionFunction.reductionFunction(newHash.getBytes(), count);
                algorithm.reset();
                algorithm.update(reducedHash.getBytes());
                msgDigest = algorithm.digest();
                // Generate a new hash
                newHash = Utility.getHex(msgDigest);
                int count2 = count;
                // Check if the new hash is not already stored in the HashSet
                while (!visitedPlaintexts.add(newHash)) {
                    reducedHash = ReductionFunction.reductionFunction(newHash.getBytes(), count2);
                    algorithm.reset();
                    algorithm.update(reducedHash.getBytes());
                    msgDigest = algorithm.digest();

                    newHash = Utility.getHex(msgDigest);
                    count2++;
                    if (count2 >= chainLength) {
                        count2 = 0;
                    }
                }
                // Reset the iterator
                set = RainbowTable.entrySet();
                i = set.iterator();
            }
            count++;
        }
        return foundValue;
    }

    public void generateRainbowTable() throws NoSuchAlgorithmException {
        byte msgDigest[] = null;
        MessageDigest algorithm = MessageDigest.getInstance("SHA1");
        String value;
        int blockCount = 1;

        for (int i = 0; i < chainCount; i++) {
            // Store startingPlaintext in another variable to avoid it getting modified
            value = startingPlaintext;
            for (int j = 0; j < chainLength; j++) {
                algorithm.reset();
                algorithm.update(value.getBytes());
                msgDigest = algorithm.digest();
                value = ReductionFunction.reductionFunction(msgDigest, j);
            }
            // Add first plaintext of chain along with the last hash
            RainbowTable.put(startingPlaintext, Utility.getHex(msgDigest));
            int randomIndex = Utility.generateRandomInteger(1, Utility.BLOCK_SIZE, randomGenerator);

            startingPlaintext = tSpace.getText(randomIndex);
            // Keep generating random plaintexts until it finds one that haven't been used
            while (RainbowTable.containsKey(startingPlaintext)) {
                randomIndex = Utility.generateRandomInteger(1, Utility.BLOCK_SIZE, randomGenerator);
            }
            blockCount++;
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {

        // Create a new rainbow table with "a" as starting plaintext
        RainbowTables rt = new RainbowTables("a");

        long start = 0, end = 0;
        try {
            start = System.currentTimeMillis();
            rt.generateRainbowTable();
            end = System.currentTimeMillis();

        } catch (NoSuchAlgorithmException e) {
        }
        HashMap RainbowTable = rt.getRainbowTable();
        Set set = RainbowTable.entrySet();
        // Get an iterator
        Iterator i = set.iterator();
        // Display the Rainbow Table
        while (i.hasNext()) {
            Map.Entry chain = (Map.Entry) i.next();
            // Display first word of the chain
            System.out.print(chain.getKey() + ": ");
            byte[] msgDigest = (byte[]) chain.getValue();
            // Display the chain's last hash in Hexadecimal
            System.out.println(Utility.getHex(msgDigest));
        }
        System.out.println("Rainbow table length is: " + RainbowTable.size());
        // First SHA-1 code from .doc
        // Needs changing from hard coded
        System.out.println("Searching for SHA-1: " + "c2543fff3bfa6f144c2f06a7de6cd10c0b650cae");
        // Get plaintext from Hash
        String plaintext = rt.getPlaintext("c2543fff3bfa6f144c2f06a7de6cd10c0b650cae");
        System.out.println("Found the original plaintext: " + plaintext);
    }
}