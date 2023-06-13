package dev.onesix.nyoomcarts.listeners;

import com.xxmicloxx.NoteBlockAPI.model.SoundCategory;
import com.xxmicloxx.NoteBlockAPI.songplayer.EntitySongPlayer;
import dev.dejvokep.boostedyaml.route.Route;
import dev.onesix.nyoomcarts.NyoomCartsPlugin;
import dev.onesix.nyoomcarts.tasks.StationTask;
import dev.onesix.nyoomcarts.util.SignUtils;
import java.util.List;
import java.util.Locale;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;

public class VehicleListener implements Listener {

    private final double maxSpeed;
    private final NyoomCartsPlugin plugin;
    private final Double maxRange;

    public VehicleListener(NyoomCartsPlugin plugin) {
        this.maxSpeed = plugin.config.getDouble(Route.from("speed", "max-speed-multiplier")) * 0.4d;
        this.maxRange = plugin.config.getDouble(Route.from("songs", "max-range"));
        this.plugin = plugin;
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

                // Clamp speed to maxSpeed
                if (radius > maxRange) radius = maxRange;

                List<Entity> nearbyEntities =
                        event.getVehicle().getNearbyEntities(radius + 4, radius + 4, radius + 4);
                for (Entity entity : nearbyEntities) {
                    if (!(entity instanceof Player)) continue;
                    esp.addPlayer((Player) entity);
                }

                esp.setDistance((int) radius);
                esp.setPlaying(true);
            }
            case STATION -> {
                if (event.getVehicle().getScoreboardTags().contains("nyoom-station")) return;
                event.getVehicle().addScoreboardTag("nyoom-station");

                double oldSpeed = ((Minecart) event.getVehicle()).getMaxSpeed();
                event.getVehicle().setVelocity(new Vector(0, 0, 0));
                ((Minecart) event.getVehicle()).setMaxSpeed(0);
                long delay = (long) Double.parseDouble(SignUtils.getSign(block).getLine(3)) * 20;

                String direction = SignUtils.getSign(block).getLine(2);
                if (!SignUtils.validDirections.contains(direction.toLowerCase(Locale.ROOT))) return;
                BlockFace directionFace = BlockFace.valueOf(direction.toUpperCase(Locale.ROOT));

                Bukkit.getScheduler()
                        .runTaskLater(
                                plugin,
                                new StationTask(event.getVehicle(), oldSpeed, directionFace),
                                delay);
                Bukkit.getScheduler()
                        .runTaskLater(
                                plugin,
                                () -> {
                                    event.getVehicle().removeScoreboardTag("nyoom-station");
                                },
                                delay + 20);
            }
        }
    }
}
