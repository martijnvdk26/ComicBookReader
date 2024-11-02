// FrontPage.java
package UI;

import Classes.ButtonControl;
import Classes.ComicMetadataReader;
import Classes.ProgressManager;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class FrontPage {
    private JPanel mainPanel;
    private JLabel recentItems;
    private JLabel option;
    private JButton button1;
    private JButton button2;
    private JButton closeButton;
    private JList<String> recentComicsList;

    public FrontPage() {
        mainPanel = new JPanel(new BorderLayout());

        // Padding labels
        recentItems.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 275));
        option.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Grootte van de buttons
        Dimension buttonSize = new Dimension(150, 20); // Shrink the button size
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        closeButton.setPreferredSize(buttonSize); // Set size for closeButton

        // Panel voor de buttons
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JPanel buttons = new JPanel(new GridLayout(3, 1)); // Adjust grid layout to fit 3 buttons
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(closeButton); // Add closeButton to the panel
        buttonPanel.add(option, BorderLayout.NORTH);
        buttonPanel.add(buttons, BorderLayout.CENTER);

        // Panel voor de recent geopende items
        JPanel listPanel = new JPanel(new BorderLayout());
        recentComicsList = new JList<>();
        updateRecentComicsList();
        listPanel.add(recentItems, BorderLayout.NORTH);
        listPanel.add(new JScrollPane(recentComicsList), BorderLayout.CENTER);

        // Onderdelen voor de 'mainPanel'
        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(listPanel, BorderLayout.CENTER);

        // Initialize ButtonController
        new ButtonControl(button1, button2, closeButton);

        // Add action listener for recent comics list
        recentComicsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedComic = recentComicsList.getSelectedValue();
                if (selectedComic != null) {
                    String comicName = selectedComic.split(" - ")[0];
                    int page = ProgressManager.loadProgress(comicName);
                    new ComicReadWindow(comicName, page).setVisible(true);
                }
            }
        });


    }

    private void updateRecentComicsList() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Map.Entry<String, Integer> entry : ProgressManager.getProgressMap().entrySet()) {
            listModel.addElement(entry.getKey() + " - Page " + entry.getValue());
        }
        recentComicsList.setModel(listModel);
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Martijn's Comic Book Reader");
        frame.setContentPane(new FrontPage().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 480));
        frame.pack();
        frame.setVisible(true);
    }
}