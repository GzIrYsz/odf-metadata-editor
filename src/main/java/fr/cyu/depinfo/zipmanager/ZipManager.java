package fr.cyu.depinfo.zipmanager;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipManager {
    public static int BUFFER_SIZE = 1024;

    public static void unzip(File zipFile, File outDir) throws IOException {
        if (!outDir.exists()) {
            outDir.mkdir();
        }

        FileInputStream fis;
        ZipInputStream zis;
        byte[] buffer = new byte[BUFFER_SIZE];

        fis = new FileInputStream(zipFile);
        zis = new ZipInputStream(fis);
        ZipEntry ze = zis.getNextEntry();
        while (ze != null) {
            String fileName = ze.getName();
            File newFile = new File(outDir + File.separator + fileName);
            new File(newFile.getParent()).mkdirs();
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zis.closeEntry();
            ze = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
        fis.close();
    }
}
