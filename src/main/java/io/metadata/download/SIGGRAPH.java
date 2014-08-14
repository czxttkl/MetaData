package io.metadata.download;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SIGGRAPH {

    
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {
                boolean titleField;
                boolean abstractField;
                String lastEEString = "";

                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    // System.out.println("Start Element :" + qName);
                    if (qName.equalsIgnoreCase("title")) {
                        titleField = true;
                    }
                    
                    if (qName.equalsIgnoreCase("abstract")) {
                        abstractField = true;
                    }
                }

                public void endElement(String uri, String localName, String qName) throws SAXException {
                    // System.out.println("End Element :" + qName);
                }

                public void characters(char[] ch, int start, int length) throws SAXException {
                    if (abstractField) {
                        String abstractName = new String(ch, start, length);
                        System.err.println(abstractName);
                        abstractField = false;
                    }
                    
                    if (titleField) {
                        String titleName = new String(ch, start, length);
                        System.out.println(titleName);
                        titleField = false;
                    }
                }
            };
            
            for (File file : new File("data/siggraph").listFiles()) {
                saxParser.getXMLReader().setFeature("http://xml.org/sax/features/external-general-entities", false);
                saxParser.getXMLReader().setFeature("http://xml.org/sax/features/validation", false);
                
                saxParser.parse(file, handler);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
