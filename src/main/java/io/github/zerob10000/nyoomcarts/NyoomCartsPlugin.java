package io.github.zerob10000.nyoomcarts;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import io.github.zerob10000.nyoomcarts.listeners.BlockRedstoneListener;
import io.github.zerob10000.nyoomcarts.listeners.SignListener;
import io.github.zerob10000.nyoomcarts.listeners.VehicleListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public final class NyoomCartsPlugin extends JavaPlugin {

    public YamlDocument config;
    private UpdaterSettings updaterSettings;

    @Override
    public void onEnable() {
        // Initialize bStats
        Metrics metrics = new Metrics(this, 15337);

        // Initialize configs
        try {
            //noinspection ConstantConditions
            config = YamlDocument.create(new File(getDataFolder(), "config.yml"), getResource("config.yml"));

            UpdaterSettings.builder().setVersioning(new BasicVersioning("file-version"));

            config.setUpdaterSettings(updaterSettings);
        } catch (IOException e) {
            Bukkit.getLogger().severe(ChatColor.RED + "Error while reading config: \n" + e);
        }

        // Register events
        Bukkit.getPluginManager().registerEvents(new VehicleListener(this), this);
        Bukkit.getPluginManager().registerEvents(new SignListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockRedstoneListener(), this);


        // And we're off to the races!
        Bukkit.getLogger().info(ChatColor.GREEN + "Enabled " + this.getName());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info(ChatColor.RED + "Disabled " + this.getName());

    }
}
