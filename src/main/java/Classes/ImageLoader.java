package Classes;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * De klasse ImageLoader biedt functionaliteit om afbeeldingen te laden uit een directory.
 */
public class ImageLoader {

    /**
     * Laadt afbeeldingen uit een directory op basis van het opgegeven pad.
     *
     * @param directoryPath het pad naar de directory
     * @return een lijst van BufferedImage objecten die de geladen afbeeldingen vertegenwoordigen
     */
    public static List<BufferedImage> loadImages(String directoryPath) {
        return loadImages(new File(directoryPath));
    }

    /**
     * Laadt afbeeldingen uit een directory.
     *
     * @param directory de directory waarin de afbeeldingen zich bevinden
     * @return een lijst van BufferedImage objecten die de geladen afbeeldingen vertegenwoordigen
     */
    private static List<BufferedImage> loadImages(File directory) {
        List<BufferedImage> images = new ArrayList<>();
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    images.addAll(loadImages(file)); // Recursief afbeeldingen laden uit subdirectories
                } else if (isImageFile(file)) {
                    try {
                        if (file.getName().toLowerCase().endsWith(".gif")) {
                            images.addAll(loadGif(file));
                        } else {
                            images.add(ImageIO.read(file));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return images;
    }

    /**
     * Controleert of een bestand een afbeelding is op basis van de bestandsextensie.
     *
     * @param file het bestand dat gecontroleerd moet worden
     * @return true als het bestand een afbeelding is, anders false
     */
    private static boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".gif");
    }

    /**
     * Laadt de frames van een GIF-bestand.
     *
     * @param gifFile het GIF-bestand
     * @return een lijst van BufferedImage objecten die de frames van de GIF vertegenwoordigen
     * @throws IOException als er een fout optreedt bij het lezen van het GIF-bestand
     */
    private static List<BufferedImage> loadGif(File gifFile) throws IOException {
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
}