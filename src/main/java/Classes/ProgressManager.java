// ProgressManager.java
package Classes;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ProgressManager {
    private static final String PROGRESS_DIR = "S:/ComicBookReader/comicsProgress";
    private static final String PROGRESS_FILE = PROGRESS_DIR + "/progress.txt";
    private static Map<String, Integer> progressMap = new HashMap<>();

    static {
        loadProgress();
    }

    public static void saveProgress(String comicName, int page) {
        progressMap.put(comicName, page);
        saveProgressToFile();
    }

    public static int loadProgress(String comicName) {
        return progressMap.getOrDefault(comicName, 1);
    }

    public static Map<String, Integer> getProgressMap() {
        return progressMap;
    }

    private static void loadProgress() {
        File dir = new File(PROGRESS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(PROGRESS_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        progressMap.put(parts[0], Integer.parseInt(parts[1]));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void saveProgressToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROGRESS_FILE))) {
            for (Map.Entry<String, Integer> entry : progressMap.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}