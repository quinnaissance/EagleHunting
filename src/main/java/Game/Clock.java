package Game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Clock extends Pane {

    private Timeline animation;
    private int seconds;

    /**
     * Default constructor
     */
    public Clock() {
        this.seconds = 0;

        animation = new Timeline(new KeyFrame(Duration.millis(1000), e -> tick()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    /**
     * Get the label
     * @return
     */
    public String getLabel() {
        int min = (int) Math.floor(seconds/60);
        int sec = seconds % 60;
        return min + " m " + sec + " s";
    }

    /**
     * Update the label value
     */
    private void tick() {
        seconds++;
    }

    /**
     * Pause the animation
     */
    public void pause() {
        animation.pause();
    }

    /**
     * Start/resume the animation
     */
    public void play() {
        animation.play();
    }

    /**
     * Restart the animation
     */
    public void restart() {
        seconds = 0;
    }

    /**
     * Get the duration
     * @return
     */
    public int getDuration() {
        return seconds;
    }
}
