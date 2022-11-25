package fr.cyu.depinfo.zipmanager;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
            outDir.mkdir();
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
}
