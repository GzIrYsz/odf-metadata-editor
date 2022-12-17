package fr.cyu.depinfo.xmlprocessor;

import org.w3c.dom.*;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

/**
 * This class contains methods to get and set metadata from an XML file extracted from an ODT file.
 *
 * @author Thomas REMY
 */
public class ParsedFile {
    private Document doc;

    /**
     * Constructs a new ParsedFile from an XML file.
     *
     * @param f The XML file to parse.
     */
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

    /**
     * @return The parsed XML document.
     */
    public Document getDoc() {
        return this.doc;
    }

    /**
     * @param doc The modified parsed XML document.
     */
    public void setDoc(Document doc) {
        this.doc = doc;
    }

    /**
     * Saves a parsed DOM Document into an .xml file.
     *
     * @param out The output file.
     * @return The current object.
     * @throws ClassNotFoundException If there's an issue when instantiating the registry.
     * @throws InstantiationException If there's an issue when instantiating the registry.
     * @throws IllegalAccessException If there's an issue when instantiating the registry.
     * @throws IOException If there's an issue with the input/output operation.
     */
    public ParsedFile save(File out) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        DOMImplementationLS domImpl = (DOMImplementationLS) registry.getDOMImplementation("LS");
        LSSerializer ser = domImpl.createLSSerializer();
        LSOutput output = domImpl.createLSOutput();
        output.setEncoding("UTF-8");
        FileOutputStream fos = new FileOutputStream(out);
        output.setByteStream(fos);
        ser.write(doc, output);
        fos.close();
        return this;
    }
}
