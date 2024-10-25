package UI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ComicOverview {
    private JPanel mainPanel;
    private JList<String> comicList;
    private JScrollPane comicScroll;

    public ComicOverview() {
        List<String> comics = getComics();
        comicList.setListData(comics.toArray(new String[0]));

        // grootte mainPanel
        mainPanel.setPreferredSize(new Dimension(600, 480));
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private List<String> getComics() {
        // Haal de lijst met comics op (bijvoorbeeld uit een database of bestand)
        return List.of("Comic 1", "Comic 2", "Comic 3"); // Voorbeeld lijst van comics
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bibliotheek");
        frame.setContentPane(new ComicOverview().getMainPanel());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}