package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Klasse für die EBook-Objekte.
 * @author Verena Kauth Hausarbeit GP2 WS 20/21 E-Book Verwaltungsprogramm
 *
 */
public class EBook implements Serializable {

    /**
     * UID für das Serialisieren.
     */
    private static final long serialVersionUID = -7397863160507644516L;
    /**
     * Titel des Buches.
     */
    private transient SimpleStringProperty title;
    /**
     * Vorname des Autors.
     */
    private transient SimpleStringProperty authorFirstName;
    /**
     * Nachname des Autors.
     */
    private transient SimpleStringProperty authorLastName;
    /**
     * Genre des Buches.
     */
    private transient SimpleStringProperty genre;
    /**
     * Seitenanzahl des Buches.
     */
    private transient SimpleIntegerProperty pages;
    /**
     * Bewertung des Buches.
     */
    private transient SimpleDoubleProperty rating;
    /**
     * Flag, ob Buch auf den E-Book-Reader geladen ist.
     */
    private transient SimpleBooleanProperty onReader;

    // Constructor
    /**
     * 
     * @param bookTitle  Titel des Buches
     * @param aFirstName Vorname des Autors
     * @param aLastName  Nachname des Autors
     * @param bookGenre  Genre des Buches
     * @param pageCount  Seitenanzahl des Buches
     * @param bookRating Bewertung des Buches
     * @param isOnReader Bool, um anzugeben, ob das Buch bereits auf den
     *                   E-Book-Reader geladen wurde
     */
    public EBook(String bookTitle, String aFirstName, String aLastName, String bookGenre, int pageCount,
            double bookRating, boolean isOnReader) {
        this.title = new SimpleStringProperty(bookTitle);
        this.authorFirstName = new SimpleStringProperty(aFirstName);
        this.authorLastName = new SimpleStringProperty(aLastName);
        this.genre = new SimpleStringProperty(bookGenre);
        this.pages = new SimpleIntegerProperty(pageCount);
        this.rating = new SimpleDoubleProperty(bookRating);
        this.onReader = new SimpleBooleanProperty(isOnReader);

    }

    /**
     * @return title
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * 
     * @param bookTitle
     */
    public void setTitle(String bookTitle) {
        title.set(bookTitle);
    }

    /**
     * 
     * @return authorFirstName
     */
    public String getAuthorFirstName() {
        return authorFirstName.get();
    }

    /**
     * 
     * @param aFirstName
     */
    public void setAuthorFirstName(String aFirstName) {
        authorFirstName.set(aFirstName);
    }

    /**
     * 
     * @return authorLastName
     */
    public String getAuthorLastName() {
        return authorLastName.get();
    }

    /**
     * 
     * @param aLastName
     */
    public void setAuthorLastName(String aLastName) {
        authorLastName.set(aLastName);
    }

    /**
     * 
     * @return genre
     */
    public String getGenre() {
        return genre.get();
    }

    /**
     * 
     * @param bookGenre
     */
    public void setGenre(String bookGenre) {
        genre.set(bookGenre);
    }

    /**
     * 
     * @return pages
     */
    public int getPages() {
        return pages.get();
    }

    /**
     * 
     * @param pageCount
     */
    public void setPages(int pageCount) {
        pages.set(pageCount);
    }

    /**
     * 
     * @return rating
     */
    public double getRating() {
        return rating.get();
    }

    /**
     * 
     * @param bookRating
     */
    public void setRating(double bookRating) {
        rating.set(bookRating);
    }

    /**
     * 
     * @return onReader
     */
    public boolean getOnReader() {
        return onReader.get();
    }

    /**
     * 
     * @param isOnReader
     */
    public void setOnReader(boolean isOnReader) {
        onReader.set(isOnReader);
    }

    /**
     * 
     * @param outputStream
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeObject(title.get());
        outputStream.writeObject(authorFirstName.get());
        outputStream.writeObject(authorLastName.get());
        outputStream.writeObject(genre.get());
        outputStream.writeInt(pages.get());
        outputStream.writeDouble(rating.get());
        outputStream.writeBoolean(onReader.get());

    }

    /**
     * 
     * @param inputStream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        title = new SimpleStringProperty((String) inputStream.readObject());
        authorFirstName = new SimpleStringProperty((String) inputStream.readObject());
        authorLastName = new SimpleStringProperty((String) inputStream.readObject());
        genre = new SimpleStringProperty((String) inputStream.readObject());
        pages = new SimpleIntegerProperty((int) inputStream.readInt());
        rating = new SimpleDoubleProperty((Double) inputStream.readDouble());
        onReader = new SimpleBooleanProperty((Boolean) inputStream.readBoolean());
    }

}
