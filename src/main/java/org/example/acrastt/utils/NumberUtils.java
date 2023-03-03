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
        String end = "th";
        int lastTwo = Math.abs(number) % 100;
        if (lastTwo < 11 || lastTwo > 13) {
            int units = Math.abs(number) % 10;
            end = switch (units) {
                case 1 -> "st";
                case 2 -> "nd";
                case 3 -> "rd";
                default -> "th";
            };
        }
        return number + end;
    }
}