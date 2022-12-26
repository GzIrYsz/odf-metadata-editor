package fr.cyu.depinfo.filemanager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * This class contains different static methods to manage files.
 *
 * @author Thomas REMY
 */
public class FileManager {

    public static final String APPLICATION_VND_OASIS_OPENDOCUMENT_TEXT = "application/vnd.oasis.opendocument.text";

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

    /**
     * Checks if the file given in parameter is an odt.
     *
     * @param path The path to the file.
     * @return {@code true} if the file is an odt, {@code false} otherwise.
     * @throws IOException If an I/O error occurs.
     * @throws NullPointerException If the path to the odt file is {@code null}.
     */
    public static boolean isODT(String path) throws IOException, NullPointerException {
        File f;
        try {
            f = new File(path);
        } catch (NullPointerException e) {
            throw new NullPointerException("Le chemin vers le fichier .odt ne peut pas etre null !");
        }
        return isODT(f);
    }

    /**
     * Checks if the file given in parameter is an odt.
     *
     * @param f The file.
     * @return {@code true} if the file is an odt, {@code false} otherwise.
     * @throws IOException If an I/O error occurs.
     */
    public static boolean isODT(File f) throws IOException {
        if (!f.exists() || f.isDirectory()) {
            throw new NoSuchFileException(f.getCanonicalPath(), null, "Fichier non existant !");
        }
        Path path = f.toPath();
        String mimetype;
        try {
            mimetype = Files.probeContentType(path);
        } catch (IOException e) {
            throw new IOException("Une erreur est survenue lors de la verification du MIMETYPE !", e);
        }
        return mimetype != null && mimetype.equals(APPLICATION_VND_OASIS_OPENDOCUMENT_TEXT);
    }

    /**
     * Gets a list of the odt files in a directory.
     *
     * @param odtFiles The list of the files.
     * @param path The path of the directory.
     * @param recursive {@code true} if you want to search recursively, {@code false} otherwise.
     * @throws IOException If an I/O error occurs.
     */
    public static void getODTInDir(ArrayList<File> odtFiles, String path, boolean recursive) throws IOException {
        File dir = new File(path);
        File[] files = dir.listFiles();
//        if (recursive) {
//            for (File file : files) {
//                if (file.isDirectory()) {
//                    getODTInDir(odtFiles, file.getCanonicalPath(), true);
//                }
//                if (isODT(file)) {
//                    odtFiles.add(file);
//                }
//            }
//        } else {
//            for (File file : files) {
//                if (isODT(file)) {
//                    odtFiles.add(file);
//                }
//            }
//        }
        for (File file : files) {
            if (recursive) {
                if (file.isDirectory()) {
                    getODTInDir(odtFiles, file.getCanonicalPath(), true);
                } else {
                    if (isODT(file)) {
                        odtFiles.add(file);
                    }
                }
            } else if (file.isDirectory()) {

            } else if (isODT(file)) {
                odtFiles.add(file);
            }
        }
    }
}
