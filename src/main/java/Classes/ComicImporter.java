package Classes;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import javax.swing.*;
import java.io.*;

public class ComicImporter {

    public void importComic(File file) {
        if (!file.exists() || !file.canRead()) {
            showMessage("Import Failed", "File does not exist or cannot be read.");
            return;
        }

        String extension = getFileExtension(file);
        switch (extension.toLowerCase()) {
            case "cbz":
            case "nhlcomic":
                importZipComic(file);
                break;
            case "cbr":
                importRarComic(file);
                break;
            default:
                showMessage("Import Failed", "Unsupported file format.");
                break;
        }
    }

    private void importZipComic(File file) {
        String outputDir = "S:/ComicBookReader";
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
            showMessage("Import Failed", "Failed to import the comic: " + e.getMessage());
        }
    }

    private void importRarComic(File file) {
        String outputDir = "S:/ComicBookReader";
        new File(outputDir).mkdirs();

        try (Archive archive = new Archive(new FileInputStream(file))) {
            FileHeader fileHeader;
            while ((fileHeader = archive.nextFileHeader()) != null) {
                File newFile = new File(outputDir, fileHeader.getFileNameString().trim());
                if (fileHeader.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    new File(newFile.getParent()).mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        archive.extractFile(fileHeader, fos);
                    }
                }
            }
            showMessage("Import Successful", "The comic has been successfully imported.");
        } catch (RarException | IOException e) {
            showMessage("Import Failed", "Failed to import the comic: " + e.getMessage());
        }
    }

    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        return (lastIndexOf == -1) ? "" : name.substring(lastIndexOf + 1);
    }

    private void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}