package me.boyakabrodyaka.core.util;

public final class FormatNumber {

    private static final String[] SUFFIXES = {"", "K", "M", "B", "T"};
    private static final double THOUSAND = 1000.0D;

    private FormatNumber() {
    }

    public static String format(double amount) {
        if (amount < 0) return "-" + format(-amount);
        if (amount < THOUSAND) return String.valueOf((int) amount);

        int tier = 0;
        double value = amount;

        while (value >= THOUSAND && tier < SUFFIXES.length - 1) {
            value /= THOUSAND;
            tier++;
        }

        return formatValue(value) + SUFFIXES[tier];
    }

    private static String formatValue(double value) {
        if (value >= 100) return trim(value, 1);
        return trim(value, 2);
    }

    private static String trim(double value, int decimals) {
        String formatted = String.format("%." + decimals + "f", value);
        return stripZeros(formatted);
    }

    private static String stripZeros(String text) {
        if (!text.contains(".")) return text;

        int end = text.length();
        while (end > 0 && text.charAt(end - 1) == '0') end--;
        if (end > 0 && text.charAt(end - 1) == '.') end--;

        return text.substring(0, end);
    }
}