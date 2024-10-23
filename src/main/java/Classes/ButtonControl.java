package Classes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ButtonControl {
    private JButton button1;
    private JButton button2;
    private JButton button3;

    public ButtonControl(JButton button1, JButton button2, JButton button3) {
        this.button1 = button1;
        this.button2 = button2;
        this.button3 = button3;

        addListeners();
    }

    private void addListeners() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openComic();
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importComic();
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteComic();
            }
        });
    }

    private void openComic() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Comic files", "cbr", "cbz", "nhlcomic"));
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            // Add logic to open and display the comic file
            System.out.println("Opening: " + selectedFile.getName());
        }
    }

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

    private void deleteComic() {
        // Add logic to delete a comic file
        System.out.println("Deleting comic...");
    }
}
