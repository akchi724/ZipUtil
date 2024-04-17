package org.example;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ZipUtil {


    public static void main(String[] args) {
//        Папка, из которой берётся zip-архив
        String sourceFolderPath = "/Users/akchi724/Work/ПВВ/Гостиниц тест/little archive";
//        Папка, на которой окажется zip-архив
        String zipFilePath = "/Users/akchi724/Work/ПВВ/Гостиниц тест/";
//        Название zip архива
        String zipFileName = "little_archive";
        try {
            createZipFromFolder(sourceFolderPath, zipFilePath + zipFileName + ".zip");
            System.out.println("Zip-архив успешно создан!");
        } catch (IOException | ArchiveException e) {
            System.err.println("Ошибка при создании zip-архива: " + e.getMessage());
        }
    }

    public static void createZipFromFolder(String sourceFolderPath, String zipFilePath) throws IOException, ArchiveException {
        File sourceFolder = new File(sourceFolderPath);
        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
             ArchiveOutputStream aos = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, fos)) {
            addFilesToZip(sourceFolder, "", aos);
        }
    }

    private static void addFilesToZip(File file, String basePath, ArchiveOutputStream aos) throws IOException {
        String entryName = basePath + file.getName();
        if (file.isFile()) {
            ZipArchiveEntry entry = new ZipArchiveEntry(file, entryName);
            aos.putArchiveEntry(entry);
            try (FileInputStream fis = new FileInputStream(file)) {
                IOUtils.copy(fis, aos);
            }
            aos.closeArchiveEntry();
        } else if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                addFilesToZip(subFile, entryName + "/", aos);
            }
        }
    }

}