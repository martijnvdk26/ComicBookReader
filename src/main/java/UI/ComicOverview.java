package UI;

import Classes.ComicMetadata;
import Classes.ComicMetadataReader;
import Classes.deleteComic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

public class ComicOverview {
    private JPanel mainPanel;
    private JScrollPane comicScroll;
    private final JTable comicTable;
    private JButton deleteButton;
    private final DefaultTableModel tableModel;
    private JFileChooser fileChooser;

    public ComicOverview() {
        // Read metadata from the directory
        ComicMetadataReader reader = new ComicMetadataReader();
        List<ComicMetadata> comics = reader.readMetadata("S:/ComicBookReader");

        // Initialize the JTable with comic metadata
        tableModel = new DefaultTableModel(new Object[]{"Name", "Author(s)", "Tags", "Path"}, 0);
        for (ComicMetadata comic : comics) {
            tableModel.addRow(new Object[]{comic.getName(), comic.getAuthor(), comic.getTags(), comic.getPath()});
        }
        comicTable = new JTable(tableModel);
        comicTable.removeColumn(comicTable.getColumnModel().getColumn(3)); // Hide the path column

        // Initialize the delete button
        deleteButton = new JButton("Comic Verwijderen");

        // Initialize the file chooser
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Add action listener to the delete button
        deleteButton.addActionListener(e -> {
            int selectedRow = comicTable.getSelectedRow();
            if (selectedRow != -1) {
                // Delete comic from table selection
                String comicPath = (String) tableModel.getValueAt(selectedRow, 3);
                deleteComic deleter = new deleteComic();
                if (deleter.deleteComicFile(comicPath)) {
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(null, "Comic succesvol verwijderd.");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Verwijderen mislukt.");
                }
            } else {
                // Open file chooser to select directory
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedDirectory = fileChooser.getSelectedFile();
                    deleteComic deleter = new deleteComic();
                    if (deleter.deleteComicFile(selectedDirectory.getAbsolutePath())) {
                        JOptionPane.showMessageDialog(null, "Comic map succesvol verwijderd.");
                        refreshTable();
                    } else {
                        JOptionPane.showMessageDialog(null, "Verwijderen mislukt.");
                    }
                }
            }
        });

        // Set layout for mainPanel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(600, 480));

        // Add components to the mainPanel
        comicScroll = new JScrollPane(comicTable);
        mainPanel.add(comicScroll, BorderLayout.CENTER);
        mainPanel.add(deleteButton, BorderLayout.SOUTH);
    }

    private void refreshTable() {
        // Clear the table
        tableModel.setRowCount(0);

        // Read metadata from the directory
        ComicMetadataReader reader = new ComicMetadataReader();
        List<ComicMetadata> comics = reader.readMetadata("S:/ComicBookReader");

        // Refill the table with updated data
        for (ComicMetadata comic : comics) {
            tableModel.addRow(new Object[]{comic.getName(), comic.getAuthor(), comic.getTags(), comic.getPath()});
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
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