package Game;

import Agents.Agent;
import Agents.Direction;
import Agents.Enemy;
import Agents.MainCharacter;
import Display.Assets;
import Display.HealthBar;
import Display.Utilities;
import Fixed.EventTile;
import Fixed.Lootable;
import Levels.LevelFromTxt;

import Pages.LosePage;
import Pages.StartPage;
import Pages.WinPage;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Game class will run GUI, instantiate/display Mazes, etc.
 */
public class Game extends Application {

    private Maze gameMaze;
    private Scene scene;

    private List<GameNode> staticNodeList;
    private List<GameNode> dynamicNodeList;
    private List<GameNode> agentNodeList;

    private final Color BG_COLOR = Color.web("#091f0e"); // 133b1b, 0d2913, 061409

    // Cell characteristics
    private double gridWidth = 50;
    private double gridHeight = 50;
    private double offsetX = 0;
    private double offsetY = 40;

    // Keeps track of process of removing EndTile node/wall
    private boolean endTileUpdated = false;
    private boolean checkGameOver = false;
    private boolean bonusRewardFlip = false;
    private boolean globalSoundMute = false;

    // Keyboard rate limiter
    private final long KB_RATE_LIMIT = 100_000_000L;
    private long lastMoveTime;

    /**
     * Game.Main method
     * @param args
     */
    public static void main(String[] args) {
        // Launch GUI
        launch(args);
    }

    /**
     * Start method
     * @param primaryStage Game.Main stage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Initiate maze & GameNode array
        gameMaze = new Maze(new LevelFromTxt(Assets.LVL_ONE));

        // Level background image
        Image lvlOneBgImage = new Image("file:" + Assets.LVL_ONE_BG);
        ImageView lvlOneBgImageView = new ImageView(lvlOneBgImage);
        StackPane lvlOneBgStackPane = new StackPane();
        lvlOneBgStackPane.getChildren().add(lvlOneBgImageView);

        // GUI window
        Stage stage = new Stage();
        Group root = new Group(lvlOneBgStackPane);
        scene = new Scene(root, Assets.GAME_WIDTH, Assets.GAME_HEIGHT, BG_COLOR);
        scene.getStylesheets().add(getClass().getResource("/Style.css").toExternalForm());

        // Grid settings
        gridWidth = (scene.getWidth()/gameMaze.getWidth());
        gridHeight = gridWidth;
        offsetY = (scene.getHeight() - (gridHeight * gameMaze.getHeight()));

        // Pause page background image
        Image image = new Image("file:" + Assets.PAUSEPAGE_BG);
        ImageView imgView = new ImageView(image);
        StackPane pauseStackPane = new StackPane();
        pauseStackPane.getChildren().add(imgView);

        // Pause page, it will be called in line276
        Group Pause = new Group(pauseStackPane);
        Scene pausescene = new Scene(Pause,Assets.GAME_WIDTH,Assets.GAME_HEIGHT,Color.BLACK);



        // Load nodes
        dynamicNodeList = new ArrayList<>();
        staticNodeList = new ArrayList<>();
        agentNodeList = new ArrayList<>();
        loadNodes(root);

        // BGM Audio
        Media bgm = new Media(new File(Assets.MAZE_BGM).toURI().toString());
        MediaPlayer bgmPlayer = new MediaPlayer(bgm);
        bgmPlayer.setOnEndOfMedia(new Runnable() { // Repeat music
            @Override
            public void run() {
                bgmPlayer.seek(Duration.ZERO);
            }
        });
        bgmPlayer.setVolume(0.1);
        bgmPlayer.play();

        // BGM Mute button
        Button bgmMuteButton = new Button();
        bgmMuteButton.setTranslateX(scene.getWidth()*0.7);
        bgmMuteButton.setPrefSize(40,40);
        bgmMuteButton.setTranslateY(26);
        bgmMuteButton.setFocusTraversable(false);
        bgmMuteButton.getStyleClass().add("mutebutton");
        bgmMuteButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                if (globalSoundMute) {
                    globalSoundMute = false;
                    bgmPlayer.setMute(false);
                    bgmMuteButton.getStyleClass().remove("mutebuttonon");
                    bgmMuteButton.getStyleClass().add("mutebutton");
                } else {
                    Utilities.playSoundEffect(Assets.MAZE_SFX_CLICK, 0.15);
                    globalSoundMute = true;
                    bgmPlayer.setMute(true);
                    bgmMuteButton.getStyleClass().remove("mutebutton");
                    bgmMuteButton.getStyleClass().add("mutebuttonon");
                }
            }
        });
        root.getChildren().add(bgmMuteButton);

        // Maze
        //Text mazeRender = new Text();
        //mazeRender.setFill(Color.WHITE);
        //mazeRender.setText(gameMaze.getCellsArrayAsString());
        //mazeRender.setFont(Font.loadFont("file:" + Assets.MAZE_FONT, 32));
        //mazeRender.setX(scene.getWidth() / 2 - mazeRender.getLayoutBounds().getWidth() / 2);
        //mazeRender.setY(scene.getHeight() / 2 - mazeRender.getLayoutBounds().getHeight() / 2);
        //root.getChildren().add(mazeRender);

        // Header
        Text header = new Text();
        header.setText(Assets.GAME_TITLE);
        header.setFill(Color.WHITE);
        header.setFont(Font.loadFont("file:" + Assets.WINPAGE_FONT, 42));
        header.setTextAlignment(TextAlignment.CENTER);
        header.setX(10);
        header.setY(header.getFont().getSize() + 12);
        Rectangle headerRec = new Rectangle(0, header.getY()-36, 210, 52);
        headerRec.setFill(Color.BLACK);
        root.getChildren().addAll(headerRec, header);

        //Score
        Text mainCharScore = new Text();
        mainCharScore.setFill(Color.WHITE);
        mainCharScore.setTextAlignment(TextAlignment.RIGHT);
        int score = gameMaze.getMainChar().getCharacterScore();
        mainCharScore.setText(String.valueOf(score));
        mainCharScore.setFont(Font.loadFont("file:" + Assets.MAZE_FONT_8BIT, 32));
        mainCharScore.setX(250);
        mainCharScore.setY(46);
        Text scoreSubHeader = new Text("SCORE");
        scoreSubHeader.setFill(Color.web("#d4d4d4"));
        scoreSubHeader.setFont(Font.loadFont("file:" + Assets.MAZE_FONT_8BIT, 16));
        scoreSubHeader.setX(mainCharScore.getX());
        scoreSubHeader.setY(mainCharScore.getY()+14);
        Rectangle subHeaderRec = new Rectangle(mainCharScore.getX() - 10, mainCharScore.getY()-26, 65, 50);
        subHeaderRec.setFill(Color.BLACK);
        root.getChildren().addAll(subHeaderRec, mainCharScore, scoreSubHeader);

        // Reward count
        Text rewardCount = new Text();
        rewardCount.setFill(Color.WHITE);
        rewardCount.setTextAlignment(TextAlignment.LEFT);
        rewardCount.setText(gameMaze.rewardsRemaining + "/" + gameMaze.rewardTotal);
        rewardCount.setFont(Font.loadFont("file:" + Assets.MAZE_FONT_8BIT, 32));
        rewardCount.setX(scene.getWidth() - 720);
        rewardCount.setY(56);
        Image rewardImg = new Image("file:" + Assets.MO_REWARD);
        ImageView rewardImgView = new ImageView(rewardImg);
        rewardImgView.setScaleX(0.7);
        rewardImgView.setScaleY(0.7);
        rewardImgView.setX(rewardCount.getX() - 62);
        rewardImgView.setY(rewardCount.getY() - 50);
        Rectangle rewardTotalRec = new Rectangle(rewardImgView.getX()+22, rewardImgView.getY()+22, 118, 36);
        rewardTotalRec.setFill(Color.BLACK);
        root.getChildren().addAll(rewardTotalRec, rewardCount, rewardImgView);

        // Timer
        Clock gameTimer = new Clock();
        gameTimer.setLayoutX(rewardCount.getX() + 102);
        gameTimer.setLayoutY(30);
        root.getChildren().add(gameTimer);

        // Timer label
        Text gameTimerLabel = new Text();
        gameTimerLabel.setFill(Color.WHITE);
        gameTimerLabel.setTextAlignment(TextAlignment.LEFT);
        gameTimerLabel.setText(gameTimer.getLabel());
        gameTimerLabel.setFont(Font.loadFont("file:" + Assets.MAZE_FONT_8BIT, 32));
        gameTimerLabel.setX(rewardCount.getX() + 102);
        gameTimerLabel.setY(55);

        // Timer timeline
        Timeline clockTimeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameTimerLabel.setText(gameTimer.getLabel());
            }
        }));

        // Timer black background
        Rectangle timerRec = new Rectangle(gameTimer.getLayoutX()-12, rewardTotalRec.getY(), 110, 36);
        timerRec.setFill(Color.BLACK);
        root.getChildren().addAll(timerRec, gameTimerLabel);

        // Health bar
        HealthBar hb = new HealthBar(scene.getWidth()*0.38, 15, 280, 30, gameMaze.getMainChar());
        root.getChildren().add(hb);

        //BonusReward
        Timeline bonusRewardTimeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // bonus
                if (gameTimer.getDuration() >= 20 && bonusRewardFlip == false) {
                    int n = ThreadLocalRandom.current().nextInt(1, 21);
                    if (n == 20){
                        bonusRewardFlip = true;
                        gameMaze.setNotRetiredBonusReward();
                        if (!globalSoundMute)
                            Utilities.playSoundEffect(Assets.MAZE_SFX_BONUS_REVEAL, 0.15);
                    }
                }
                if (bonusRewardFlip == true){
                    gameMaze.findBonusReward();
                }
            }
        }));

        //enemyMoving
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameMaze.movingEnemies();
                moveAgentNodes(root);
                //System.out.println(gameMaze.movementQueue.size());

                // Update UI
                int score = gameMaze.getMainChar().getCharacterScore();
                mainCharScore.setText(String.valueOf(score));
                rewardCount.setText(gameMaze.rewardsRemaining + "/" + gameMaze.rewardTotal);

                // End condition
                if (checkEndCondition() && !checkGameOver){
                    Utilities.playSoundEffect(Assets.MAZE_SFX_LOSE, 0.15);
                    bonusRewardTimeline.stop();
                    bgmPlayer.stop();
                    LosePage end = new LosePage();
                    end.start(new Stage());
                    stage.close();
                    checkGameOver = true;
                }

                // Health bar & hurt sound
                if (hb.updateHealthBar() && !globalSoundMute)
                    Utilities.playSoundEffect(Assets.MAZE_SFX_HIT, 0.15);

                // Bonus reward
                if (gameMaze.bonusRewardLooted) {
                    gameMaze.bonusRewardLooted = false;
                    if (!globalSoundMute)
                        Utilities.playSoundEffect(Assets.MAZE_SFX_BONUS_GET, 0.12);
                }

                // Load nodes
                try {
                    loadDynamicNodes(root);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        bonusRewardTimeline.setCycleCount(Timeline.INDEFINITE);
        clockTimeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        clockTimeline.play();
        bonusRewardTimeline.play();

        // Key press listener
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                // Rate limiter
                long now = System.nanoTime();
                if (lastMoveTime <= 0 || now - lastMoveTime >= KB_RATE_LIMIT) {
                    switch (event.getCode()) {
                        case UP:
                            gameMaze.mazeObjectDirectionalMove(gameMaze.getMainChar(), Direction.UP);
                            break;
                        case DOWN:
                            gameMaze.mazeObjectDirectionalMove(gameMaze.getMainChar(), Direction.DOWN);
                            break;
                        case LEFT:
                            gameMaze.mazeObjectDirectionalMove(gameMaze.getMainChar(), Direction.LEFT);
                            break;
                        case RIGHT:
                            gameMaze.mazeObjectDirectionalMove(gameMaze.getMainChar(), Direction.RIGHT);
                            break;
                        case ESCAPE:
                            timeline.stop();
                            bonusRewardTimeline.stop();
                            bgmPlayer.pause();
                            clockTimeline.pause();
                            gameTimer.pause();
                            stage.setScene(pausescene);
                    }
                    lastMoveTime = now;
                }

                // Reward SFX
                if (gameMaze.rewardTriggered) {
                    gameMaze.rewardTriggered = false;
                    if (!globalSoundMute)
                        Utilities.playSoundEffect(Assets.MAZE_SFX_ITEM, 0.1);
                }

                // Bonus reward SFX
                if (gameMaze.bonusRewardLooted) {
                    gameMaze.bonusRewardLooted = false;
                    Utilities.playSoundEffect(Assets.MAZE_SFX_BONUS_GET, 0.12);
                }

                // Test loading nodes
                try {
                    loadDynamicNodes(root);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                moveAgentNodes(root);

                //mazeRender.setText(gameMaze.getCellsArrayAsString());
                int score = gameMaze.getMainChar().getCharacterScore();
                mainCharScore.setText(String.valueOf(score));
                rewardCount.setText(gameMaze.rewardsRemaining + "/" + gameMaze.rewardTotal);

                // End condition
                if (checkEndCondition() && checkEndCondition()) {
                    Utilities.playSoundEffect(Assets.MAZE_SFX_LOSE, 0.15);
                    LosePage end = new LosePage();
                    end.start(new Stage());
                    bgmPlayer.stop();
                    stage.close();
                    checkGameOver = true;
                }

                // Health bar & hurt sound
                if (hb.updateHealthBar() && !globalSoundMute)
                    Utilities.playSoundEffect(Assets.MAZE_SFX_HIT, 0.15);

                // Win condition
                for (EventTile evt : gameMaze.getEventTileList()) {
                    if (evt.getEventType() == EventTile.eventType.END_TILE && evt.isRevealed()) {
                        if (gameMaze.getMainChar().getX() == evt.getX()
                                && gameMaze.getMainChar().getY() == evt.getY()) {
                            Utilities.playSoundEffect(Assets.MAZE_SFX_WIN, 0.15);
                            bonusRewardTimeline.stop();
                            bgmPlayer.stop();
                            clockTimeline.pause();
                            WinPage win = new WinPage();
                            win.start(new Stage());
                            stage.close();
                        }
                    }
                }
            }
        });

        //Set Three buttons for Pause Page
        Button Continue = new Button("Continue");
        Continue.setFont(Font.loadFont("file:" + Assets.MAZE_FONT_8BIT, 24));
        Continue.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                stage.setScene(scene);
                //resume gameTimer and let timeline play
                timeline.play();
                bonusRewardTimeline.play();
                bgmPlayer.play();
                clockTimeline.play();
                gameTimer.play();
            }
        });

        Continue.setPrefSize(200, 50);
        Continue.setLayoutX(540);
        Continue.setLayoutY(150);
        Pause.getChildren().add(Continue);

        //Back button
        Button Back = new Button("Main Menu");
        Back.setFont(Font.loadFont("file:" + Assets.MAZE_FONT_8BIT, 24));
        Back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                // open the game widow
                StartPage Menu = new StartPage();
                Menu.start(new Stage());
                bgmPlayer.stop();
                stage.close();

            }
        });

        // Back button size and location
        Back.setPrefSize(200, 50);
        Back.setLayoutX(540);
        Back.setLayoutY(250);
        Pause.getChildren().add(Back);

        //Restart button
        Button Restart = new Button("Restart");
        Restart.setFont(Font.loadFont("file:" + Assets.MAZE_FONT_8BIT, 24));
        Restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                // open the game widow
                Game game = new Game();
                try {
                    game.start(new Stage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //close stage page
                bgmPlayer.stop();
                stage.close();
            }
        });

        Restart.setPrefSize(200, 50);
        Restart.setLayoutX(540);
        Restart.setLayoutY(350);

        Pause.getChildren().add(Restart);



        //Image icon = new Image("resources/filename.png");
        bgmMuteButton.toFront();
        stage.setResizable(false);
        stage.setTitle(Assets.GAME_TITLE);
        stage.setScene(scene);
        stage.show();

        //Exit button
        Button Exit = new Button("Exit");
        Exit.setFont(Font.loadFont("file:" + Assets.MAZE_FONT_8BIT, 24));
        Exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });
        Exit.setPrefSize(200,50);
        Exit.setLayoutX(540);
        Exit.setLayoutY(450);
        Pause.getChildren().add(Exit);
    }

    /**
     * Load all nodes
     * @param root Root node
     * @throws FileNotFoundException
     */
    public void loadNodes(Group root) throws FileNotFoundException {
        loadStaticNodes(root);
        loadDynamicNodes(root);
        loadAgentNodes(root);
    }

    /**
     * Load static nodes (walls)
     * @param root Root node
     * @throws FileNotFoundException
     */
    public void loadStaticNodes(Group root) throws FileNotFoundException {

        // Remove old
        for (GameNode n : staticNodeList) {
            root.getChildren().remove(n);
        }
        staticNodeList.clear();

        // Load trees
        for (int i = 0; i < gameMaze.getHeight(); i++) {
            for (int j = 0; j < gameMaze.getWidth(); j++) {

                // New Node
                try {
                    GameNode newNode = new GameNode(gameMaze, (j + "," + i), j, i, (j * gridWidth) + offsetX, (i * gridHeight) + offsetY, gridWidth, gridHeight);

                    // Add to group
                    root.getChildren().add(newNode);
                    staticNodeList.add(newNode);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Load nodes for object types that are dynamic (anything non-background)
     * @param root Root node
     * @throws FileNotFoundException
     */
    public void loadDynamicNodes(Group root) throws FileNotFoundException {

        // Check for endTile revealed
        checkEndTileNode(root);

        // Clear old
        for (GameNode n : dynamicNodeList) {
            //root.getChildren().remove(n);
            root.getChildren().removeAll(dynamicNodeList);
        }
        dynamicNodeList.clear();

        // Load lootables
        for (Lootable ltb : gameMaze.getLootableList()) {
            try {
                GameNode newNode = new GameNode(ltb, (ltb.getX() + "," + ltb.getY()), ltb.getY(), ltb.getX(),
                        (ltb.getX() * gridWidth) + offsetX, (ltb.getY() * gridHeight) + offsetY, gridWidth, gridHeight);

                // Add to group
                root.getChildren().add(newNode);
                dynamicNodeList.add(newNode);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Load EventTiles
        for (EventTile evt : gameMaze.getEventTileList()) {
            try {
                GameNode newNode = new GameNode(evt, (evt.getX() + "," + evt.getY()), evt.getY(), evt.getX(),
                        (evt.getX() * gridWidth) + offsetX, (evt.getY() * gridHeight) + offsetY, gridWidth, gridHeight);

                // Add to group
                root.getChildren().add(newNode);
                dynamicNodeList.add(newNode);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        agentNodesToFront();
    }

    /**
     * Should only be used once at beginning
     * @param root Root node
     */
    private void loadAgentNodes(Group root) {

        // Clear old
        root.getChildren().removeAll(agentNodeList);
        agentNodeList.clear();

        // Load agents
        for (Agent agt : gameMaze.getAgentList()) {
            if (!((Enemy) agt).isRetired()) {
                try {
                    GameNode newNode = new GameNode(agt, (agt.getX() + "," + agt.getY()), agt.getY(), agt.getX(),
                            (agt.getX() * gridWidth) + offsetX, (agt.getY() * gridHeight) + offsetY, gridWidth, gridHeight);

                    // Add to group
                    root.getChildren().add(newNode);
                    agentNodeList.add(newNode);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Load MainCharacter
        try {
            MainCharacter mc = gameMaze.getMainChar();
            GameNode mainCharNode = new GameNode(mc, (mc.getX() + "," + mc.getY()), mc.getY(), mc.getX(),
                    (mc.getX() * gridWidth) + offsetX, (mc.getY() * gridHeight) + offsetY, gridWidth, gridHeight);

            // Add to group
            root.getChildren().add(mainCharNode);
            agentNodeList.add(mainCharNode);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Bring to front
        for (GameNode n : agentNodeList)
            n.toFront();
    }

    /**
     * Test whether the game should end or not
     */
    private Boolean checkEndCondition(){
        if (gameMaze.getMainChar().getHealth() <= 0) {
            return true;
        }
        else
            return false;
    }

    /**
     * Check the endTile node and reveal it if necessary
     * @param root
     * @throws FileNotFoundException
     */
    private void checkEndTileNode(Group root) throws FileNotFoundException {
        if (gameMaze.endTileRevealed && !endTileUpdated) {

            // Get the EndTile
            for (EventTile et : gameMaze.getEventTileList()) {

                // Check if EndTile is revealed
                if (et.getEventType() == EventTile.eventType.END_TILE && et.isRevealed()) {

                    // Remove the node referencing EndTile
                    GameNode nodeToRemove = null;
                    for (GameNode gn : staticNodeList) {
                        if (gn.getXIndex() == et.getX() && gn.getYIndex() == et.getY()) {
                            nodeToRemove = gn;
                        }
                    }

                    // Remove target node
                    if (nodeToRemove != null) {
                        staticNodeList.remove(nodeToRemove);
                        root.getChildren().remove(nodeToRemove);
                    }
                }
            }
            endTileUpdated = true;
            if (!globalSoundMute)
                Utilities.playSoundEffect(Assets.MAZE_SFX_ENDTILE, 0.15);
        }
    }

    /**
     * Move all Agent nodes based on the movementQueue in Maze
     * @param root Root node
     */
    private void moveAgentNodes(Group root) {
        checkAgentNodeRetirement();

        List<TranslateTransition> ptl = new ArrayList<>();

        while (!gameMaze.movementQueue.isEmpty()) {
            Pair p = gameMaze.movementQueue.remove();

            // Find associated node in agentNodeList
            for (GameNode n : agentNodeList) {
                if (n.getMazeObj() != null && n.getMazeObj() == p.getKey()) {

                    TranslateTransition tt = new TranslateTransition(Duration.millis(50), n);

                    switch ((Direction)p.getValue()) {
                        case UP: tt.setByY((-1)*gridHeight); break;
                        case DOWN: tt.setByY(gridHeight) ;break;
                        case LEFT: tt.setByX((-1)*gridWidth) ;break;
                        case RIGHT: tt.setByX(gridWidth) ;break;
                    }
                    tt.setCycleCount(1);
                    ptl.add(tt);
                }
            }
        }

        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(ptl);
        pt.setCycleCount(1);
        pt.play();
        agentNodesToFront();
    }

    /**
     * Hide inactive Agent nodes
     */
    private void checkAgentNodeRetirement() {
        for (GameNode g : agentNodeList) {
            if (g.getMazeObj() != null && g.getMazeObj() instanceof Enemy) {
                Enemy e = (Enemy) g.getMazeObj();
                if (e.isRetired())
                    g.setVisible(false);
            }
        }
    }

    /**
     * Move all Agent nodes to the front (above items, etc.)
     */
    private void agentNodesToFront() {
        for (GameNode g : agentNodeList) {
            g.toFront();
        }
    }

}
