package Classes;

import UI.ComicReadWindow;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ComicReadController {
    private final ComicReadWindow comicReadWindow;
    private int currentPage = 1;
    private final List<BufferedImage> pages;

    public ComicReadController(ComicReadWindow comicReadWindow, String comicDirectoryPath) {
        this.comicReadWindow = comicReadWindow;
        this.pages = loadPages(new File(comicDirectoryPath));
        updatePage();

        // Set action listeners for the buttons
        comicReadWindow.getPrevButton().addActionListener(e -> prevPage());
        comicReadWindow.getNextButton().addActionListener(e -> nextPage());
    }

    private List<BufferedImage> loadPages(File directory) {
        List<BufferedImage> pageList = new ArrayList<>();
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    pageList.addAll(loadPages(file)); // Recursively load images from subdirectories
                } else if (file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".gif") || file.getName().toLowerCase().endsWith(".png")) {
                    try {
                        if (file.getName().toLowerCase().endsWith(".gif")) {
                            pageList.addAll(loadGif(file));
                        } else {
                            pageList.add(ImageIO.read(file));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return pageList;
    }

    private List<BufferedImage> loadGif(File gifFile) throws IOException {
        List<BufferedImage> frames = new ArrayList<>();
        ImageInputStream stream = ImageIO.createImageInputStream(gifFile);
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("gif");

        if (readers.hasNext()) {
            ImageReader reader = readers.next();
            reader.setInput(stream);
            int numFrames = reader.getNumImages(true);
            for (int i = 0; i < numFrames; i++) {
                frames.add(reader.read(i));
            }
        }
        return frames;
    }

    private void updatePage() {
        if (!pages.isEmpty()) {
        BufferedImage currentPageImage = pages.get(currentPage - 1);
        comicReadWindow.getPageTextField().setText(String.valueOf(currentPage)); // Update the JTextField with the current page number
        int progress = (int) ((double) currentPage / pages.size() * 100);
        comicReadWindow.getProgressLabel().setText("Progress: " + progress + "%");
        comicReadWindow.getImageLabel().setIcon(new ImageIcon(currentPageImage));
        }
}

    private void prevPage() {
        if (currentPage > 1) {
            currentPage--;
            updatePage();
        }
    }

    private void nextPage() {
        if (currentPage < pages.size()) {
            currentPage++;
            updatePage();
        }
    }
}