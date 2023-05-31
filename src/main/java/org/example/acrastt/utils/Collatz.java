package org.example.acrastt.utils;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for the Collatz conjecture
 *
 * @author Bohan Du
 * @version 1.0.0
 * @since 1.0.0
 */
public class Collatz {

    /**
     * Should not be called
     */
    private Collatz() {
    }

    /**
     * Map for memorizing previous computed results for shortcuts
     */
    private static final HashMap<BigInteger, Boolean> memory = new HashMap<>();

    /**
     * Check if i satisfies the Collatz conjecture
     *
     * @param i the number to be checked
     * @return whether i satisfies the Collatz conjecture
     */
    private static boolean checkCollatz(@NotNull BigInteger i) {
        // i does not satisfy if the number is negative or zero
        if (i.compareTo(BigInteger.ZERO) <= 0) {
            return false;
        }
        // Set used to detect infinite loops
        Set<BigInteger> used = new HashSet<>();
        while (!i.equals(BigInteger.ONE)) {
            int x = i.intValue();
            // Check whether the value is a power of 2,
            // if yes then you can keep dividing it by 2 to get 1.
            // Which satisfies the Collatz conjecture
            if ((x & (x - 1)) == 0) {
                break;
            }
            // If infinite loops are detected
            if (used.contains(i)) {
                // Mark i as which does not satisfy
                memory.putIfAbsent(i, false);
                // Return false meaning i does not satisfy
                return false;
            }
            // If i is marked as which does not satisfy
            if (Boolean.FALSE.equals(memory.get(i))) {
                // Return false meaning i does not satisfy
                return false;
            }
            // If i is marked as which does satisfy
            if (Boolean.TRUE.equals(memory.get(i))) {
                // Return true meaning i does satisfy
                return true;
            }
            // Add i to the infinite loop detection list
            used.add(i);
            // Change i according to the Collatz conjecture
            if (i.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
                i = i.divide(BigInteger.TWO);
            } else {
                i = i.multiply(BigInteger.valueOf(3)).add(BigInteger.ONE);
            }
        }
        // Mark i as which does satisfy
        used.forEach(trueI -> memory.putIfAbsent(trueI, true));
        // Return true meaning i does satisfy
        return true;
    }

    /**
     * Another version of checkCollatz with a long typed value
     *
     * @param i the number to be checked
     * @return whether i satisfies the Collatz conjecture
     */
    public static boolean checkCollatz(long i) {
        return checkCollatz(BigInteger.valueOf(i));
    }
}
