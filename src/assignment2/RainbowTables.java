/*
 * Rainbow Tables Class
 * Cryptography Assignment 2
 */
package assignment2;

/**
 *
 * @author Harinder
 */
public class RainbowTables {

//    int Hash;
//    String Password;
//    private static int min = Integer.parseInt("100", 36);
//    private static int range = Integer.parseInt("zzz", 36) - min;
//
//    public static String shortHash(String sha) {
//        return Integer.toString(min + (sha.hashCode() & 0x7FFFFFFF) % range, 36);
//    }
//    public class ReductionFunc {
    private static int[] stringToInt(String hash) {

        char[] hashCharArray = new char[hash.length()];
        hashCharArray = hash.toCharArray();
        int[] hashIntArray = new int[hashCharArray.length];

        for (int i = 0; i < hashCharArray.length; i++) {
            hashIntArray[i] = (int) hashCharArray[i];
        }
        return hashIntArray;
    }

    private static char[] IntArrayToCharArray(int[] hashIntArray) {

        char[] hashCharArray = new char[hashIntArray.length];

        for (int i = 0; i < hashIntArray.length; i++) {
            hashCharArray[i] = (char) hashIntArray[i];
        }
        return hashCharArray;
    }

    private static int[] reductionSeed(int[] hashIntArray, int i) {

        int k = 0;
        int j = i;
        int end = hashIntArray.length;
        int begin = 0;
        int[] temp = new int[hashIntArray.length];

        temp = hashIntArray;

        if (i > end) {
            return null;
        } else {

            while (i < end && k < end) {
                hashIntArray[k++] = hashIntArray[i++];
            }
            i = 0;
            while (j > begin && k < end) {
                hashIntArray[k++] = temp[0];
                j--;
            }
        }
        return hashIntArray;
    }

    private static String IntArrayToString(char[] hashIntArray) {

        String StrIntHash = new String(hashIntArray);
        return StrIntHash;
    }

    private static String truncate(String StrIntHash, int length) {

        if (StrIntHash != null && StrIntHash.length() > length) {
            StrIntHash = StrIntHash.substring(0, length);
        }
        return StrIntHash;
    }

    public static void lowerAlpha(String hash, int seed, int len) {

        int[] hashIntArray = new int[hash.length()];
        hashIntArray = stringToInt(hash);
        hashIntArray = reductionSeed(hashIntArray, seed);

        for (int i = 0; i < hashIntArray.length; i++) {
            while (hashIntArray[i] < 97) {
                if (i % 3 == 0) {
                    hashIntArray[i] += 2 + i;

                } else if (i % 3 == 1) {
                    hashIntArray[i] += 4 + i;

                } else if (i % 3 == 2) {
                    hashIntArray[i] += 6 + i;
                }
            }
            while (hashIntArray[i] > 122) {
                if (i % 3 == 0) {
                    hashIntArray[i] -= 1 + i;
                } else if (i % 3 == 1) {
                    hashIntArray[i] -= 4 + i;
                } else if (i % 3 == 2) {
                    hashIntArray[i] -= 6 + i;
                }
            }
        }

        System.out.println(truncate(IntArrayToString(IntArrayToCharArray(hashIntArray)), len));

    }
}