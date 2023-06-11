package dev.onesix.nyoomcarts.util;

import org.bukkit.ChatColor;

public class MessageUtils {
    public static String getMessagePrefix() {
        return ""
                + ChatColor.BOLD
                + ChatColor.GOLD
                + "[NyoomCarts] "
                + ChatColor.RESET
                + ChatColor.GREEN;
    }
}
