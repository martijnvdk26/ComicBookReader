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

public class ImageLoader {

    public static List<BufferedImage> loadImages(String directoryPath) {
        return loadImages(new File(directoryPath));
    }

    private static List<BufferedImage> loadImages(File directory) {
        List<BufferedImage> images = new ArrayList<>();
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    images.addAll(loadImages(file)); // Recursively load images from subdirectories
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

    private static boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".gif");
    }

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
