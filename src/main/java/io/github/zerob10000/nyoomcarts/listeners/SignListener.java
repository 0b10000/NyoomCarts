package io.github.zerob10000.nyoomcarts.listeners;

import io.github.zerob10000.nyoomcarts.enums.SignType;
import io.github.zerob10000.nyoomcarts.util.MessageUtils;
import io.github.zerob10000.nyoomcarts.util.SignUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignListener implements Listener {

    @EventHandler
    public void OnSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        String[] lines = event.getLines();

        //Exclude any non-NyoomCarts signs
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

        // Final sanity check
        if (SignUtils.isValidSignText(lines)) {
            event.setLine(0, "" + ChatColor.GREEN + ChatColor.BOLD + "[nyoom]");
        }
    }
}
