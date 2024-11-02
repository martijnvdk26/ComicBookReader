package Classes;

import java.io.File;

/**
 * De klasse deleteComic biedt functionaliteit om stripboekbestanden te verwijderen.
 */
public class deleteComic {

    /**
     * Verwijdert een stripboekbestand op basis van het opgegeven pad.
     *
     * @param comicPath het pad naar het stripboekbestand dat verwijderd moet worden
     * @return true als het bestand succesvol is verwijderd, anders false
     */
    public boolean deleteComicFile(String comicPath) {
        File comicDir = new File(comicPath);
        System.out.println("Trying to delete: " + comicDir.getAbsolutePath());
        if (comicDir.exists()) {
            boolean result = deleteDirectory(comicDir);
            System.out.println("Deletion " + (result ? "succeeded" : "failed"));
            return result;
        } else {
            System.out.println("Directory does not exist.");
            return false;
        }
    }

    /**
     * Verwijdert een directory en al zijn inhoud.
     *
     * @param dir de directory die verwijderd moet worden
     * @return true als de directory succesvol is verwijderd, anders false
     */
    private boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            if (children != null) {
                for (File child : children) {
                    boolean success = deleteDirectory(child);
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }
}