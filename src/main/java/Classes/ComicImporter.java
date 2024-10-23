package Classes;

import javax.swing.*;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ComicImporter {

    public void importComic(File file) {
        // Specify the correct directory path
        String outputDir = "D:/ComicBookReader/ImportedComics/" + file.getName().replaceAll("\\.\\w+$", "");
        new File(outputDir).mkdirs();

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(file))) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                File newFile = new File(outputDir, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    new File(newFile.getParent()).mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zis.closeEntry();
            }
            showMessage("Import Successful", "The comic has been successfully imported.");
        } catch (IOException e) {
            e.printStackTrace();
            showMessage("Import Failed", "Failed to import the comic.");
        }
    }

    private void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}