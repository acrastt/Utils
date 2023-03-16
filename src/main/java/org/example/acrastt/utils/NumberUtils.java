package org.example.acrastt.utils;

/**
 * The utility class for numbers(int, double, long, etc.).
 *
 * @author Bohan Du
 * @version 1.0.0
 * @since 1.0.0
 */
public final class NumberUtils {

    private NumberUtils() {
    }

    /**
     * Convert a number to number with ordinals in String form.
     *
     * @param number the number to be converted
     * @return the String with ordinals
     */
    public static String convertToOrdinal(int number) {
        // Gets the absolute value to convert negative numbers
        int absNumber = Math.abs(number);
        // Gets the last two digits
        int lastTwoDigits = absNumber % 100;
        // If the last two digits are 11 or 13, we know the suffix is "th"
        if (11 <= lastTwoDigits && lastTwoDigits <= 13) {
            return number + "th";
        }
        // Return the number and suffix depending on the last digit
        return number + returnOrdinal(number);
    }

    /**
     * Returns the suffix for the specified number(Will not exclude exceptions like when the number ends with 11 or 13
     *
     * @param number the number to be calculated
     * @return the number with suffix(Will not exclude exceptions)
     */
    private static String returnOrdinal(int number) {
        // Gets the last digit
        int lastDigit = Math.abs(number) % 10;
        // Initialize the suffix
        String suffix;
        // Set the suffix in different situations
        if (lastDigit == 1) {
            suffix = "st";
        } else if (lastDigit == 2) {
            suffix = "nd";
        } else if (lastDigit == 3) {
            suffix = "rd";
        } else {
            suffix = "th";
        }
        // Returns the number with the suffix calculated
        return suffix;
    }
}