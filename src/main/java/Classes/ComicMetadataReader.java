package Classes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ComicMetadataReader {

    public List<ComicMetadata> readMetadata(String directoryPath) {
        List<ComicMetadata> metadataList = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            readMetadataFromDirectory(directory, metadataList);
        }
        return metadataList;
    }

    private void readMetadataFromDirectory(File directory, List<ComicMetadata> metadataList) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    readMetadataFromDirectory(file, metadataList);
                } else if (file.isFile() && file.getName().endsWith(".json")) {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode rootNode = objectMapper.readTree(file);
                        String name = rootNode.path("name").asText();
                        String author = rootNode.path("authors").asText();
                        String tags = rootNode.path("tags").toString();
                        String path = file.getParent();
                        String coverImagePath = findCoverImage(path); // Find the cover image
                        metadataList.add(new ComicMetadata(name, author, tags, coverImagePath, path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String findCoverImage(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles((dir, name) -> name.matches("(?i).*\\.(jpg|jpeg|png|gif)"));
        if (files != null && files.length > 0) {
            return files[0].getAbsolutePath(); // Take the first image as the cover
        }
        return null;
    }
}