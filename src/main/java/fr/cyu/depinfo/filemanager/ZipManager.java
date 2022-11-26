package fr.cyu.depinfo.filemanager;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * The class contains static methods to unzip and zip a file.
 *
 * @author Thomas REMY
 */
public class ZipManager {
    /**
     * The size of the buffer.
     */
    public static int BUFFER_SIZE = 1024;

    /**
     * Unzip a file.
     *
     * @param zipFile The file to unzip.
     * @param outDir The output directory.
     * @throws IOException TO DO
     */
    public static void unzip(File zipFile, File outDir) throws IOException {
        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        FileInputStream fis;
        ZipInputStream zis;
        byte[] buffer = new byte[BUFFER_SIZE];

        fis = new FileInputStream(zipFile);
        zis = new ZipInputStream(fis);
        ZipEntry ze;
        while ((ze = zis.getNextEntry()) != null) {
            String fileName = ze.getName();
            File newFile = new File(outDir + File.separator + fileName);
            if (ze.isDirectory()) {
                newFile.mkdirs();
                continue;
            }
            new File(newFile.getParent()).mkdirs();
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zis.closeEntry();
        }
        zis.closeEntry();
        zis.close();
        fis.close();
    }

    public static void zip(File dirToZip, File outDir) throws IOException {
        ArrayList<File> filesInDir = new ArrayList<File>();
        filesInDir = FileManager.getAllFiles(dirToZip, filesInDir);

        FileOutputStream fos;
        ZipOutputStream zos;
        byte[] buffer = new byte[BUFFER_SIZE];

        fos = new FileOutputStream(outDir);
        zos = new ZipOutputStream(fos);

        for (File file : filesInDir) {
            ZipEntry ze = new ZipEntry(file.getCanonicalPath().substring(dirToZip.getCanonicalPath().length() + 1, file.getCanonicalPath().length()));
            zos.putNextEntry(ze);
            System.out.println("Zipping: " + file.getCanonicalPath().substring(dirToZip.getCanonicalPath().length() + 1, file.getCanonicalPath().length()));
            FileInputStream fis = new FileInputStream(file);
            int len;
            while ((len = fis.read(buffer)) > 0) {
                System.out.println(String.valueOf(len));
                zos.write(buffer, 0, len);
            }
            zos.closeEntry();
            fis.close();
        }
        zos.close();
        fos.close();
    }
}
