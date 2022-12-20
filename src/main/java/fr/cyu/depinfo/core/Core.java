package fr.cyu.depinfo.core;

import fr.cyu.depinfo.filemanager.*;
import fr.cyu.depinfo.xmlprocessor.*;

import java.io.File;
import java.io.IOException;

public class Core {
    public static String OUTPUT_BASE_DIRECTORY_PATH = System.getProperty("java.io.tmpdir") + File.separator + "odf-metadata-editor";

    private File baseOutDir = new File(OUTPUT_BASE_DIRECTORY_PATH);
    private File outDir;
    private File odt;

    public File getBaseOutDir() {
        return this.baseOutDir;
    }

    public void setBaseOutDir(File baseOutDir) {
        this.baseOutDir = baseOutDir;
    }

    public File getOutDir() {
        return this.outDir;
    }

    public Core setOutDir() throws NullPointerException, IOException {
        if (this.odt == null) {
            throw new NullPointerException("odt file is not set");
        } else {
            this.outDir = new File(this.baseOutDir.getCanonicalPath() + File.separator + odt.getName().substring(0, odt.getName().length() - 3));
        }
        return this;
    }

    public File getOdt() {
        return this.odt;
    }

    public void setOdt(File odt) {
        this.odt = odt;
    }

    public Core setOdtWPath(String path) throws IOException {
        if (FileManager.isODT(path)) {
            setOdt(new File(path));
        }
        return this;
    }

    public static String getAllMetadata(String path) {
        return Core.getAllMetadata(new File(path));
    }

    public static String getAllMetadata(File f) {
        StringBuilder output = new StringBuilder("\n\n*** Metadonneees du fichier " + f.getName() + " ***\n\n");
        ParsedFile parsedMeta = new ParsedFile(f);
        MetadataExtractor meta = new MetadataExtractor(parsedMeta);
        output.append("\nTitre : ").append(meta.getTitle()).append("\n");
        output.append("\nAuteur : ").append(meta.getAuthor()).append("\n");
        output.append("\nSujet : ").append(meta.getSubject()).append("\n");
        output.append("\nDescription : ").append(meta.getDescription()).append("\n");
        output.append("\nMots-cles : ").append("\n");
//        output.append("\nStatistiques : ").append(meta.getStats()).append("\n");
        output.append("\nDate de creation : ").append(meta.getCreationDate()).append("\n");
        return output.toString();
    }
}