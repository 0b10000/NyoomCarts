package dev.onesix.nyoomcarts.listeners;

import com.xxmicloxx.NoteBlockAPI.model.SoundCategory;
import com.xxmicloxx.NoteBlockAPI.songplayer.EntitySongPlayer;
import dev.dejvokep.boostedyaml.route.Route;
import dev.onesix.nyoomcarts.NyoomCartsPlugin;
import dev.onesix.nyoomcarts.util.SignUtils;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
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
                if (!event.getVehicle().isEmpty()
                        || event.getVehicle().getType() != EntityType.MINECART) return;
                event.getVehicle().remove();
            }
            case SONG -> {
                // Don't play a song every time
                if (event.getVehicle().getScoreboardTags().contains("nyoom-song")) return;
                event.getVehicle().addScoreboardTag("nyoom-song");

                EntitySongPlayer esp = new EntitySongPlayer(SignUtils.getSignSong(block));
                esp.setEntity(event.getVehicle());
                esp.setCategory(SoundCategory.AMBIENT);
                esp.setAutoDestroy(true);

                double radius = Double.parseDouble(SignUtils.getSign(block).getLine(3));
                List<Entity> nearbyEntities =
                        event.getVehicle().getNearbyEntities(radius + 4, radius + 4, radius + 4);
                for (Entity entity : nearbyEntities) {
                    if (!(entity instanceof Player)) continue;
                    esp.addPlayer((Player) entity);
                }

                esp.setDistance((int) radius);
                esp.setPlaying(true);
            }
        }
    }
}
