package fr.cyu.depinfo.filemanager;

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;

public class FileManager {
    public static ArrayList<File> getAllFiles(File dir) throws NotDirectoryException {
        ArrayList<File> filesInDir = new ArrayList<File>();

        File[] files = dir.listFiles();
        if (files == null) {
            throw new NotDirectoryException(dir.getName());
        }
        for (File file : files) {
            if (file.isDirectory()) {
                getAllFiles(file);
            } else {
                filesInDir.add(file);
            }
        }
        return filesInDir;
    }
}
