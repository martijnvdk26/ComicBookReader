// ComicReadWindow.java
package UI;

import Classes.ComicReadController;
import Classes.ProgressManager;

import javax.swing.*;
import java.awt.*;

public class ComicReadWindow extends JFrame {
    private JPanel mainPanel;
    private JTextField pageTextField;
    private JLabel progressLabel;
    private JLabel imageLabel;
    private JButton prevButton;
    private JButton nextButton;
    private JButton saveProgressButton;
    private String comicName;

    public ComicReadWindow(String comicName, int startPage) {
        this.comicName = comicName;
        setTitle("Comic Reader");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1920, 1080);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        pageTextField = new JTextField(5);
        progressLabel = new JLabel("Progress: 0%");
        imageLabel = new JLabel();
        prevButton = new JButton("Vorige");
        nextButton = new JButton("Volgende");
        saveProgressButton = new JButton("Save Progress");

        JPanel controlPanel = new JPanel();
        controlPanel.add(prevButton);
        controlPanel.add(pageTextField);
        controlPanel.add(nextButton);
        controlPanel.add(progressLabel);
        controlPanel.add(saveProgressButton);

        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(imageLabel), BorderLayout.CENTER);
        setContentPane(mainPanel);

        ComicReadController controller = new ComicReadController(this, comicName, startPage);

        saveProgressButton.addActionListener(e -> ProgressManager.saveProgress(comicName, controller.getCurrentPage()));
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