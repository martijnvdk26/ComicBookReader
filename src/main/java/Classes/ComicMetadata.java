package Classes;

public class ComicMetadata {
    private String name;
    private String author;
    private String tags;
    private String path;

    // Constructor
    public ComicMetadata(String name, String author, String tags, String path) {
        this.name = name;
        this.author = author;
        this.tags = tags;
        this.path = path;
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

    public String getPath() {
        return path;
    }
}