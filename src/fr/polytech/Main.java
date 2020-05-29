package fr.polytech;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    /**
     * Launch JavaFX Application
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starting JavaFX application
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Setting FXML file URL
            final URL url = getClass().getResource("/fr/polytech/View/gui.fxml");

            // Loading FXML file
            final FXMLLoader fxmlLoader = new FXMLLoader(url);

            // Creating GUI root
            final HBox root = (HBox) fxmlLoader.load();

            // Creating JAVAFX Scene
            final Scene scene = new Scene(root, 800, 400);
            primaryStage.setScene(scene);
        } catch (IOException ex) {
            System.err.println("Loading error: " + ex);
        }
        primaryStage.setTitle("Graphical Editor");

        primaryStage.show();
    }
}
