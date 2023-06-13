package dev.onesix.nyoomcarts;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import dev.onesix.nyoomcarts.listeners.BlockRedstoneListener;
import dev.onesix.nyoomcarts.listeners.SignListener;
import dev.onesix.nyoomcarts.listeners.SongListener;
import dev.onesix.nyoomcarts.listeners.VehicleListener;
import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class NyoomCartsPlugin extends JavaPlugin {
    public boolean noteBlockAPILoaded = true;
    public YamlDocument config;
    public static NyoomCartsPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        // Initialize bStats
        Metrics metrics = new Metrics(this, 15337);

        // Initialize configs
        try {
            //noinspection ConstantConditions
            config =
                    YamlDocument.create(
                            new File(getDataFolder(), "config.yml"),
                            getResource("config.yml"),
                            GeneralSettings.DEFAULT,
                            LoaderSettings.builder().setAutoUpdate(true).build(),
                            DumperSettings.DEFAULT,
                            UpdaterSettings.builder()
                                    .setVersioning(new BasicVersioning("file-version"))
                                    .build());

        } catch (IOException e) {
            Bukkit.getLogger().severe(ChatColor.RED + "Error while reading config: \n" + e);
        }

        if (Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI")) {
            if (new File(getDataFolder(), "songs").mkdir()) {
                Bukkit.getLogger().info("Creating songs directory...");
            } else {
                Bukkit.getLogger()
                        .info(
                                "Song directory exists with %d songs"
                                        .formatted(
                                                new File(getDataFolder(), "songs")
                                                        .listFiles(
                                                                (dir, name) ->
                                                                        name.toLowerCase()
                                                                                .endsWith(".nbs"))
                                                        .length));
            }
        } else {
            getLogger()
                    .info(
                            "NoteBlockAPI is not installed, any sound features will not be"
                                    + " available!");
            noteBlockAPILoaded = false;
        }

        // Register events
        Bukkit.getPluginManager().registerEvents(new VehicleListener(this), this);
        Bukkit.getPluginManager().registerEvents(new SignListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockRedstoneListener(), this);
        Bukkit.getPluginManager().registerEvents(new SongListener(), this);

        // And we're off to the races!
        Bukkit.getLogger().info(ChatColor.GREEN + "Enabled " + this.getName());
    }

    @Override
    public void onDisable() {
        instance = null;
        Bukkit.getLogger().info(ChatColor.RED + "Disabled " + this.getName());
    }
}
