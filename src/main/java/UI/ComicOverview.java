// ComicOverview.java
package UI;

import Classes.ComicMetadata;
import Classes.ComicMetadataReader;
import Classes.deleteComic;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class ComicOverview {
    private JPanel mainPanel;
    private JScrollPane comicScroll;
    private final JPanel comicPanel;
    private JButton deleteButton;
    private final JFileChooser fileChooser;

    public ComicOverview() {
        // Read metadata from the directory
        ComicMetadataReader reader = new ComicMetadataReader();
        List<ComicMetadata> comics = reader.readMetadata("S:/ComicBookReader");

        // Initialize the panel for the comics
        comicPanel = new JPanel();
        comicPanel.setLayout(new GridLayout(0, 3, 10, 10)); // Grid layout with 3 columns

        // Add the comics to the panel
        for (ComicMetadata comic : comics) {
            JPanel comicItemPanel = new JPanel();
            comicItemPanel.setLayout(new BorderLayout());

            // Add the cover image from the metadata
            ImageIcon coverIcon = scaleImageIcon(comic.getCoverImagePath(), 150, 200);
            if (coverIcon != null) {
                JLabel coverLabel = new JLabel(coverIcon);
                comicItemPanel.add(coverLabel, BorderLayout.CENTER);
            } else {
                JLabel coverLabel = new JLabel("No Image Available");
                comicItemPanel.add(coverLabel, BorderLayout.CENTER);
            }

            // Add the metadata
            JLabel metadataLabel = new JLabel("<html>Name: " + comic.getName() + "<br>Author: " + comic.getAuthor() + "<br>Tags: " + comic.getTags() + "</html>");
            comicItemPanel.add(metadataLabel, BorderLayout.NORTH);

            // Add the read button
            JButton readButton = new JButton("Lezen");
            readButton.addActionListener(e -> {
                openImageInNewWindow(comic.getDirectoryPath(), 1); // Pass the starting page number
            });
            comicItemPanel.add(readButton, BorderLayout.SOUTH);

            comicPanel.add(comicItemPanel);
        }

        // Initialize the delete button
        deleteButton = new JButton("Comic Verwijderen");

        // Initialize the file chooser
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Add action listener to the delete button
        deleteButton.addActionListener(e -> {
            // Open the file chooser to select a directory
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = fileChooser.getSelectedFile();
                deleteComic deleter = new deleteComic();
                if (deleter.deleteComicFile(selectedDirectory.getAbsolutePath())) {
                    JOptionPane.showMessageDialog(null, "Comic map succesvol verwijderd.");
                    refreshPanel();
                } else {
                    JOptionPane.showMessageDialog(null, "Verwijderen mislukt.");
                }
            }
        });

        // Set layout for mainPanel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(800, 600)); // Larger window

        // Add components to the mainPanel
        comicScroll = new JScrollPane(comicPanel);
        mainPanel.add(comicScroll, BorderLayout.CENTER);
        mainPanel.add(deleteButton, BorderLayout.SOUTH);
    }

    private void refreshPanel() {
        // Remove all components from the panel
        comicPanel.removeAll();

        // Read metadata from the directory
        ComicMetadataReader reader = new ComicMetadataReader();
        List<ComicMetadata> comics = reader.readMetadata("S:/ComicBookReader");

        // Add the comics again to the panel
        for (ComicMetadata comic : comics) {
            JPanel comicItemPanel = new JPanel();
            comicItemPanel.setLayout(new BorderLayout());

            // Add the cover image from the metadata
            ImageIcon coverIcon = scaleImageIcon(comic.getCoverImagePath(), 150, 200);
            if (coverIcon != null) {
                JLabel coverLabel = new JLabel(coverIcon);
                comicItemPanel.add(coverLabel, BorderLayout.CENTER);
            } else {
                JLabel coverLabel = new JLabel("No Image Available");
                comicItemPanel.add(coverLabel, BorderLayout.CENTER);
            }

            // Add the metadata
            JLabel metadataLabel = new JLabel("<html>Name: " + comic.getName() + "<br>Author: " + comic.getAuthor() + "<br>Tags: " + comic.getTags() + "</html>");
            comicItemPanel.add(metadataLabel, BorderLayout.NORTH);

            // Add the read button
            JButton readButton = new JButton("Lezen");
            readButton.addActionListener(e -> {
                openImageInNewWindow(comic.getDirectoryPath(), 1); // Pass the starting page number
            });
            comicItemPanel.add(readButton, BorderLayout.SOUTH);

            comicPanel.add(comicItemPanel);
        }

        // Refresh the panel
        comicPanel.revalidate();
        comicPanel.repaint();
    }

    private void openImageInNewWindow(String comicDirectoryPath, int startPage) {
        ComicReadWindow comicReadWindow = new ComicReadWindow(comicDirectoryPath, startPage);
        comicReadWindow.setVisible(true);
    }

    private ImageIcon scaleImageIcon(String imagePath, int width, int height) {
        try {
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                System.err.println("Image file does not exist: " + imagePath);
                return null;
            }
            BufferedImage img = ImageIO.read(imageFile);
            if (img == null) {
                System.err.println("Failed to read image file: " + imagePath);
                return null;
            }
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bibliotheek");
        frame.setContentPane(new ComicOverview().getMainPanel());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600); // Larger window
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}