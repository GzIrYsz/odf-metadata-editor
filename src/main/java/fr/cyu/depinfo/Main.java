package fr.cyu.depinfo;

import fr.cyu.depinfo.xmlprocessor.*;
import fr.cyu.depinfo.filemanager.*;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        File zipFile = new File("src/main/resources/Test.odt");
        File outDir = new File("src/main/resources/out");
        try {
            ZipManager.unzip(zipFile, outDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File metaXML = new File("src/main/resources/out/meta.xml");
        File newMetaXML = new File("src/main/resources/out/meta.xml");
        ParsedFile parsedMeta = new ParsedFile(metaXML);
        MetadataExtractor meta = new MetadataExtractor(parsedMeta);
        System.out.println(meta.getTitle());
        meta.setTitle("NEW TITLE");
        try {
            parsedMeta.serialize(newMetaXML);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}