package Classes;

import UI.ComicOverview;

import javax.swing.*;
import java.io.File;

/*Deze code verzorgt de controle op de buttons op de voorpagina, ook wel Front Page genoemd.*/
public class ButtonControl {
    private final JButton button1;
    private final JButton button2;
    private final JButton closeButton;

    public ButtonControl(JButton button1, JButton button2, JButton closeButton) {
        this.button1 = button1;
        this.button2 = button2;
        this.closeButton = closeButton;

        addListeners();
    }

    /*Deze methode voegt de listeners toe aan de buttons.*/
    private void addListeners() {
        button1.addActionListener(_ -> openComicOverview());

        button2.addActionListener(_ -> importComic());

        closeButton.addActionListener(_ -> closeApplication());
    }

    /*Deze methode opent de Bibliotheek pagina.*/
    private void openComicOverview() {
        JFrame frame = new JFrame("Comic Overview");
        frame.setContentPane(new ComicOverview().getMainPanel());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /*Deze methode importeert een comic.*/
    private void importComic() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Comic files", "cbr", "cbz", "nhlcomic"));
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            ComicImporter importer = new ComicImporter();
            importer.importComic(selectedFile);
        }
    }

    /* Methode voor het sluiten van de applicatie.*/
    private void closeApplication() {
        System.exit(0);
    }
}