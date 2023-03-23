package org.example.acrastt.utils;

import org.apache.commons.math3.util.FastMath;

/**
 * The utility class for numbers(int, double, long, etc.).
 *
 * @author Bohan Du
 * @version 1.0.0
 * @since 1.0.0
 */
public final class NumberUtils {


    /**
     * Do not use this(Why this is private)
     */
    private NumberUtils() {
    }

    /**
     * Convert a number to number with ordinals in String form.
     *
     * @param number the number to be converted
     * @return the String with ordinals
     */
    public static String convertToOrdinal(int number) {
        // Gets the last two digits of the absolute value of number
        int lastTwoDigits = FastMath.abs(number) % 100;
        // If the last two digits are 11 or 13, we know the suffix is "th".
        // So return number and "th" is the right thing to do
        if (11 <= lastTwoDigits && lastTwoDigits <= 13) {
            return number + "th";
        }
        // Return the number and suffix depending on the last digit
        return number + returnOrdinal(number);
    }

    /**
     * Returns the suffix for the specified number
     * (Will not exclude exceptions when the number ends with 11, 12, or 13)
     *
     * @param number the number to be calculated
     * @return the number with suffix(Will not exclude exceptions)
     */
    private static String returnOrdinal(int number) {
        // Gets the last digit
        int lastDigit = FastMath.abs(number) % 10;
        // Return the suffix based on different situations
        return switch (lastDigit) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }
}