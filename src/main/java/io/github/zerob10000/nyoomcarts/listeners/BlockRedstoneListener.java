package io.github.zerob10000.nyoomcarts.listeners;

import io.github.zerob10000.nyoomcarts.enums.SignType;
import io.github.zerob10000.nyoomcarts.util.SignUtils;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Locale;

public class BlockRedstoneListener implements Listener {
    static int[][] relativeLocationsToCheck = {{0, 0, 1}, {0, 0, -1}, {1, 0, 0}, {-1, 0, 0}};

    @EventHandler
    public void onBlockRedstoneChange(BlockRedstoneEvent event) {
        boolean highOld = event.getOldCurrent() > 0;
        boolean highNew = event.getNewCurrent() > 0;

        // Only fire if there was a signal change to high
        if (highOld || !highNew) return;

        Block signBlock = getNearbySign(event.getBlock());

        if (signBlock == null) return;

        String[] lines = ((Sign) signBlock.getState()).getLines();

        // Make sure it is a launch sign
        if (SignUtils.classifySign(lines) != SignType.LAUNCH) return;
        if (!SignUtils.validDirections.contains(lines[2].toLowerCase(Locale.ROOT))) return;

        Collection<Entity> nearbyEntities = signBlock.getWorld().getNearbyEntities(signBlock.getLocation(), 2, 2, 2);

        for (Entity entity : nearbyEntities) {
            if (entity.getType() != EntityType.MINECART) continue;
            if (entity.getVelocity().length() > 0.1) continue; // Do not launch minecarts that are already moving

            BlockFace blockFace = BlockFace.valueOf(lines[2].toUpperCase(Locale.ROOT));

            // nyoom
            entity.setVelocity(blockFace.getDirection());
        }
    }

    @Nullable
    public Block getNearbySign(Block block) {
        for (int[] location : relativeLocationsToCheck) {
            Block tempBlock = block.getRelative(location[0], location[1], location[2]);
            if (Tag.SIGNS.isTagged(tempBlock.getType())) return tempBlock;
        }

        return null;
    }
}
