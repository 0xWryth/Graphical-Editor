package fr.polytech;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Localisation du fichier FXML.
            final URL url = getClass().getResource("./View/gui.fxml");
            // Création du loader.
            final FXMLLoader fxmlLoader = new FXMLLoader(url);
            // Chargement du FXML.
            final HBox root = (HBox) fxmlLoader.load();
            // Création de la scène.
            final Scene scene = new Scene(root, 800, 400);
            primaryStage.setScene(scene);
        } catch (IOException ex) {
            System.err.println("Erreur au chargement: " + ex);
        }
        primaryStage.setTitle("Graphical Editor");

        primaryStage.show();
    }
}
