package Pages;

import Display.Assets;

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

/**
 * Introduction window that shows how to play the game
 */
public class IntroductionPage extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Background image
        Image image = new Image("file:" + Assets.INTROPAGE_BG);
        ImageView imgView = new ImageView(image);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(imgView);

        // Introduction GUI window
        Stage stage = new Stage();
        Group root = new Group(stackPane);
        Scene scene = new Scene(root, Assets.GAME_WIDTH, Assets.GAME_HEIGHT, Color.BLACK);

        // Introduction text
        Text introduction = new Text();
        introduction.setFill(Color.WHITE);

        //Back button
        Button Back = new Button("Back");
        Back.setFont(Font.loadFont("file:" + Assets.MAZE_FONT_8BIT, 20));
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
        Back.setPrefSize(80, 30);
        Back.setLayoutX(50);
        Back.setLayoutY(50);

        root.getChildren().add(Back);

        primaryStage.setTitle(Assets.GAME_TITLE);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
