package controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Startklasse der Applikation.
 * 
 * @author Verena Kauth
 *
 */
public class Main extends Application {

    /**
     * Variablen Szene (Variable für die Szene).
     */
    private static Scene scene;
    /**
     * Variable Bildschirmgröße.
     */
    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

    @SuppressWarnings("exports")
    @Override
    /**
     * JavaFX-Start-Methode.
     */
    public void start(Stage primaryStage) throws IOException {

        scene = new Scene(loadFXML("StartFXML"), 500, 750);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    static void setSceneSize(double x, double y) throws IOException {
        scene.getWindow().setWidth(x);
        scene.getWindow().setHeight(y);

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * 
     * @param oldStage
     * @return secondaryStage - Bühne für das Hauptfenster
     * @throws IOException
     */
    @SuppressWarnings("exports")
    public static Stage secondStage(Stage oldStage) throws IOException {
        Stage secondaryStage = new Stage();
        scene = new Scene(loadFXML("StartFXML"), 1024, 768);
        scene.setFill(Color.TRANSPARENT);
        secondaryStage.setTitle("E-Book Bibliothek");
        secondaryStage.initStyle(StageStyle.DECORATED);
        secondaryStage.setScene(scene);
        secondaryStage.show();
        oldStage.close();
        return secondaryStage;

    }

    /**
     * Main-Methode.
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
