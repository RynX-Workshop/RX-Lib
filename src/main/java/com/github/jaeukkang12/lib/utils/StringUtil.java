package com.github.jaeukkang12.lib.utils;

import org.bukkit.ChatColor;

public final class StringUtil {
    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
