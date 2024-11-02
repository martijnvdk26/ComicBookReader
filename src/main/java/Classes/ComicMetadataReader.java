package Classes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * De klasse ComicMetadataReader leest metadata-informatie van stripboeken uit JSON-bestanden.
 */
public class ComicMetadataReader {

    /**
     * Leest metadata van stripboeken uit een opgegeven directory.
     *
     * @param directoryPath het pad naar de directory
     * @return een lijst van ComicMetadata objecten
     */
    public List<ComicMetadata> readMetadata(String directoryPath) {
        List<ComicMetadata> metadataList = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            readMetadataFromDirectory(directory, metadataList);
        }
        return metadataList;
    }

    /**
     * Leest metadata van stripboeken uit een directory en voegt deze toe aan de lijst.
     *
     * @param directory de directory om te doorzoeken
     * @param metadataList de lijst om de metadata aan toe te voegen
     */
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
                        String coverImagePath = findCoverImage(path); // Vind de omslagafbeelding
                        metadataList.add(new ComicMetadata(name, author, tags, coverImagePath, path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Vindt de omslagafbeelding in een directory.
     *
     * @param directoryPath het pad naar de directory
     * @return het pad naar de omslagafbeelding, of null als er geen afbeelding is gevonden
     */
    private String findCoverImage(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles((dir, name) -> name.matches("(?i).*\\.(jpg|jpeg|png|gif)"));
        if (files != null && files.length > 0) {
            return files[0].getAbsolutePath(); // Neem de eerste afbeelding als omslag
        }
        return null;
    }

    /**
     * Haalt de namen van de stripboeken op uit een directory.
     *
     * @param directoryPath het pad naar de directory
     * @return een lijst van stripboeknamen
     */
    public List<String> getComicNames(String directoryPath) {
        List<String> comicNames = new ArrayList<>();
        List<ComicMetadata> metadataList = readMetadata(directoryPath);
        for (ComicMetadata metadata : metadataList) {
            comicNames.add(metadata.getName());
        }
        return comicNames;
    }
}