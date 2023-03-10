package Pages;


import Display.Assets;
import Display.Utilities;
import Game.Game;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Game.Main Menu GUI with navigation buttons
 */
public class StartPage extends Application {

    private double buttonWidth = 600;
    private double buttonHeight = 200;

    private Color btnDefault = Color.web("#645349");
    private Color btnMouseOver = Color.ORANGERED;

    @Override
    public void start(Stage primaryStage) {
        try {

            System.setProperty("prism.lcdtext", "false");
            VBox vBox1 = new VBox();
            HBox hBox1 = new HBox();

            // Background image
            Image image = new Image("file:" + Assets.MAZE_BG);
            ImageView imgView = new ImageView(image);


            //Start button
            Button startButton = new Button("Start");
            startButton.setFont(Font.loadFont("file:" + Assets.MENU_FONT, 32));
            startButton.setTextFill(btnDefault);
            startButton.getStyleClass().add("menubutton");

            // Listeners
            startButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    // open the game widow
                    Game game_1 = new Game();
                    try {
                        game_1.start(new Stage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //close stage page
                    primaryStage.close();
                }
            });
            startButton.setOnMouseEntered(mouseEvent -> {
                Utilities.playSoundEffect(Assets.MAZE_SFX_CLICK, 0.15);
                startButton.setTextFill(btnMouseOver);
                startButton.getStyleClass().remove("menubutton");
                startButton.getStyleClass().add("menubuttonhover");
            });
            startButton.setOnMouseExited(mouseEvent -> {
                startButton.setTextFill(btnDefault);
                startButton.getStyleClass().remove("menubuttonhover");
                startButton.getStyleClass().add("menubutton");
            });

            vBox1.getChildren().add(startButton);

            // set start button size
            startButton.setPrefSize(buttonHeight, buttonWidth);

            //Exit button
            Button exitButton = new Button("Exit");
            exitButton.setFont(Font.loadFont("file:" + Assets.MENU_FONT, 32));
            exitButton.setTextFill(btnDefault);
            exitButton.getStyleClass().add("menubutton");

            // Exit button listener
            exitButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    primaryStage.close();
                }
            });
            exitButton.setOnMouseEntered(mouseEvent -> {
                Utilities.playSoundEffect(Assets.MAZE_SFX_CLICK, 0.15);
                exitButton.setTextFill(btnMouseOver);
                exitButton.getStyleClass().remove("menubutton");
                exitButton.getStyleClass().add("menubuttonhover");
            });
            exitButton.setOnMouseExited(mouseEvent -> {
                exitButton.setTextFill(btnDefault);
                exitButton.getStyleClass().remove("menubuttonhover");
                exitButton.getStyleClass().add("menubutton");
            });

            vBox1.getChildren().add(exitButton);

            // set exit button size
            exitButton.setPrefSize(buttonHeight, buttonWidth);

            // Set Space between button
            vBox1.setSpacing(20);

            //Introduction button
            Button introButton = new Button("Intro");
            introButton.setFont(Font.loadFont("file:" + Assets.MENU_FONT, 32));
            introButton.setTextFill(btnDefault);
            introButton.getStyleClass().add("menubutton");
            introButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    IntroductionPage text = new IntroductionPage();

                    text.start(new Stage());

                    //close stage page
                    primaryStage.close();
                }
            });
            introButton.setOnMouseEntered(mouseEvent -> {
                Utilities.playSoundEffect(Assets.MAZE_SFX_CLICK, 0.15);
                introButton.setTextFill(btnMouseOver);
                introButton.getStyleClass().remove("menubutton");
                introButton.getStyleClass().add("menubuttonhover");
            });
            introButton.setOnMouseExited(mouseEvent -> {
                introButton.setTextFill(btnDefault);
                introButton.getStyleClass().remove("menubuttonhover");
                introButton.getStyleClass().add("menubutton");
            });

            vBox1.getChildren().add(introButton);

            // set introduction button size
            introButton.setPrefSize(buttonHeight, buttonWidth);

            // set location of vbox
            vBox1.setPadding(new Insets(30));
            // add node to vBox
            hBox1.getChildren().add(vBox1);

            // Background stackpane
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(imgView, hBox1);
            Scene scene = new Scene(stackPane, 720, 481);
            scene.getStylesheets().add(getClass().getResource("/Style.css").toExternalForm());

            primaryStage.setTitle(Assets.GAME_TITLE);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
