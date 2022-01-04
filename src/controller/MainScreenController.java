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
     * ObservableList f�r EBook-Objekte zum Handling der Tabelle.
     */
    private ObservableList<EBook> bookList = FXCollections.observableArrayList();

    /**
     * Variable f�r ausgew�hlte Tabellenzeile.
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
     * Speichern-Button f�r neuen Buch-Eintrag.
     */
    @FXML
    private Button buttonSaveBook;

    /**
     * Abbrechen-Button f�r neuen Bucheintrag.
     */
    @FXML
    private Button cancelButton;

    /**
     * L�schen-Button f�r markierten Buch-Eintrag in der Tabelle. Standardm�ssig
     * versteckt - wird erst aktiviert, wenn ein Buch in der Tabelle angeklickt
     * wurde.
     */
    @FXML
    private Button buttonDelete;

    /**
     * Button f�r das Editieren bestehender B�cher.
     */
    @FXML
    private Button buttonEdit;

    /**
     * verstecktes Label f�r Fehlermeldung bei falscher Eingaben bei Wertung.
     */
    @FXML
    private Label labelErrorRating;

    /**
     * verstecktes Label f�r Fehlermeldung bei falscher Eingaben bei Seiten.
     */
    @FXML
    private Label labelErrorPages;

    /**
     * Tabelle f�r EBook-Objekte.
     */
    @FXML
    private TableView<EBook> tableView;

    /**
     * Tabellenspalte f�r Eigenschaft Buchtitel.
     */
    @FXML
    private TableColumn<EBook, String> titleColumn;

    /**
     * Tabellenspalte f�r Eigenschaft Autor Vorname.
     */
    @FXML
    private TableColumn<EBook, String> firstNameColumn;

    /**
     * Tabellenspalte f�r Eigenschaft Autor Nachname.
     */
    @FXML
    private TableColumn<EBook, String> lastNameColumn;

    /**
     * Tabellenspalte f�r Eigenschaft Genre.
     */
    @FXML
    private TableColumn<EBook, String> genreColumn;

    /**
     * Tabellenspalte f�r Eigenschaft Seiten.
     */
    @FXML
    private TableColumn<EBook, Integer> pagesColumn;

    /**
     * Tabellenspalte f�r Eigenschaft Wertung.
     */
    @FXML
    private TableColumn<EBook, Double> ratingColumn;

    /**
     * Tabellenspalte f�r Eigenschaft auf Reader.
     */
    @FXML
    private TableColumn<EBook, Boolean> onReaderColumn;

    // ************************************************************************************//
    // ********************************* NODE EVENTS **************************************//
    // ************************************************************************************//

    /**
     * Event zum L�schen eines E-Book-Objektes aus der Tabelle.
     * 
     * @param event
     */
    @FXML
    void deleteBook(ActionEvent event) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Eintrag l�schen?");
        alert.setContentText("Sind Sie sicher, dass Sie diesen Eintrag l�schen m�chten?");
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
        titleEditor.setText("Buch �ndern");
        buttonSaveBook.setText("�nderungen speichern");
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
     * Event zum Laden der B�cher aus der Datei.
     * 
     * @param event
     */
    @FXML
    void loadFile(ActionEvent event) {
        loadBooks();
    }

    /**
     * Event f�r Tooltip zum Vorgehen beim Editieren. ausgel�st durch MouseOver �ber
     * der Tabelle.
     * 
     * @param event
     */
    @FXML
    void tableMouseOver(MouseEvent event) {
        tableView.setTooltip(ToolTipWindow.createToolTip("Zum Editieren oder L�schen eines Buches, bitte das Buch anklicken"));
    }

    /**
     * Event beim Schlie�en der Applikation. pr�ft, ob alle Daten gespeichert sind
     * und fragt ansonsten nach, ob man vor dem Verlassen speichern m�chte.
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
                    alert.setContentText("M�chten Sie Ihre �nderungen vor dem Beenden speichern?");
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
     * Methode zum Laden der B�cher-Datei in die Tabelle.
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
     * Methode zum Hinzuf�gen einer neuen Tabellenzeile nach dem Einf�gen eines
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
                titleEditor.setText("Buch hinzuf�gen");
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
     * Methode zum Runden des Slider-Wertes f�r das Rating auf eine Nachkommastelle.
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
     * Aktivierung der L�schen- & Editieren-M�glichkeit nach Klick auf eine
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
