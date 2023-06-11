package dev.onesix.nyoomcarts.listeners;

import com.xxmicloxx.NoteBlockAPI.event.SongEndEvent;
import com.xxmicloxx.NoteBlockAPI.songplayer.EntitySongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SongListener implements Listener {

    @EventHandler
    public void onSongEnd(SongEndEvent event) {
        SongPlayer player = event.getSongPlayer();
        if (player instanceof EntitySongPlayer) {
            ((EntitySongPlayer) player).getEntity().removeScoreboardTag("nyoom-song");
        }
    }
}
