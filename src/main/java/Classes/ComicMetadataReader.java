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
                        String path = file.getParent(); // Define the path variable
                        metadataList.add(new ComicMetadata(name, author, tags, path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}