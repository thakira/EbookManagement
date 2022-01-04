package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Controller für den Startbildschirm.
 * 
 * @author Verena Kauth
 *
 */
public class StartController implements Initializable {

    /**
     * Variable für die zweite Bühne.
     */
    static Stage secondaryStage;

    /**
     * transparenter Hintergrund für Beenden-Button oberhalb des Readers.
     */
    @FXML
    private AnchorPane exitPane;

    /**
     * transparenter Hintergrund für schwarzen abgerundeten Rahmen.
     */
    @FXML
    private AnchorPane backgroundPane;

    /**
     * Hintergrund der GUI.
     */
    @FXML
    private Pane startWindow;

    /**
     * Start-Button.
     */
    @FXML
    private Button startButton;

    /**
     * Titel der Applikation.
     */
    @FXML
    private Text titleText;

    /**
     * Untertitel.
     */
    @FXML
    private Text subTitleText;

    /**
     * Beenden-Button.
     */
    @FXML
    private Button exitButton;

    /**
     * Event zum Beenden der Applikation.
     * 
     * @param event
     */
    @FXML
    void exitStartScreen(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Event für Start-Button.
     * 
     * @param event
     * @throws IOException
     */
    @FXML
    void startApp(ActionEvent event) throws IOException {
        Stage oldStage = (Stage) startButton.getScene().getWindow();
        secondaryStage = Main.secondStage(oldStage);
        Main.setRoot("MainScreenFXML");

    }

    /**
     * Getter für zweite Bühne.
     * 
     * @return secondaryStage
     */
    static Stage getStage() {
        return secondaryStage;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        startWindow.setEffect(new InnerShadow(BlurType.THREE_PASS_BOX, Color.GREY, 15, 0, 0, 0));
        backgroundPane.setStyle(
                "-fx-background-color: transparent; -fx-border-color: black; -fx-border-radius: 5; -fx-border-width: 70;");
        exitPane.setStyle("-fx-background-color: transparent;");
        exitButton.getStyleClass().add("exitButton");
        startButton.getStyleClass().add("startButton");
    }

}
