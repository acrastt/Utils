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
        int absNumber = Math.abs(number);
        int lastTwoDigits = absNumber % 100;
        if (lastTwoDigits == 11 || lastTwoDigits == 13) {
            return number + "th";
        }

        return number + switch (absNumber % 10) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }
}