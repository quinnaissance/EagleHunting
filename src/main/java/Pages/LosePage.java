package Pages;

import Display.Assets;

import Game.Game;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * Game Over window
 */
public class LosePage extends Application {

    private Group root;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) {

        // Background image
        Image image = new Image("file:" + Assets.LOSEPAGE_BG);
        ImageView imgView = new ImageView(image);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(imgView);

        // GUI window
        Stage stage = new Stage();
        root = new Group(stackPane);
        scene = new Scene(root, Assets.GAME_WIDTH, Assets.GAME_HEIGHT, Color.BLACK);

        // Window text
        Text endMessage = new Text();
        endMessage.setText("You Lose");
        endMessage.setFill(Color.DARKRED);
        endMessage.setFont(Font.loadFont("file:" + Assets.LOSEPAGE_FONT, 92));
        endMessage.setX(scene.getWidth()/2 - (endMessage.getLayoutBounds().getWidth()/2));
        endMessage.setY(scene.getHeight()/5);
        root.getChildren().add(endMessage);

        //Back button
        Button Back = new Button("Main Menu");
        Back.setFont(Font.loadFont("file:" + Assets.MAZE_FONT_8BIT, 24));
        //Back.setStyle("-fx-background-color: #ff0000; ");
        Back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                // open the game widow
                StartPage Menu = new StartPage();
                Menu.start(new Stage());
                primaryStage.close();

            }
        });

        // Back button size and location
        Back.setPrefSize(200, 50);
        Back.setLayoutX(540);
        Back.setLayoutY(250);

        root.getChildren().add(Back);


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
                primaryStage.close();
            }
        });

        // Back button size and location
        Restart.setPrefSize(200, 50);
        Restart.setLayoutX(540);
        Restart.setLayoutY(350);

        root.getChildren().add(Restart);

        primaryStage.setTitle(Assets.GAME_TITLE);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
