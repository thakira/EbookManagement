package controller;

import java.io.*;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import model.EBook;

/**
 * Klasse für den Import/Export der Objekte in eine Datei.
 * 
 * @author Verena Kauth
 * GP2 Hausarbeit im Wintersemester 2020/21
 *
 */
public class BooksIO {
    /**
     * Pfad zur Datei.
     */
    static File bookFile = new File(
            (System.getProperty("user.dir")).toString() + "//resources//bookList//bookList.dat");

    /**
     * 
     * @return eBookArrayList - wenn erfolgreich, none, wenn nicht
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<EBook> loadEbooks() throws IOException, FileNotFoundException, ClassNotFoundException {

        if (bookFile.isFile() && bookFile.canRead()) {
            ArrayList<EBook> eBookArrayList = new ArrayList<EBook>();
            ObjectInputStream or = new ObjectInputStream(new FileInputStream(bookFile));
            Object eBookObject = or.readObject();
            or.close();
            eBookArrayList = (ArrayList<EBook>) eBookObject;
            return eBookArrayList;
        } else {
            return null;
        }
    }

    /**
     * 
     * @param bookList - ObservableList der EBook-Objekte
     * @param isSaved  - Flag für den Speicherstatus
     * @return isSaved - Flag für den Speicherstatus
     * @throws IOException
     */
    public static boolean saveEbooks(ObservableList<EBook> bookList, Boolean isSaved) throws IOException {

        if (!isSaved) {
            try {
                ObjectOutputStream ow = new ObjectOutputStream(new FileOutputStream(bookFile));
                ArrayList<EBook> eBookList = new ArrayList<EBook>();
                eBookList.addAll(bookList);
                ow.writeObject(eBookList);
                ow.close();

                isSaved = true;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isSaved;
    }
}
