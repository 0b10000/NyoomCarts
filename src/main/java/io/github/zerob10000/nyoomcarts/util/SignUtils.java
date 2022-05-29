package io.github.zerob10000.nyoomcarts.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SignUtils {
    static int[][] relativeLocationsToCheck = {{0, 0, 1}, {0, 0, -1}, {1, 0, 0}, {-1, 0, 0}, {0, -1, 1}, {0, -1, -1}, {1, -1, 0}, {-1, -1, 0}};

    public static boolean isValidSignText(@NotNull String[] lines) {
        return lines[0].contains("[nyoom]") && !lines[1].isBlank() && isValidDouble(lines[1]);
    }

    public static boolean isValidSign(Sign sign) {
        return isValidSignText(sign.getLines());
    }

    @Nullable
    public static Block locateNearbySign(Location location) {
        Block targetBlock = null;

        for (int[] relativeLocationToCheck : relativeLocationsToCheck) {
            Block block = location.getBlock().getRelative(relativeLocationToCheck[0], relativeLocationToCheck[1], relativeLocationToCheck[2]);
            if (Tag.SIGNS.isTagged(block.getType())) {
                if (isValidSign(((Sign) block.getState()))) {
                    targetBlock = block;
                    break;
                }
            }
        }

        return targetBlock;
    }

    @Nullable
    public static Double getSignSpeed(@NotNull Block block) {
        Sign sign = (Sign) block.getState();
        if (isValidSign(sign) && isValidDouble(sign.getLine(1))) {
            return 0.4d * Double.parseDouble(sign.getLine(1));
        }
        return null;
    }

    public static boolean isValidDouble(@NotNull String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
