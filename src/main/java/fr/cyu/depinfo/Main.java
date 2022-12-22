package fr.cyu.depinfo;

import fr.cyu.depinfo.xmlprocessor.*;
import fr.cyu.depinfo.filemanager.*;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        File zipFile = new File("src/main/resources/odt_test_file.odt");
        File outDir = new File("src/main/resources/out");
        try {
            ZipManager.unzip(zipFile, outDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File metaXML = new File("src/main/resources/out/meta.xml");
        File contentXML = new File("src/main/resources/out/content.xml");
        ParsedFile parsedMeta = new ParsedFile(metaXML);
        MetadataExtractor meta = new MetadataExtractor(parsedMeta);
        ParsedFile parsedContent = new ParsedFile(contentXML);
        MetadataExtractor content = new MetadataExtractor(parsedContent);
        System.out.println(content.getTextContentByTagName(MetadataExtractor.HYPERLINK, MetadataExtractor.HYPERLINK_TARGET, true));
        meta.setTextContentByTagName(MetadataExtractor.AUTHOR, "Thomas");
        System.out.println(meta.getMainMeta());
        try {
            parsedMeta.save(metaXML);
            ZipManager.zipDir(new File ("src/main/resources/out"), new File("src/main/resources/NewOdt.odt"));
            FileManager.deleteDir(outDir, outDir.listFiles());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}