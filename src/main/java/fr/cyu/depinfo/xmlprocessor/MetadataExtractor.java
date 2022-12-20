package fr.cyu.depinfo.xmlprocessor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class contains methods to get and set metadata from a parsed XML file.
 *
 * @author Thomas REMY
 */
public class MetadataExtractor {

    /**
     * The separator used to print the multiple hyperlinks.
     */
    public static String SEPARATOR = ", ";

    /**
     *
     */
    public static final String META = "office:meta";

    /**
     * The title tag.
     */
    public static final String TITLE = "dc:title";

    /**
     * The description tag.
     */
    public static final String DESCRIPTION = "dc:description";

    /**
     * The subject tag.
     */
    public static final String SUBJECT = "dc:subject";

    /**
     * The keyword tag.
     */
    public static final String KEYWORD = "meta:keyword";

    /**
     * The initial creator tag.
     */
    public static final String AUTHOR = "meta:initial-creator";

    /**
     * The creation date tag.
     */
    public static final String CREATION_DATE = "meta:creation-date";

    /**
     * The document statistic tag.
     */
    public static final String STATISTICS = "meta:document-statistic";

    /**
     * The table count tag.
     */
    public static final String NB_TABLES = "meta:table-count";

    /**
     * The image count tag.
     */
    public static final String NB_IMAGES = "meta:image-count";

    /**
     * The page count tag.
     */
    public static final String NB_PAGES = "meta:page-count";

    /**
     * The paragraph count tag.
     */
    public static final String NB_PARAGRAPHS = "meta:paragraph-count";

    /**
     * The word count tag.
     */
    public static final String NB_WORDS = "meta:word-count";

    /**
     * The character count tag.
     */
    public static final String NB_CHARACTERS = "meta:character-count";

    /**
     * The non whitespace character count tag.
     */
    public static final String NB_NON_WHITESPACE_CHARACTERS = "meta:non-whitespace-character-count";

    /**
     * The hyperlink tag.
     */
    public static final String HYPERLINK = "text:a";

    /**
     * The hyperlink target tag.
     */
    public static final String HYPERLINK_TARGET = "xlink:href";

    private Document parsedXML;

    /**
     * Constructs a new MetadataExtractor from a ParsedFile
     *
     * @param f The ParsedFile
     * @see ParsedFile
     */
    public MetadataExtractor(ParsedFile f) {
        parsedXML = f.getDoc();
    }

    /**
     * Returns the first element in the document with the given tag name.
     *
     * @param elementTagName The name of the element.
     * @return The element if found or {@code null}.
     */
    public Element getFirstElementByTagName(String elementTagName) {
        return (Element) parsedXML.getElementsByTagName(elementTagName).item(0);
    }

    /**
     * Returns a string containing all the text content of the given tag.
     *
     * @param elementTagName The tag name.
     * @return A string containing all the text content of the tag given in parameter.a
     */
    public String getTextContentByTagName(String elementTagName) {
        return this.getTextContentByTagName(elementTagName, null, false);
    }
    public String getTextContentByTagName(String elementTagName, String attributeName, boolean isAttribute) {
        NodeList nl = parsedXML.getElementsByTagName(elementTagName);
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < nl.getLength(); i++) {
            Element element = (Element) nl.item(i);
            if (!isAttribute) {
                if (element != null) {
                    output.append(element.getTextContent());
                }
            } else {
                if (element.hasAttribute(attributeName)) {
                    output.append(element.getAttribute(attributeName));
                }
            }
            if ((nl.getLength() > 1) && (i < (nl.getLength() - 1))) {
                output.append(SEPARATOR);
            }
        }
        return output.toString();
    }

    /**
     * Modify the content of the given node.
     *
     * @param elementTagName The tag name of the element to modify.
     * @param newTextContent The new content of the node.
     * @return The current object.
     */
    public MetadataExtractor setTextContentByTagName(String elementTagName, String newTextContent) {
        Element element = getFirstElementByTagName(elementTagName);
        if (element != null) {
            element.setTextContent(newTextContent);
        } else {
            Element meta = getFirstElementByTagName(META);
            Element newElement = parsedXML.createElement(elementTagName);
            newElement.setTextContent(newTextContent);
            meta.appendChild(newElement);
        }
        return this;
    }

    /**
     * Returns the title of the document.
     *
     * @return The title of the document.
     */
    public String getTitle() {
        return getTextContentByTagName(TITLE);
    }

    /**
     * Changes the title of the document.
     *
     * @param title The new title of the document.
     */
    public MetadataExtractor setTitle(String title) {
        return setTextContentByTagName(TITLE, title);
    }

    /**
     * Returns the creation date and time of the document.
     *
     * @return The creation date and time of the document.
     */
    public String getCreationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime creationDate = LocalDateTime.parse(getTextContentByTagName(CREATION_DATE));
        return creationDate.format(formatter);
    }

    /**
     * Returns the description of the document.
     *
     * @return The description of the document.
     */

    public String getDescription() {
        return getTextContentByTagName(DESCRIPTION);
    }


    /**
     * Changes the description of the document.
     *
     * @param description The new description of the document.
     */
    public MetadataExtractor setDescription(String description) {
        return setTextContentByTagName(DESCRIPTION, description);
    }

    /**
     * Returns the subject of the document.
     *
     * @return The subject of the document.
     */
    public String getSubject() {
        return getTextContentByTagName(SUBJECT);
    }

    /**
     * Changes the subject of the document.
     *
     * @param subject The new subject of the document.
     */
    public MetadataExtractor setSubject(String subject) {
        return setTextContentByTagName(SUBJECT, subject);
    }

    /**
     * Returns the author of the document.
     *
     * @return The author of the document.
     */
    public String getAuthor() {
        return getTextContentByTagName(AUTHOR);
    }

    /**
     * Changes the author of the document.
     *
     * @param author The new author of the document.
     */
    public MetadataExtractor setAuthor(String author) {
        return setTextContentByTagName(AUTHOR, author);
    }

    public String getNbTables() {
        return getTextContentByTagName(STATISTICS, NB_TABLES, true);
    }

    public String getNbImages() {
        return getTextContentByTagName(STATISTICS, NB_IMAGES, true);
    }

    public String getNbPages() {
        return getTextContentByTagName(STATISTICS, NB_PAGES, true);
    }

    public String getNbParagraphs() {
        return getTextContentByTagName(STATISTICS, NB_PARAGRAPHS, true);
    }

    public String getNbWords() {
        return getTextContentByTagName(STATISTICS, NB_WORDS, true);
    }

    public String getNbCharacters() {
        return getTextContentByTagName(STATISTICS, NB_CHARACTERS, true);
    }

    public String getNbNonWhitespaceCharacters() {
        return getTextContentByTagName(STATISTICS, NB_NON_WHITESPACE_CHARACTERS, true);
    }

    public String getHyperlinks() {
        return getTextContentByTagName(HYPERLINK, HYPERLINK_TARGET, true);
    }

    public String getMainMeta() {
        StringBuilder output = new StringBuilder();
        output.append("\nTitre : ").append(getTitle());
        output.append("\nAuteur : ").append(getAuthor());
        output.append("\nDate de creation : ").append(getCreationDate());
        output.append("\nSujet : ").append(getSubject());
        output.append("\nDescription : ").append(getDescription());
        output.append("\nMots-cles : ").append(getTextContentByTagName(KEYWORD));
        return output.toString();
    }

    /**
     * Returns all the statistics of the document.
     *
     * @return The statistics of the document.
     */
    public String getStatsMeta() {
        StringBuilder output = new StringBuilder();
        output.append("Nombre de tableaux : ").append(getNbTables());
        output.append("Nombre d'images : ").append(getNbImages());
        output.append("Nombre de pages : ").append(getNbPages());
        output.append("Nombre de paragraphes : ").append(getNbParagraphs());
        output.append("Nombre de mots : ").append(getNbWords());
        output.append("Nombre de caracteres : ").append(getNbCharacters());
        output.append("Nombre de caracteres non blanc : ").append(getNbNonWhitespaceCharacters());
        return output.toString();
    }
}
