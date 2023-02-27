package utils;

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
        String suffix = "th";
        int lastTwoDigits = Math.abs(number) % 100;
        if (lastTwoDigits < 11 || lastTwoDigits > 13) {
            int lastDigit = Math.abs(number) % 10;
            suffix = switch (lastDigit) {
                case 1 -> "st";
                case 2 -> "nd";
                case 3 -> "rd";
                default -> "th";
            };
        }
        return number + suffix;
    }
}