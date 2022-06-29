package io.github.zerob10000.nyoomcarts.util;

import io.github.zerob10000.nyoomcarts.enums.SignType;
import org.bukkit.Location;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class SignUtils {
    public static List<String> validDirections = Arrays.asList("north", "south", "east", "west");
    static int[][] relativeLocationsToCheck = {{0, 0, 1}, {0, 0, -1}, {1, 0, 0}, {-1, 0, 0}, {0, -1, 1}, {0, -1, -1}, {1, -1, 0}, {-1, -1, 0}};

    public static SignType classifySign(@NotNull String[] lines) {
        if (!lines[0].contains("[nyoom]")) return SignType.NONE;
        if (lines[1].isBlank()) return SignType.NONE;

        if (lines[1].contains("ecd")) return SignType.ECD;

        if (isValidDouble(lines[1])) return SignType.SPEED;

        if (lines[1].contains("launch") && validDirections.contains(lines[2])) return SignType.LAUNCH;

        return SignType.NONE;
    }

    public static SignType classifySign(@NotNull Block block) {
        Sign sign = (Sign) block.getState();
        return classifySign(sign.getLines());
    }

    public static boolean isValidSignText(@NotNull String[] lines) {
        return classifySign(lines) != SignType.NONE;
    }

    public static boolean isValidSign(@NotNull Sign sign) {
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
