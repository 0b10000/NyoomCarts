package io.github.zerob10000.nyoomcarts.listeners;

import dev.dejvokep.boostedyaml.route.Route;
import io.github.zerob10000.nyoomcarts.NyoomCartsPlugin;
import io.github.zerob10000.nyoomcarts.util.SignUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;


public class VehicleListener implements Listener {

    private final double maxSpeed;

    public VehicleListener(NyoomCartsPlugin plugin) {
        this.maxSpeed = plugin.config.getDouble(Route.from("speed", "max-speed-multiplier")) * 0.4d;
    }

    @EventHandler
    public void OnVehicleMove(VehicleMoveEvent event) {
        Block block = SignUtils.locateNearbySign(event.getTo());
        if (block == null) return;
        if (!(event.getVehicle() instanceof Minecart minecart)) return;
        Double speed = SignUtils.getSignSpeed(block);

        if (speed == null) return;
        // Clamp speed to maxSpeed
        if (speed > maxSpeed) speed = maxSpeed;

        minecart.setMaxSpeed(speed);
    }

    private static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

}
