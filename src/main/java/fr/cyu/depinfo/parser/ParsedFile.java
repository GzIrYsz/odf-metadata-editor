package fr.cyu.depinfo.parser;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ParsedFile {
    Document doc;

    public ParsedFile(File f) {
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();

            doc = builder.parse(f);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Document getDoc() {
        return this.doc;
    }

    public String getTitle() {
        return doc.getElementsByTagName("dc:title").item(0).getTextContent();
    }
    public void getStats() {
        NamedNodeMap nnm = doc.getElementsByTagName("meta:document-statistic").item(0).getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            System.out.println(nnm.item(i).getNodeName() + ": " + nnm.item(i).getNodeValue());
        }
    }

    public void setTitle(String title) {
        doc.getElementsByTagName("dc:title").item(0).setTextContent(title);
    }

    public static void main(String[] args) {
        File meta = new File("D:\\Users\\Thomas REMY\\Etudes\\odf-metadata-editor\\src\\main\\resources\\out\\meta.xml");
        ParsedFile parsedMeta = new ParsedFile(meta);
        System.out.println(parsedMeta.getTitle());
////        System.out.println(parsedMeta.getStats());
        parsedMeta.setTitle("Remets un titre");
        System.out.println(parsedMeta.getTitle());
        parsedMeta.getStats();
    }
}
