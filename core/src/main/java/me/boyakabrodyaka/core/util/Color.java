package me.boyakabrodyaka.core.util;

import org.bukkit.ChatColor;

public final class Color {

    private Color() {
    }

    public static String color(String text) {
        if (text == null || text.isEmpty()) return "";
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}