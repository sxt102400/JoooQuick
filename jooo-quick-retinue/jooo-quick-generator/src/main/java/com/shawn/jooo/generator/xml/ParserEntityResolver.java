package com.shawn.jooo.generator.xml;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author shawn
 */
public class ParserEntityResolver implements EntityResolver {

    public ParserEntityResolver() {
    }

    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        InputStream is;
        InputSource ins;
        if ("-//rats.org//DTD Lu Generator Configuration 1.0//EN".equalsIgnoreCase(publicId)) {
            is = this.getClass().getClassLoader().getResourceAsStream("generator_1_0.dtd");
            ins = new InputSource(is);
            return ins;
        } else {
            return null;
        }
    }
}
