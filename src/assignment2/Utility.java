/*
 * Encrypt and Utility Helper Class
 * Cryptography Assignment 2
 */
package assignment2;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Random;

/**
 *
 * @author Harinder
 */
public class Utility {

    public static final int BLOCK_SIZE = 100000000;
    long blockNumber;
    int textLength;
    String alphabet;
    int alphabetLength;

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

    // Generate SHA1 code
    public static String SHA1(String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    public static String getHex(byte[] mDigest) {

        String values = "0123456789abcdefghijklmnopqrstuvwxyz";
        if (mDigest == null) {
            return null;
        }
        final StringBuilder hex = new StringBuilder(2 * mDigest.length);
        for (final byte b : mDigest) {
            hex.append(values.charAt((b & 0xF0) >> 4))
                    .append(values.charAt((b & 0x0F)));
        }
        return hex.toString();
    }

    // Generating a random size for the plaintext
    public static int generateRandomLength(int min, int max) {
        int length = min + (int) (Math.random() * ((max - min) + 1));
        return length;
    }

    // Generating a Random Character
    public static char generateRandomCharacter(int min, int max) {
        int index = min + (int) (Math.random() * ((max - min) + 1));
        return ReductionFunction.alphabetSet.charAt(index);
    }

    // Generating a Random Integer
    public static int generateRandomInteger(int start, int end, Random rand) {
        if (start > end) {
            throw new IllegalArgumentException("Start has exceeded the end!");
        }
        long range = (long) end - (long) start + 1;
        long fraction = (long) (range * rand.nextDouble());
        int randomNumber = (int) (fraction + start);
        return randomNumber;
    }

    // Generate a random plaintext
    public static String generateRandomPlaintext(int length) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buf.append(generateRandomCharacter(0, 25));
        }
        return buf.toString();
    }

    public String getText(int blockIndex) {
        char[] characterValues = new char[textLength];
        long index = (long) blockNumber * BLOCK_SIZE + blockIndex;
        for (int i = 0; i < textLength; i++) {
            int offset = (int) (index % alphabetLength);
            characterValues[textLength - 1 - i] = alphabet.charAt(offset);
            index /= alphabetLength;
        }
        if (index > 0) {
            return null;
        }
        return new String(characterValues);
    }
}
