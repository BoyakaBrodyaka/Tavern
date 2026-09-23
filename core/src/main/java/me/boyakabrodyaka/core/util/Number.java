package me.boyakabrodyaka.core.util;

public final class Number {

    private Number() {
    }

    public static boolean isNumber(String text) {
        if (text == null || text.isEmpty()) return false;

        try {
            parse(text);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    public static double parse(String text) {
        if (text == null || text.isEmpty()) return 0.0D;

        String trimmed = text.trim().toUpperCase();
        if (trimmed.isEmpty()) return 0.0D;

        char last = trimmed.charAt(trimmed.length() - 1);
        double multiplier = multiplierOf(last);

        String numberPart = multiplier == 1.0D
                ? trimmed
                : trimmed.substring(0, trimmed.length() - 1);

        return Double.parseDouble(numberPart) * multiplier;
    }

    private static double multiplierOf(char suffix) {
        switch (suffix) {
            case 'K': return 1_000.0D;
            case 'M': return 1_000_000.0D;
            case 'B': return 1_000_000_000.0D;
            case 'T': return 1_000_000_000_000.0D;
            default:  return 1.0D;
        }
    }
}