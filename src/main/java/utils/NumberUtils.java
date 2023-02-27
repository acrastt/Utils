package utils;

public class NumberUtils {

    private NumberUtils() {
    }

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