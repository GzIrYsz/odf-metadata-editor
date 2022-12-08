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
        return parsedXML.getElementsByTagName(TITLE).item(0).getTextContent();
    }

    /**
     * Changes the title of the document.
     *
     * @param title The new title of the document.
     */
    public void setTitle(String title) {
        parsedXML.getElementsByTagName(TITLE).item(0).setTextContent(title);
    }

    /**
     * Returns the creation date and time of the document.
     *
     * @return The creation date and time of the document.
     */
    public String getCreationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime creationDate = LocalDateTime.parse(parsedXML.getElementsByTagName(CREATION_DATE).item(0).getTextContent());
        return creationDate.format(formatter);
    }

    /**
     * Returns the description of the document.
     *
     * @return The description of the document.
     */
    public String getDescription() {
        return parsedXML.getElementsByTagName(DESCRIPTION).item(0).getTextContent();
    }


    /**
     * Changes the description of the document.
     *
     * @param description The new description of the document.
     */
    public void setDescription(String description) {
        parsedXML.getElementsByTagName(DESCRIPTION).item(0).setTextContent(description);
    }

    /**
     * Returns the subject of the document.
     *
     * @return The subject of the document.
     */
    public String getSubject() {
        return parsedXML.getElementsByTagName(SUBJECT).item(0).getTextContent();
    }

    /**
     * Changes the subject of the document.
     *
     * @param subject The new subject of the document.
     */
    public void setSubject(String subject) {
        parsedXML.getElementsByTagName(SUBJECT).item(0).setTextContent(subject);
    }

    /**
     * Returns the author of the document.
     *
     * @return The author of the document.
     */
    public String getAuthor() {
        return parsedXML.getElementsByTagName(AUTHOR).item(0).getTextContent();
    }

    /**
     * Changes the author of the document.
     *
     * @param author The new author of the document.
     */
    public void setAuthor(String author) {
        parsedXML.getElementsByTagName(AUTHOR).item(0).setTextContent(author);
    }

    /**
     * Returns all the stats of the document.
     *
     * @return The stats of the document.
     */
    public String getStats() {
        NamedNodeMap nnm = parsedXML.getElementsByTagName(STATISTICS).item(0).getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            System.out.println(nnm.item(i).getNodeName() + ": " + nnm.item(i).getNodeValue());
        }
        return "";
    }

    public void getInfo(String m) {
        System.out.println(parsedXML.getElementsByTagName(m).getLength());
    }
}
