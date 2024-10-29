package UI;

import Classes.ComicReadController;

import javax.swing.*;

public class ComicReadWindow extends JFrame {
    private JPanel mainPanel;
    private JTextField pageTextField;
    private JLabel progressLabel;
    private JLabel imageLabel;
    private JButton prevButton;
    private JButton nextButton;

    public ComicReadWindow(String comicDirectoryPath) {
        setTitle("Comic Reader");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1920, 1080); // Set window size to 1920x1080 pixels
        setLocationRelativeTo(null); // Center the window on the screen

        // Ensure the .form file is correctly associated and components are initialized
        setContentPane(mainPanel);

        new ComicReadController(this, comicDirectoryPath);
    }

    public JTextField getPageTextField() {
        return pageTextField;
    }

    public JLabel getProgressLabel() {
        return progressLabel;
    }

    public JLabel getImageLabel() {
        return imageLabel;
    }

    public JButton getPrevButton() {
        return prevButton;
    }

    public JButton getNextButton() {
        return nextButton;
    }
}