package fr.cyu.depinfo.filemanager;

import java.io.File;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;

/**
 * This class contains different static methods to manage files.
 *
 * @author Thomas REMY
 */
public class FileManager {
    /**
     * Returns a list of all the files and directory contained recursively in a directory.
     *
     * @param dir The root directory, before the recursion.
     * @param filesInDir Before the recursion, it's an empty ArrayList of File type.
     * @return An ArrayList of all the files and directory contained recursively in a given directory.
     * @throws NotDirectoryException If the file given in the first parameter is not a directory.
     */
    public static ArrayList<File> getAllFiles(File dir, ArrayList<File> filesInDir) throws NotDirectoryException {
        ArrayList<File> filesInDirApnd = filesInDir;

        File[] files = dir.listFiles();
        if (files == null) {
            throw new NotDirectoryException(dir.getName());
        }
        for (File file : files) {
            if (file.isDirectory()) {
                filesInDirApnd.add(file);
                getAllFiles(file, filesInDir);
            } else {
                filesInDirApnd.add(file);
            }
        }
        return filesInDirApnd;
    }

    /**
     * Delete a directory and the files contained in it.
     *
     * @param dir The directory to delete.
     * @param files A list of the files contained in the directory.
     */
    public static void deleteDir(File dir, File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                deleteDir(file, file.listFiles());
                file.delete();
            } else {
                file.delete();
            }
        }
        dir.delete();
    }
}
