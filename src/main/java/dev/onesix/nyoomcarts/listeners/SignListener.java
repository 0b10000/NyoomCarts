package dev.onesix.nyoomcarts.listeners;

import dev.onesix.nyoomcarts.enums.SignType;
import dev.onesix.nyoomcarts.util.MessageUtils;
import dev.onesix.nyoomcarts.util.SignUtils;
import dev.onesix.nyoomcarts.util.SongUtils;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignListener implements Listener {

    @EventHandler
    public void OnSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        String[] lines = event.getLines();

        // Exclude any non-NyoomCarts signs
        if (!lines[0].contains("[nyoom]")) return;

        // Checks for permissions
        if (!player.hasPermission("nyoomcarts.sign")) {
            player.sendMessage(MessageUtils.getMessagePrefix() + ChatColor.RED + "No permission!");
            event.setCancelled(true);
            return;
        }

        if (SignUtils.classifySign(lines) == SignType.NONE) {
            player.sendMessage(MessageUtils.getMessagePrefix() + ChatColor.RED + "Invalid sign!");
            event.setCancelled(true);
            return;
        }

        if (SignUtils.classifySign(lines) == SignType.SONG && !SongUtils.songExists(lines[2])) {
            player.sendMessage(
                    MessageUtils.getMessagePrefix()
                            + ChatColor.RED
                            + "Song does not exist in songs folder!");
            event.setCancelled(true);
            return;
        }

        // Final sanity check
        if (SignUtils.isValidSignText(lines)) {
            event.setLine(0, "" + ChatColor.GREEN + ChatColor.BOLD + "[nyoom]");
        }
    }
}
