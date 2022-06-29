package io.github.zerob10000.nyoomcarts.listeners;

import dev.dejvokep.boostedyaml.route.Route;
import io.github.zerob10000.nyoomcarts.NyoomCartsPlugin;
import io.github.zerob10000.nyoomcarts.util.SignUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
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
        if (!(event.getVehicle() instanceof Minecart)) return;
        switch (SignUtils.classifySign(block)) {
            case SPEED -> {
                Double speed = SignUtils.getSignSpeed(block);

                if (speed == null) return;
                // Clamp speed to maxSpeed
                if (speed > maxSpeed) speed = maxSpeed;

                ((Minecart) event.getVehicle()).setMaxSpeed(speed);
            }
            case ECD -> {
                if (!event.getVehicle().isEmpty() || event.getVehicle().getType() != EntityType.MINECART) return;
                event.getVehicle().remove();
            }
        }


    }

}
