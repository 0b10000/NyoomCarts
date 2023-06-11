package dev.onesix.nyoomcarts.util;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import dev.onesix.nyoomcarts.NyoomCartsPlugin;
import java.io.File;

public class SongUtils {
    public static boolean songExists(String songToCheck) {
        File songFolder = new File(NyoomCartsPlugin.instance.getDataFolder(), "songs");
        File song = new File(songFolder, songToCheck + ".nbs");

        return song.exists() && NBSDecoder.parse(song) != null;
    }

    public static Song getSong(String song) {
        File songFolder = new File(NyoomCartsPlugin.instance.getDataFolder(), "songs");
        File songFile = new File(songFolder, song + ".nbs");

        return NBSDecoder.parse(songFile);
    }
}
