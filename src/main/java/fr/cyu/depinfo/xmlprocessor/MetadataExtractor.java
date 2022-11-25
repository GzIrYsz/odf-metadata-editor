package fr.cyu.depinfo.xmlprocessor;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class contains methods to get and set metadata from a parsed XML file.
 *
 * @author Thomas REMY
 */
public class MetadataExtractor {
    public static final String TITLE = "dc:title";
    public static final String DESCRIPTION = "dc:description";
    public static final String SUBJECT = "dc:subject";
    public static final String AUTHOR = "meta:initial-author";
    public static final String CREATION_DATE = "meta:creation-date";
    public static final String STATISTICS = "meta:document-statistic";

    private Document parsedXML;

    public MetadataExtractor(ParsedFile f) {
        parsedXML = f.getDoc();
    }

    /**
     * Returns the title of the document.
     *
     * @return The title of the document.
     */
    public String getTitle() {
        return parsedXML.getElementsByTagName("dc:title").item(0).getTextContent();
    }

    /**
     * Change the title of the document.
     *
     * @param title The new title of the document.
     */
    public void setTitle(String title) {
        parsedXML.getElementsByTagName("dc:title").item(0).setTextContent(title);
    }

    /**
     * Returns the creation date and time of the document.
     *
     * @return The creation date and time of the document.
     */
    public String getCreationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime creationDate = LocalDateTime.parse(parsedXML.getElementsByTagName("meta:creation-date").item(0).getTextContent());
        return creationDate.format(formatter);
    }

    /**
     * Returns the author of the document.
     *
     * @return The author of the document.
     */
    public String getAuthor() {
        return parsedXML.getElementsByTagName("meta:initial-creator").item(0).getTextContent();
    }

    /**
     * Change the author of the document.
     *
     * @param author The new author of the document.
     */
    public void setAuthor(String author) {
        parsedXML.getElementsByTagName("meta:initial-creator").item(0).setTextContent(author);
    }

    /**
     * Returns all the stats of the document.
     *
     * @return The stats of the document.
     */
    public String getStats() {
        NamedNodeMap nnm = parsedXML.getElementsByTagName("meta:document-statistic").item(0).getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            System.out.println(nnm.item(i).getNodeName() + ": " + nnm.item(i).getNodeValue());
        }
        return "";
    }
}
