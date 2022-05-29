package io.github.zerob10000.nyoomcarts;

import dev.dejvokep.boostedyaml.YamlDocument;
import io.github.zerob10000.nyoomcarts.listeners.SignListener;
import io.github.zerob10000.nyoomcarts.listeners.VehicleListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public final class NyoomCartsPlugin extends JavaPlugin {

    public YamlDocument config;
    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            config = YamlDocument.create(new File(getDataFolder(), "config.yml"), getResource("config.yml"));
        } catch (IOException e) {
            Bukkit.getLogger().severe(ChatColor.RED + "Error while reading config: \n" + e);
        }
        Bukkit.getPluginManager().registerEvents(new VehicleListener(this), this);
        Bukkit.getPluginManager().registerEvents(new SignListener(), this);

        Bukkit.getLogger().info(ChatColor.GREEN + "Enabled " + this.getName());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info(ChatColor.RED + "Disabled " + this.getName());

    }
}
