package Display;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

/**
 * Game/GUI-related utilities
 */
public class Utilities {

    private static MediaPlayer bgmPlayer;

    /**
     * Plays a particular sound effect using JavaFX MediaPlayer
     * @param sound Sound file
     * @param volume Volume to play at
     */
    public static void playSoundEffect(String sound, double volume) {

        if (volume <= 0 || volume > 1)
            throw new IllegalArgumentException("Volume must be between (0,1]");

        // BGM Audio
        if (new File(sound).exists()) {
            Media bgm = new Media(new File(sound).toURI().toString());
            bgmPlayer = new MediaPlayer(bgm);
            bgmPlayer.setVolume(volume);
            bgmPlayer.play();
        }
        //bgmPlayer.dispose();
    }
}
