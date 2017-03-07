package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        primaryStage.setTitle("Rock Wall Management");
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setScene(new Scene(root, visualBounds.getWidth(), visualBounds.getHeight()));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
