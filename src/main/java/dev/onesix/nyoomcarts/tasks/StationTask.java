package dev.onesix.nyoomcarts.tasks;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Vehicle;

public class StationTask implements Runnable {
    private final Minecart vehicle;
    private final double oldSpeed;
    private final BlockFace directionFace;

    public StationTask(Vehicle vehicle, double oldSpeed, BlockFace directionFace) {
        this.vehicle = (Minecart) vehicle;
        this.oldSpeed = oldSpeed;
        this.directionFace = directionFace;
    }

    @Override
    public void run() {
        this.vehicle.setMaxSpeed(oldSpeed);
        this.vehicle.setVelocity(directionFace.getDirection().multiply(2));
    }
}
