package fr.cyu.depinfo.core;

import fr.cyu.depinfo.cli.CLI;
import fr.cyu.depinfo.filemanager.*;
import fr.cyu.depinfo.xmlprocessor.*;
import fr.cyu.depinfo.cli.CLI.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.HashMap;

/**
 * This class represents the core of the CLI and the GUI.
 *
 * @author Thomas REMY
 */
public class Core {
    /**
     * The path to the output base directory.
     */
    public static String OUTPUT_BASE_DIRECTORY_PATH = System.getProperty("java.io.tmpdir") + File.separator + "odf-metadata-editor";

    /**
     * The name of the file containing the main metadata.
     */
    public static String META = "meta.xml";

    /**
     * The name of the file containing the content of the file.
     */
    public static String CONTENT = "content.xml";

    /**
     * The name of the thumbnail image.
     */
    public static String THUMBNAIL_IMAGE = "thumbnail.png";

    /**
     * The name of the thumbnail folder.
     */
    public static String THUMBNAILS_FOLDER = "Thumbnails";

    public static String PICTURES_FOLDER = "Pictures";

    private File baseOutDir = new File(OUTPUT_BASE_DIRECTORY_PATH);
    private File outDir;
    private File odt, outOdt;
    private File picturesDir;
    private File thumbnail;
    private File metaDotXML, contentDotXML;
    private ParsedFile parsedMeta, parsedContent;
    private MetadataExtractor metaExtractor, contentExtractor;

    /**
     * Returns a string reprentation of the main metadata.
     *
     * @return A string representation of the main metadata.
     */
    public String getMainMetadata() {
        StringBuilder output = new StringBuilder();
        output.append("\nTitre : ").append(metaExtractor.getTitle()).append("\n");
        output.append("\nDescription :\n").append(metaExtractor.getDescription()).append("\n");
        output.append("\nSujet : ").append(metaExtractor.getSubject()).append("\n");
        output.append("\nMots-cles : ").append(metaExtractor.getKeywords()).append("\n");
        output.append("\nAuteur : ").append(metaExtractor.getAuthor()).append("\n");
        return output.toString();
    }

    /**
     * Returns a string representation of the statistics/secondary metadata.
     *
     * @return A string representation of the statistics metadata.
     */
    public String getStatisticsMetadata() {
        StringBuilder output = new StringBuilder();
        output.append("\nDate de creation : ").append(metaExtractor.getCreationDate()).append("\n");
        output.append("Taille du fichier : ");
        try {
            output.append(Files.size(odt.toPath()) / 1024).append(" kB");
        } catch (IOException e) {
            output.append("Erreur, taille inconnue !");
        }
        output.append("\n");
        output.append("\nNombre d'images : ").append(metaExtractor.getNbImages()).append("\n");
        if (picturesDir.exists()) {
            for (File file : picturesDir.listFiles()) {
                output.append("\t").append(file.getName()).append(", ");
                try {
                    output.append(Files.size(file.toPath()) / 1024).append(" kB");
                } catch (IOException e) {
                    output.append("Taille non connue");
                }
                output.append("\n");
            }
        }
        output.append("Nombre de pages : ").append(metaExtractor.getNbPages()).append("\n");
        output.append("Nombre de paragraphes : ").append(metaExtractor.getNbParagraphs()).append("\n");
        output.append("Nombre de mots : ").append(metaExtractor.getNbWords()).append("\n");
        output.append("Nombre de caracteres : ").append(metaExtractor.getNbCharacters()).append("\n");
        output.append("Nombre de caracteres non blancs : ").append(metaExtractor.getNbNonWhitespaceCharacters()).append("\n");
        output.append("\nListe des liens hypertexte : ").append(contentExtractor.getHyperlinks()).append("\n");
        return output.toString();
    }

    /**
     * Returns a concatenation of the main and secondary metadata.
     *
     * @return A string representation of all the metadata.
     */
    public String getAllMetadata() {
        StringBuilder output = new StringBuilder();
        output.append("\n\n***** Metadonnees principales *****").append("\n");
        output.append(getMainMetadata());
        output.append("\n\n***** Metadonnees secondaires et statistiques *****");
        output.append(getStatisticsMetadata());
        return output.toString();
    }

    /**
     * Changes the metadata of the parsed document.
     * The metadata is kept the same as before if the object is {@code null}. Otherwise, it is set to the string in parameter.
     *
     * @param editables A dictionary containing the new metadata referenced by their tag in the document.
     * @return The current object.
     */
    public Core setMetadata(HashMap<String, String> editables) {
        metaExtractor.setTextContentByTagName(MetadataExtractor.TITLE, editables.get(MetadataExtractor.TITLE));
        metaExtractor.setTextContentByTagName(MetadataExtractor.DESCRIPTION, editables.get(MetadataExtractor.DESCRIPTION));
        metaExtractor.setTextContentByTagName(MetadataExtractor.AUTHOR, editables.get(MetadataExtractor.AUTHOR));
        metaExtractor.setTextContentByTagName(MetadataExtractor.SUBJECT, editables.get(MetadataExtractor.SUBJECT));
        metaExtractor.setKeywords(editables.get(MetadataExtractor.KEYWORD));
        return this;
    }

    /**
     * Save the parsed document into meta.xml and zip back the odt file.
     *
     * @return The current object.
     * @throws IOException If an I/O error occurs.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Core saveODT() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        parsedMeta.save(metaDotXML);
        ZipManager.zipDir(outDir, outOdt);
        return this;
    }

    /**
     * @return The base output directory.
     */
    public File getBaseOutDir() {
        return this.baseOutDir;
    }

    /**
     * @param baseOutDir The new base output directory.
     */
    public void setBaseOutDir(File baseOutDir) {
        this.baseOutDir = baseOutDir;
    }

    /**
     * @return The output directory.
     */
    public File getOutDir() {
        return this.outDir;
    }

    /**
     * @param outDir The new output directory.
     */
    public void setOutDir(File outDir) {
        this.outDir = outDir;
    }

    /**
     * Generate the attributes needed to extract all the metadata.
     *
     * @return The current object.
     * @throws NoSuchFileException If an error occurs while getting the path to the output directory.
     */
    public Core generateExtractors() throws NoSuchFileException {
        try {
            this.metaDotXML = new File(outDir.getCanonicalPath() + File.separator + META);
            this.contentDotXML = new File(outDir.getCanonicalPath() + File.separator + CONTENT);
        } catch (IOException e) {
            throw new NoSuchFileException("Une erreur est survenue dans la construction du chemin d'acces absolu au dossier de sortie !");
        }
        this.parsedMeta = new ParsedFile(metaDotXML);
        this.parsedContent = new ParsedFile(contentDotXML);
        this.metaExtractor = new MetadataExtractor(parsedMeta);
        this.contentExtractor = new MetadataExtractor(parsedContent);
        return this;
    }

    /**
     * Generate the files and attributes to perform an extraction.
     *
     * @param mc The mode chose in the CLI. It contains all the parameters, accessible by getters.
     * @return The current object.
     * @throws NullPointerException If no odt file was set before.
     * @throws IOException If an I/O error occurs.
     */
    public Core generate(ModeChooser mc) throws NullPointerException, IOException {
        if (mc.getDfo() != null) {
            setOdtWPath(mc.getDfo().getOdt());
            outOdt = new File(odt.getCanonicalPath());
            if (this.odt == null) {
                throw new NullPointerException("An .odt file must be set before continuing !");
            } else {
                setOutDir(new File(this.baseOutDir.getCanonicalPath() + File.separator + odt.getName().substring(0, odt.getName().length() - 3)));
                ZipManager.unzip(odt, outDir);
                generateExtractors();
                thumbnail = new File(outDir + File.separator + THUMBNAILS_FOLDER + File.separator + THUMBNAIL_IMAGE);
                picturesDir = new File(outDir + File.separator + PICTURES_FOLDER);
            }
        } else if (mc.getDdo() != null) {
            System.err.println("Generator for directory content printer not yet implemented !");
        }
        return this;
    }

    /**
     * @return The odt file.
     */
    public File getOdt() {
        return this.odt;
    }

    /**
     * @param odt The new odt file.
     */
    public void setOdt(File odt) {
        this.odt = odt;
    }

    /**
     * Sets the odt with his path.
     *
     * @param path The path to the odt file.
     * @return The current object.
     * @throws IOException If an I/O error occurs.
     */
    public Core setOdtWPath(String path) throws IOException {
        if (FileManager.isODT(path)) {
            setOdt(new File(path));
        } else {
            throw new NoSuchFileException(path, null, "Ce n'est pas un fichier .odt !");
        }
        return this;
    }
}