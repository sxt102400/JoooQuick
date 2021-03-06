package com.shawn.jooo.generator.api;

import com.shawn.jooo.generator.config.Configuration;
import com.shawn.jooo.generator.xml.ParserEntityResolver;
import com.shawn.jooo.generator.xml.ParserErrorHandler;
import com.shawn.jooo.generator.config.PropertyHolder;
import com.shawn.jooo.generator.exception.XMLParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author shawn
 */
public class ConfigurationParser extends PropertyHolder {

    private List<String> warnings;
    private List<String> errors;

    public Configuration parse(File inputFile) throws IOException,
            XMLParserException {
        FileReader fr = new FileReader(inputFile);
        InputSource is = new InputSource(fr);

        return parse(is);
    }

    public Configuration parse(InputStream inputStream) throws XMLParserException {
        InputSource is = new InputSource(inputStream);
        return this.parse(is);
    }

    public Configuration parse(InputSource inputSource) throws XMLParserException {
        warnings = new ArrayList<>();
        errors = new ArrayList<>();
        Configuration config = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            ParserErrorHandler handler = new ParserErrorHandler(this.warnings, this.errors);
            builder.setEntityResolver(new ParserEntityResolver());
            builder.setErrorHandler(handler);
            Document document = builder.parse(inputSource);
            Element rootNode = document.getDocumentElement();
            config = this.parseXmlConfiguration(rootNode);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new XMLParserException(this.errors);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }


    private Configuration parseXmlConfiguration(Element rootNode)
            throws XMLParserException {
        XmlConfigurationParser parser = new XmlConfigurationParser();
        return parser.parse(rootNode);
    }


}
