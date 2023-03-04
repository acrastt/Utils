package org.example.acrastt.utils;

/**
 * The utility class for numbers(int, double, long, etc.).
 *
 * @author Bohan Du
 * @version 1.0
 * @since 1.0
 */
public class NumberUtils {

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
        if (lastTwoDigits == 11 || lastTwoDigits == 13) {
            return number + "th";
        }
        // Return the number and suffix depending on the last digit
        return number + switch (absNumber % 10) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }
}