package fr.cyu.depinfo.core;

import fr.cyu.depinfo.filemanager.*;
import fr.cyu.depinfo.xmlprocessor.*;

import java.io.File;

public class Core {
    public static String getMeta(String path) {
        return Core.getMeta(new File(path));
    }

    public static String getMeta(File f) {
        StringBuilder output = new StringBuilder("\n\n*** Metadonneees du fichier " + f.getName() + " ***\n\n");
        ParsedFile parsedMeta = new ParsedFile(f);
        MetadataExtractor meta = new MetadataExtractor(parsedMeta);
        output.append("\nTitre : ").append(meta.getTitle()).append("\n");
        output.append("\nAuteur : ").append(meta.getAuthor()).append("\n");
        output.append("\nSujet : ").append(meta.getSubject()).append("\n");
        output.append("\nDescription : ").append(meta.getDescription()).append("\n");
        output.append("\nMots-cles : ").append("\n");
        output.append("\nStatistiques : ").append(meta.getStats()).append("\n");
        output.append("\nDate de creation : ").append(meta.getCreationDate()).append("\n");
        return output.toString();
    }
}
