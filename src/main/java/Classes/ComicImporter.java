package Classes;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

public class ComicImporter extends ComicImporterBase {

    @Override
    protected void importZipComic(File file) {
        String outputDir = "S:/ComicBookReader/" + file.getName().replaceAll("\\.[^.]+$", "");
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

    @Override
    protected void importRarComic(File file) {
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
}