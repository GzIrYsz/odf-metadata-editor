package fr.cyu.depinfo;

import fr.cyu.depinfo.xmlprocessor.*;
import fr.cyu.depinfo.filemanager.*;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        File zipFile = new File("src/main/resources/Modele_odt_projet.odt");
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
            ZipManager.zip(new File ("src/main/resources/out"), new File("src/main/resources/NewOdt.odt"));
            FileManager.deleteDir(outDir, outDir.listFiles());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}