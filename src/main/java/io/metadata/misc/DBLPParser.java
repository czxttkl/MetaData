package io.metadata.misc;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *  1. This file tries to parse xml file from dblp.xml and dblp.dtd. (The two files must be put under the same directory)
 *  dblp.xml: https://www.dropbox.com/s/51ehap7mhdo4xi4/dblp.xml
 *  dblp.dtd: https://www.dropbox.com/s/az2zfgyjljyapkp/dblp.dtd
 *  
 *  2. You must specify JVM parameters before running: -DentityExpansionLimit=10000000. 
 *  Otherwise, the following exception will be thrown:
 *  org.xml.sax.SAXParseException: The parser has encountered more than ¡°64000¡± entity expansions in this document
 *  
 *  3. Some links in dblp.xml are incomplete and invalid.
 * @author Zhengxing Chen
 */
public class DBLPParser {
    
    public static String dblpLocation = "dblp.xml";
    public static String confNameToDetect = "ACMace";
    
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {
                boolean crossrefField;
                boolean eeField;
                String lastEEString = "";

                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    // System.out.println("Start Element :" + qName);
                    if (qName.equalsIgnoreCase("crossref")) {
                        crossrefField = true;
                    }
                    
                    if (qName.equalsIgnoreCase("ee")) {
                        eeField = true;
                    }
                }

                public void endElement(String uri, String localName, String qName) throws SAXException {
                    // System.out.println("End Element :" + qName);
                }

                public void characters(char[] ch, int start, int length) throws SAXException {
                    if (eeField) {
                        lastEEString = new String(ch, start, length);
                        eeField = false;
                    }
                    
                    if (crossrefField) {
                        String confName = new String(ch, start, length);
                        if (confName.contains(confNameToDetect) && lastEEString.contains("dx.")) {
                            System.out.println(confName + ":" + lastEEString);
                        }
                        crossrefField = false;
                    }
                }
            };
            
            saxParser.parse(dblpLocation, handler);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }// main

}
