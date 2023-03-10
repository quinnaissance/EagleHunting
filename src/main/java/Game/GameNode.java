package Game;

import Display.Assets;
import Fixed.BonusReward;
import Fixed.EventTile;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Class for Nodes representing Cell objects
 */
public class GameNode extends Pane { //TODO StackPane

    // Display coord & bounding box
    private Maze gameMaze;
    private MazeObject mazeObj;
    private String name;
    private Label coordLabel;
    private Label coordLabelTrans;
    private Rectangle rec;

    // Display hitboxes & coords
    private final boolean TESTING_ENABLED = false;

    // Fields
    private double x;
    private double y;
    private int xIndex;
    private int yIndex;
    private double width;
    private double height;

    /**
     * GameNode to display a particular MazeObject
     * @param mazeObj Object to display
     * @param name Name of object
     * @param xIndex x-index
     * @param yIndex y-index
     * @param x x-pos
     * @param y y-pos
     * @param width node width
     * @param height node height
     * @throws IOException
     */
    public GameNode(MazeObject mazeObj, String name, int xIndex, int yIndex, double x, double y, double width, double height) throws IOException {

        // Initialize fields
        this.mazeObj = mazeObj;
        this.name = name;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        // Set translation info
        setTranslateX(x);
        setTranslateY(y);

        // Test functions
        if (TESTING_ENABLED)
            initializeTestDisplay();

        // Object traversal
        String inputFileName = Assets.map.get(mazeObj.toString());

        // Don't render EndTile if not revealed
        if (mazeObj.toString().equals("EndTile") && !((EventTile) mazeObj).isRevealed()) {
            inputFileName = "";
        }

        if(mazeObj.toString().equals("BonusReward") && ((BonusReward)mazeObj).isRetired())
            inputFileName = "";

        // Construct object
        if (!inputFileName.isBlank()) {
            FileInputStream fis = new FileInputStream(inputFileName);
            Image img = new Image(fis);
            ImageView iv = new ImageView(img);

            iv.setPreserveRatio(true);
            iv.setFitWidth(width);

            getChildren().add(iv);

            fis.close();
        }
    }

    /**
     * Generates images for all impermeable walls in Maze object
     * @param gameMaze Maze object
     * @param name Name
     * @param xIndex x-index
     * @param yIndex y-index
     * @param x x-pos
     * @param y y-pos
     * @param width grid width
     * @param height grid height
     * @throws IOException
     */
    public GameNode(Maze gameMaze, String name, int xIndex, int yIndex, double x, double y, double width, double height) throws IOException {

        this.gameMaze = gameMaze;
        this.name = name;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        // Set translation info
        setTranslateX(x);
        setTranslateY(y);

        // Test functions
        if (TESTING_ENABLED)
            initializeTestDisplay();

        // Load walls
        if (gameMaze.getCellXY(xIndex, yIndex).hasImpermeableWall()) {
            FileInputStream fis = new FileInputStream(Assets.MO_WALL_BLOCK);
            Image img = new Image(fis);
            ImageView iv = new ImageView(img);
            iv.setPreserveRatio(true);
            iv.setFitWidth(width);
            getChildren().add(iv);
            fis.close();
        }
    }

    /**
     * Display node bounding box and x/y position
     */
    private void initializeTestDisplay() {

        // Create rectangle
        rec = new Rectangle(width, height);
        rec.setStroke(Color.LIGHTGREEN);
        rec.setStrokeWidth(1);
        rec.setOpacity(0.04);
        rec.setFill(null);

        // Label
        coordLabel = new Label(name);
        coordLabel.setTextFill(Color.WHITE);
        coordLabel.setOpacity(0.10);
        coordLabel.setFont(new Font("file:" + Assets.MAZE_FONT, height / 5));

        // Translate coords //TODO
        coordLabelTrans = new Label((int)getTranslateX() + "," + (int)getTranslateY());
        coordLabelTrans.setTextFill(Color.WHITE);
        coordLabelTrans.setOpacity(0.1);
        coordLabelTrans.setFont(new Font("Arial", height / 5));

        getChildren().addAll(rec, coordLabelTrans);
    }

    /**
     * Get the x-index
     * @return x-index
     */
    public int getXIndex() {
        return xIndex;
    }

    /**
     * Get the y-index
     * @return y-index
     */
    public int getYIndex() {
        return yIndex;
    }

    /**
     * Get the MazeObject tied to this node
     * @return
     */
    public MazeObject getMazeObj() {
        return mazeObj;
    }
}