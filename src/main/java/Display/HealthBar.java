package Display;

import Agents.MainCharacter;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Displays healthbar that can scale and colour-change based on
 * MainCharacter health values
 */
public class HealthBar extends Pane {

    private Color startColor = Color.web("#0b8026"); //green
    private Color endColor = Color.web("#eb4034"); // red

    private Color currentCol;
    private Duration transDuration = Duration.millis(300);

    private double healthPct;
    private double startWidth;
    private double startHeight;

    private double health;
    private double maxHealth;

    private final double FLASHING_THRESHOLD = 0.30;

    private MainCharacter mainChar;

    private Text hpText;
    private Rectangle rec;
    private Rectangle recBg;
    private Timeline flashTl;

    /**
     * Dynamic Health bar that responds to MainCharacter health
     * @param x x-position
     * @param y y-position
     * @param startWidth Base Width
     * @param startHeight Base Height
     * @param mainChar MainCharacter object
     */
    public HealthBar(double x, double y, double startWidth, double startHeight, MainCharacter mainChar) {

        // Fields
        this.health = mainChar.getHealth();
        this.maxHealth = mainChar.getMaxHealth();
        this.healthPct = health/maxHealth;
        this.startWidth = startWidth;
        this.startHeight = startHeight;
        this.mainChar = mainChar;

        // Background rectangle
        recBg = new Rectangle(x, y, startWidth, startHeight);
        recBg.setFill(Color.BLACK);
        recBg.setStroke(Color.BLACK);
        recBg.setStrokeWidth(8);

        // Create rectangle
        rec = new Rectangle(x, y, startWidth, startHeight);
        currentCol = getHealthColor();
        rec.setFill(currentCol);

        // HP text
        hpText = new Text();
        hpText.setFont(Font.loadFont("file:" + Assets.MAZE_FONT_8BIT, 24));
        hpText.setText(mainChar.getHealth() + "HP");
        hpText.setX(x + 10);
        hpText.setY(y + (startHeight/2) + (hpText.getFont().getSize()/2) - (startHeight/6));
        hpText.setFill(currentCol.darker().darker());
        hpText.setVisible(true);

        // Add to StackPane
        getChildren().addAll(recBg, rec, hpText);
        setOpacity(100);
        setVisible(true);
        setTranslateX(x);
        setTranslateY(y);
        setVisible(true);
    }

    /**
     * Update the HealthBar. Takes into account MainCharacter health
     * @return boolean
     */
    public boolean updateHealthBar() {

        if (mainChar.getHealth() == health) // no change in health; no need for update
            return false;

        updateHealthPct();
        updateLabel();

        Timeline anim = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rec.widthProperty(), rec.getWidth())),
                new KeyFrame(transDuration, new KeyValue(rec.widthProperty(), startWidth * healthPct))
        );
        anim.play();

        FillTransition ft = new FillTransition(transDuration, rec, (Color) rec.getFill(), getHealthColor());
        ft.play();

        checkFlashing();
        return true;
    }

    /**
     * Update text saying "00HP" on HealthBar
     */
    private void updateLabel() {
        hpText.setText((int)health + "HP");
        hpText.setFill(getHealthColor().darker().darker());
    }

    /**
     * Flash health bar if its below threshold value
     */
    private void checkFlashing() {
        if (healthPct <= FLASHING_THRESHOLD && flashTl == null) {
            Duration dir = Duration.millis(400);
            flashTl = new Timeline(new KeyFrame(dir, new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Color old = (Color) rec.getFill();
                    FillTransition ft = new FillTransition(dir.divide(2), rec, old, old.darker().darker());
                    ft.play();
                    FillTransition ft2 = new FillTransition(dir.divide(2), rec, old.darker().darker(), old);
                    ft2.play();
                }
            }));
            flashTl.setCycleCount(Timeline.INDEFINITE);
            flashTl.play();
        }
    }

    /**
     * Update the percentage of health remaining
     */
    private void updateHealthPct() {
        health = mainChar.getHealth();
        healthPct = health/maxHealth;
        if (healthPct > FLASHING_THRESHOLD && flashTl != null && flashTl.getStatus() == Animation.Status.RUNNING)
            flashTl.stop();
    }

    /**
     * Get the appropriate Colour based on the health percentage.
     * @return Color
     */
    public Color getHealthColor() {
        updateHealthPct();
        return endColor.interpolate(startColor, healthPct);
    }

}