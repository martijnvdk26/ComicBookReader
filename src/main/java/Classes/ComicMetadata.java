package Classes;

public class ComicMetadata {
    private String name;
    private String author;
    private String tags;
    private String coverImagePath;
    private String directoryPath;

    // Constructor
    public ComicMetadata(String name, String author, String tags, String coverImagePath, String directoryPath) {
        this.name = name;
        this.author = author;
        this.tags = tags;
        this.coverImagePath = coverImagePath;
        this.directoryPath = directoryPath;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getTags() {
        return tags;
    }

    public String getCoverImagePath() {
        return coverImagePath;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }
}