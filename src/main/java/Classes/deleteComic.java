package Classes;

import java.io.File;

public class deleteComic {

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