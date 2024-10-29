package Classes;

import javax.swing.*;
import java.io.File;

public abstract class ComicImporterBase {

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

    protected abstract void importZipComic(File file);

    protected abstract void importRarComic(File file);

    protected String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        return (lastIndexOf == -1) ? "" : name.substring(lastIndexOf + 1);
    }

    protected void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}