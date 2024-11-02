package Classes;

/**
 * De klasse ComicMetadata bevat metadata-informatie over een stripboek.
 */
public class ComicMetadata {
    private String name;
    private String author;
    private String tags;
    private String coverImagePath;
    private String directoryPath;

    /**
     * Constructor voor de ComicMetadata klasse.
     *
     * @param name de naam van de strip
     * @param author de auteur van de strip
     * @param tags de tags die aan de strip zijn gekoppeld
     * @param coverImagePath het pad naar de omslagafbeelding
     * @param directoryPath het pad naar de directory waar de strip is opgeslagen
     */
    public ComicMetadata(String name, String author, String tags, String coverImagePath, String directoryPath) {
        this.name = name;
        this.author = author;
        this.tags = tags;
        this.coverImagePath = coverImagePath;
        this.directoryPath = directoryPath;
    }

    /**
     * Haalt de naam van de strip op.
     *
     * @return de naam van de strip
     */
    public String getName() {
        return name;
    }

    /**
     * Haalt de auteur van de strip op.
     *
     * @return de auteur van de strip
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Haalt de tags van de strip op.
     *
     * @return de tags van de strip
     */
    public String getTags() {
        return tags;
    }

    /**
     * Haalt het pad naar de omslagafbeelding op.
     *
     * @return het pad naar de omslagafbeelding
     */
    public String getCoverImagePath() {
        return coverImagePath;
    }

    /**
     * Haalt het pad naar de directory waar de strip is opgeslagen op.
     *
     * @return het pad naar de directory
     */
    public String getDirectoryPath() {
        return directoryPath;
    }
}