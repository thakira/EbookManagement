package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.EBook;
import model.ToolTipWindow;

/**
 * 
 * Controller des Applikationshauptbildschirms.
 * 
 * @author Verena Kauth
 *
 */
public class MainScreenController implements Initializable {

    // ************************************************************************************//
    // ******************************* VARIABLES
    // ******************************************//
    // ************************************************************************************//

    /**
     * Flag um den Speichern-Status festzuhalten.
     */
    boolean isSaved = true;

    /**
     * Flag um zwischen Editieren und Neuanlage eines Buches zu unterscheiden.
     */
    private int editIndex = -1;

    /**
     * ObservableList für EBook-Objekte zum Handling der Tabelle.
     */
    private ObservableList<EBook> bookList = FXCollections.observableArrayList();

    /**
     * Variable für ausgewählte Tabellenzeile.
     */
    private TableViewSelectionModel<EBook> selectionModel;

    // ************************************************************************************//
    // ********************************* NODES
    // ********************************************//
    // ************************************************************************************//

    /**
     * Hintergrund-Pane.
     */
    @FXML
    private AnchorPane backgroundPane;
    
    /**
     * Titel des Editorbereiches.
     */
    @FXML
    private Label titleEditor;


    /**
     * TextFeld Buchtitel.
     */
    @FXML
    private TextField textFieldTitle;

    /**
     * verstecktes Label Eingabefehler Buchtitel.
     */
    @FXML
    private Label labelTitleError;

    /**
     * TextFeld Autor Vorname.
     */
    @FXML
    private TextField textFieldFirstName;

    /**
     * TextFeld Autor Nachname.
     */
    @FXML
    private TextField textFieldLastName;

    /**
     * TextFeld Genre.
     */
    @FXML
    private TextField textFieldGenre;

    /**
     * TextFeld Seitenanzahl.
     */
    @FXML
    private TextField textFieldPages;

    /**
     * verstecktes Label Eingabefehler Buchtitel.
     */
    @FXML
    private Label labelPagesError;

    /**
     * CheckBox als Flag, ob das Buch bereits auf den Reader geladen wurde.
     */
    @FXML
    private CheckBox checkOnReader;

    /**
     * Slider zur Eingabe einer Wertung.
     */
    @FXML
    private Slider sliderRating;
    
    /**
     * Label zur Anzeige des Slider-Wertes.
     */
    @FXML
    private Label labelSlider;
    

    /**
     * Speichern-Button für neuen Buch-Eintrag.
     */
    @FXML
    private Button buttonSaveBook;

    /**
     * Abbrechen-Button für neuen Bucheintrag.
     */
    @FXML
    private Button cancelButton;

    /**
     * Löschen-Button für markierten Buch-Eintrag in der Tabelle. Standardmässig
     * versteckt - wird erst aktiviert, wenn ein Buch in der Tabelle angeklickt
     * wurde.
     */
    @FXML
    private Button buttonDelete;

    /**
     * Button für das Editieren bestehender Bücher.
     */
    @FXML
    private Button buttonEdit;

    /**
     * verstecktes Label für Fehlermeldung bei falscher Eingaben bei Wertung.
     */
    @FXML
    private Label labelErrorRating;

    /**
     * verstecktes Label für Fehlermeldung bei falscher Eingaben bei Seiten.
     */
    @FXML
    private Label labelErrorPages;

    /**
     * Tabelle für EBook-Objekte.
     */
    @FXML
    private TableView<EBook> tableView;

    /**
     * Tabellenspalte für Eigenschaft Buchtitel.
     */
    @FXML
    private TableColumn<EBook, String> titleColumn;

    /**
     * Tabellenspalte für Eigenschaft Autor Vorname.
     */
    @FXML
    private TableColumn<EBook, String> firstNameColumn;

    /**
     * Tabellenspalte für Eigenschaft Autor Nachname.
     */
    @FXML
    private TableColumn<EBook, String> lastNameColumn;

    /**
     * Tabellenspalte für Eigenschaft Genre.
     */
    @FXML
    private TableColumn<EBook, String> genreColumn;

    /**
     * Tabellenspalte für Eigenschaft Seiten.
     */
    @FXML
    private TableColumn<EBook, Integer> pagesColumn;

    /**
     * Tabellenspalte für Eigenschaft Wertung.
     */
    @FXML
    private TableColumn<EBook, Double> ratingColumn;

    /**
     * Tabellenspalte für Eigenschaft auf Reader.
     */
    @FXML
    private TableColumn<EBook, Boolean> onReaderColumn;

    // ************************************************************************************//
    // ********************************* NODE EVENTS **************************************//
    // ************************************************************************************//

    /**
     * Event zum Löschen eines E-Book-Objektes aus der Tabelle.
     * 
     * @param event
     */
    @FXML
    void deleteBook(ActionEvent event) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Eintrag löschen?");
        alert.setContentText("Sind Sie sicher, dass Sie diesen Eintrag löschen möchten?");
        ButtonType oK = new ButtonType("Ja");
        ButtonType cancel = new ButtonType("Abbrechen");
        alert.getButtonTypes().setAll(oK, cancel);
        Optional<ButtonType> choice = alert.showAndWait();
        if (choice.get() == oK) {
            bookList.remove(selectionModel.getSelectedIndex());
            buttonDelete.setVisible(false);
            isSaved = false;
            saveBooks();
        }
    }

    /**
     * 
     * Methode zum Leeren der Eingabefelder. Event bei Aktion auf Abbrechen-Button.
     * 
     * @param event
     */
    @FXML
    void cancelButton(ActionEvent event) {
        clearFields();
    }



    /**
     * 
     * Event zum Editieren eines bestehenden E-Book-Eintrages.
     * 
     * @param event
     */
    @FXML
    void editBook(ActionEvent event) {
        textFieldTitle.setText(selectionModel.getSelectedItem().getTitle());
        textFieldFirstName.setText(selectionModel.getSelectedItem().getAuthorFirstName());
        textFieldLastName.setText(selectionModel.getSelectedItem().getAuthorLastName());
        textFieldGenre.setText(selectionModel.getSelectedItem().getGenre());
        textFieldPages.setText(String.valueOf(selectionModel.getSelectedItem().getPages()));
        sliderRating.setValue(selectionModel.getSelectedItem().getRating());
        checkOnReader.setSelected(selectionModel.getSelectedItem().getOnReader());
        editIndex = selectionModel.getSelectedIndex();
        titleEditor.setText("Buch ändern");
        buttonSaveBook.setText("Änderungen speichern");
        labelTitleError.setVisible(false);
        labelPagesError.setVisible(false);
        textFieldTitle.setStyle("-fx-border-color: grey;");
        textFieldPages.setStyle("-fx-border-color: grey;");
        
    }

    /**
     * Methode zum Speichern eines neuen Buch-Eintrags.
     * 
     * @param event
     */
    @FXML
    void buttonSaveBook(ActionEvent event) {
        addBook();
    }

    /**
     * Event zum Laden der Bücher aus der Datei.
     * 
     * @param event
     */
    @FXML
    void loadFile(ActionEvent event) {
        loadBooks();
    }

    /**
     * Event für Tooltip zum Vorgehen beim Editieren. ausgelöst durch MouseOver über
     * der Tabelle.
     * 
     * @param event
     */
    @FXML
    void tableMouseOver(MouseEvent event) {
        tableView.setTooltip(ToolTipWindow.createToolTip("Zum Editieren oder Löschen eines Buches, bitte das Buch anklicken"));
    }

    /**
     * Event beim Schließen der Applikation. prüft, ob alle Daten gespeichert sind
     * und fragt ansonsten nach, ob man vor dem Verlassen speichern möchte.
     * 
     * @param event
     */
    @SuppressWarnings("exports")
    @FXML
    public void exitApplication(ActionEvent event) {
        ((Stage) backgroundPane.getScene().getWindow()).close();
    }

    /**
     * Initalisieren-Methode der GUI.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        tableRowClickEvent();
        loadBooks();

        // Event zum Abfangen des Beendens der Applikation um eine Nachfrage zum
        // Speichern ungespeicherter Inhalte zu stellen.
        Stage stage = StartController.getStage();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent arg0) {
                if (!isSaved || !textFieldTitle.getText().isEmpty()) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Vor Beenden speichern?");
                    alert.setContentText("Möchten Sie Ihre Änderungen vor dem Beenden speichern?");
                    ButtonType oK = new ButtonType("Ja");
                    ButtonType cancel = new ButtonType("Nein, ohne Speichern beenden!");
                    alert.getButtonTypes().setAll(oK, cancel);
                    Optional<ButtonType> choice = alert.showAndWait();
                    if (choice.get() == oK) {
                        addBook();
                    }
                }
            }

        });
    }

    
    
    /**
     * Methode zum Laden der Bücher-Datei in die Tabelle.
     */
    private void loadBooks() {

        try {
            ArrayList<EBook> temp = BooksIO.loadEbooks();
            if (temp != null) {
                bookList.addAll(temp);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        titleColumn.setCellValueFactory(new PropertyValueFactory<EBook, String>("title"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<EBook, String>("authorFirstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<EBook, String>("authorLastName"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<EBook, String>("genre"));
        pagesColumn.setCellValueFactory(new PropertyValueFactory<EBook, Integer>("pages"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<EBook, Double>("rating"));
        onReaderColumn.setCellValueFactory(new PropertyValueFactory<EBook, Boolean>("onReader"));

        tableView.setItems(bookList);
    }

    /**
     * Methode zum Speichern des Tabelleninhalts in der Datei.
     */
    public void saveBooks() {
        bookList = tableView.getItems();
        try {
            isSaved = BooksIO.saveEbooks(bookList, isSaved);
            clearFields();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Methode zum Hinzufügen einer neuen Tabellenzeile nach dem Einfügen eines
     * neuen Buches.
     */
    void addBook() {
        isSaved = false;
        labelTitleError.setVisible(false);
        labelPagesError.setVisible(false);
        textFieldTitle.setStyle("-fx-border-color: grey;");
        textFieldPages.setStyle("-fx-border-color: grey;");
        if (validateEntries()) {
            double roundedSlider = roundDouble(sliderRating.getValue());
            int bookPages;
            if (textFieldPages.getText().isEmpty()) {
                bookPages = Integer.parseInt("0");
            } else {
                bookPages = Integer.parseInt(textFieldPages.getText());
            }
            if (editIndex == -1) {
                EBook book = new EBook(textFieldTitle.getText(), textFieldFirstName.getText(),
                        textFieldLastName.getText(), textFieldGenre.getText(), bookPages, roundedSlider,
                        checkOnReader.isSelected());
                bookList.add(book);
            } else {
                EBook book = bookList.get(editIndex);
                book.setTitle(textFieldTitle.getText());
                book.setAuthorFirstName(textFieldFirstName.getText());
                book.setAuthorLastName(textFieldLastName.getText());
                book.setGenre(textFieldGenre.getText());
                book.setPages(bookPages);
                book.setRating(roundedSlider);
                book.setOnReader(checkOnReader.isSelected());
                editIndex = -1;
                titleEditor.setText("Buch hinzufügen");
                buttonSaveBook.setText("Buch speichern");
            }
            tableView.setItems(bookList);
            saveBooks();
            tableView.refresh();
        }
    }

    /**
     * Methode zum Validieren der Eingaben bei der Erstellung eines neuen Buches.
     * 
     * @return valid - true, wenn keine Fehler gefunden wurden.
     */
    private boolean validateEntries() {
        isSaved = false;
        boolean valid = true;
        if (textFieldTitle.getText().isEmpty()) {
            labelTitleError.setVisible(true);
            textFieldTitle.setStyle("-fx-border-color: red;");
            valid = false;
        }
        if (!textFieldPages.getText().isEmpty() && (!textFieldPages.getText().matches("\\d{0,7}?"))) {
            labelPagesError.setVisible(true);
            textFieldPages.setStyle("-fx-border-color: red;");
            valid = false;
        }

        return valid;
    }
    
    private void clearFields() {
        textFieldTitle.clear();
        textFieldFirstName.clear();
        textFieldLastName.clear();
        textFieldPages.clear();
        textFieldGenre.clear();
        sliderRating.setValue(0);
        checkOnReader.setSelected(false);
        labelTitleError.setVisible(false);
        labelPagesError.setVisible(false);
        textFieldTitle.setStyle("-fx-border-color: grey;");
        textFieldPages.setStyle("-fx-border-color: grey;");
    }

    

    
    /**
     * Methode zum Runden des Slider-Wertes für das Rating auf eine Nachkommastelle.
     * 
     * @param d
     * @return val - gerundeter Double-Wert.
     */
    public double roundDouble(double d) {
        double val = d;
        val = val * 10;
        val = Math.round(val);
        val = val / 10;
        return val;
    }

    /**
     * Aktivierung der Löschen- & Editieren-Möglichkeit nach Klick auf eine
     * Tabellenzeile.
     */
    private void tableRowClickEvent() {
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                selectionModel = tableView.getSelectionModel();
                buttonDelete.setVisible(true);
                buttonEdit.setVisible(true);
            }
        });
    }

    
}
