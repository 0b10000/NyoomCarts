package io.github.zerob10000.nyoomcarts.listeners;

import io.github.zerob10000.nyoomcarts.util.MessageUtils;
import io.github.zerob10000.nyoomcarts.util.SignUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignListener implements Listener {

    @EventHandler
    public void OnSignChange(SignChangeEvent event){
        Player player = event.getPlayer();
        String[] lines = event.getLines();

        // Checks for permissions
        if(lines[0].contains("[nyoom]") && !player.hasPermission("nyoomcarts.sign")){
            player.sendMessage(MessageUtils.getMessagePrefix() + ChatColor.RED + "No permission!");
            event.setCancelled(true);
            return;
        }

        // Check for invalid speed
        if(lines[0].contains("[nyoom]") && !SignUtils.isValidDouble(lines[1])) {
            player.sendMessage(MessageUtils.getMessagePrefix() + ChatColor.RED + "Invalid speed!");
            event.setCancelled(true);
            return;
        }

        // Final sanity check
        if(SignUtils.isValidSignText(lines)){
            event.setLine(0, "" + ChatColor.GREEN + ChatColor.BOLD + "[nyoom]");
            player.sendMessage(MessageUtils.getMessagePrefix() + "Successfully placed valid sign!");
        }
    }
}
