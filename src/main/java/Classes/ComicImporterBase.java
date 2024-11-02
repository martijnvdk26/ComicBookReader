package Classes;

import javax.swing.*;
import java.io.File;

/**
 * De abstracte klasse ComicImporterBase biedt de basisfunctionaliteit voor het importeren van stripboeken.
 * Subklassen moeten de methoden voor het importeren van ZIP- en RAR-bestanden implementeren.
 */
public abstract class ComicImporterBase {

    /**
     * Importeert een stripboek op basis van het bestandstype.
     *
     * @param file het stripboekbestand dat geïmporteerd moet worden
     */
    public void importComic(File file) {
        if (!file.exists() || !file.canRead()) {
            showMessage("Import Failed", "File does not exist or cannot be read.");
            return;
        }

        String extension = getFileExtension(file);
        switch (extension.toLowerCase()) {
            case "cbz":
            case "nhlcomic":
                importZipComic(file);
                break;
            case "cbr":
                importRarComic(file);
                break;
            default:
                showMessage("Import Failed", "Unsupported file format.");
                break;
        }
    }

    /**
     * Importeert een stripboek uit een ZIP-bestand.
     *
     * @param file het ZIP-bestand dat geïmporteerd moet worden
     */
    protected abstract void importZipComic(File file);

    /**
     * Importeert een stripboek uit een RAR-bestand.
     *
     * @param file het RAR-bestand dat geïmporteerd moet worden
     */
    protected abstract void importRarComic(File file);

    /**
     * Haalt de bestandsextensie op van een gegeven bestand.
     *
     * @param file het bestand waarvan de extensie opgehaald moet worden
     * @return de bestandsextensie als een String
     */
    protected String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        return (lastIndexOf == -1) ? "" : name.substring(lastIndexOf + 1);
    }

    /**
     * Toont een bericht aan de gebruiker.
     *
     * @param title de titel van het bericht
     * @param message de inhoud van het bericht
     */
    protected void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}