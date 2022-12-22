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

public class Core {
    public static String OUTPUT_BASE_DIRECTORY_PATH = System.getProperty("java.io.tmpdir") + File.separator + "odf-metadata-editor";

    public static String META = "meta.xml";
    public static String CONTENT = "content.xml";

    private File baseOutDir = new File(OUTPUT_BASE_DIRECTORY_PATH);
    private File outDir;
    private File odt;
    private File metaDotXML, contentDotXML;
    private ParsedFile parsedMeta, parsedContent;
    private MetadataExtractor metaExtractor, contentExtractor;

    public String getMainMetadata() {
        StringBuilder output = new StringBuilder();
        output.append("\nTitre : ").append(metaExtractor.getTitle()).append("\n");
        output.append("\nDescription :\n").append(metaExtractor.getDescription()).append("\n");
        output.append("\nSujet : ").append(metaExtractor.getSubject()).append("\n");
        output.append("\nMots-cles : ").append(metaExtractor.getKeywords()).append("\n");
        output.append("\nAuteur : ").append(metaExtractor.getAuthor()).append("\n");
        return output.toString();
    }

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
        output.append("Nombre de pages : ").append(metaExtractor.getNbPages()).append("\n");
        output.append("Nombre de paragraphes : ").append(metaExtractor.getNbParagraphs()).append("\n");
        output.append("Nombre de mots : ").append(metaExtractor.getNbWords()).append("\n");
        output.append("Nombre de caracteres : ").append(metaExtractor.getNbCharacters()).append("\n");
        output.append("Nombre de caracteres non blancs : ").append(metaExtractor.getNbNonWhitespaceCharacters()).append("\n");
        output.append("\nListe des liens hypertexte : ").append(contentExtractor.getHyperlinks()).append("\n");
        return output.toString();
    }

    public String getAllMetadata() {
        StringBuilder output = new StringBuilder();
        output.append("\n\n***** Metadonnees principales *****").append("\n");
        output.append(getMainMetadata());
        output.append("\n\n***** Metadonnees secondaires et statistiques *****");
        output.append(getStatisticsMetadata());
        return output.toString();
    }

    public File getBaseOutDir() {
        return this.baseOutDir;
    }

    public void setBaseOutDir(File baseOutDir) {
        this.baseOutDir = baseOutDir;
    }

    public File getOutDir() {
        return this.outDir;
    }

    public void setOutDir(File outDir) {
        this.outDir = outDir;
    }

    public Core generateExtractors() throws NoSuchFileException {
        try {
            this.metaDotXML = new File(outDir.getCanonicalPath() + File.separator + META);
            this.contentDotXML = new File(outDir.getCanonicalPath() + File.separator + CONTENT);
        } catch (IOException e) {
            throw new NoSuchFileException("Une erreur est survenue dans la construction du chemin d'acces absolu au fichier .odt !");
        }
        this.parsedMeta = new ParsedFile(metaDotXML);
        this.parsedContent = new ParsedFile(contentDotXML);
        this.metaExtractor = new MetadataExtractor(parsedMeta);
        this.contentExtractor = new MetadataExtractor(parsedContent);
        return this;
    }

    public Core generate(ModeChooser mc) throws NullPointerException, IOException {
        if (mc.getDfo() != null) {
            setOdtWPath(mc.getDfo().getOdt());
            if (this.odt == null) {
                throw new NullPointerException("An .odt file must be set before continuing !");
            } else {
                setOutDir(new File(this.baseOutDir.getCanonicalPath() + File.separator + odt.getName().substring(0, odt.getName().length() - 3)));
                ZipManager.unzip(odt, outDir);
                generateExtractors();
            }
        } else if (mc.getDdo() != null) {
            System.err.println("Generator for directory content printer not yet implemented !");
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
        } else {
            throw new NoSuchFileException(path, null, "Ce n'est pas un fichier .odt !");
        }
        return this;
    }
}