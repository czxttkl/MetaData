package io.metadata.download.conference;

import io.metadata.misc.Globals;
import io.metadata.misc.Logger;
import io.metadata.misc.Utils;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Xml Parser for SIGGRAPH Sandbox. Xmls for SIGGRAPH Sandbox are available in data/siggraph-sandbox.
 * @author Zhengxing Chen
 *
 */
public class SIGGRAPHSandbox {

    public static final String VENUE = "Sandbox";

    public static void main(String[] args) throws XMLStreamException, IOException {
        // Initialize logger
        Logger mLogger = new Logger("logSandbox", true);
        
        String tagContent = "";
        Paper paper = new Paper();
        String keywords = "";
        String authors = "";
        String url = "";
        long year = 0;

        ArrayList<Paper> papersList = new ArrayList<Paper>();

        for (File file : new File("data/siggraph-sandbox").listFiles()) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(new FileReader(file));
            mLogger.appendLine(file.getName());

            while (reader.hasNext()) {
                
                int event = reader.next();
                
                switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    // find new article
                    if (reader.getLocalName().equals("article_rec")) {
                        mLogger.appendLine("");
                        // Initiates new paper instance
                        paper = new Paper().setVenue(VENUE).setVenueType(Globals.VENUE_TYPE_CONFERENCE);
                        keywords = "";
                        authors = "";
                        
                        mLogger.appendLine("new article");
                        mLogger.appendLine("venue:" + VENUE);
                    }
                    break;
                    
                case XMLStreamConstants.CHARACTERS:
                    tagContent = reader.getText().trim();
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    tagContent = Utils.trimHtmlString(tagContent);
                    
                    switch (reader.getLocalName()) {
                    // Only have year information for conference. Set year at the end of each article.
                    case "copyright_year":
                        year = Long.valueOf(tagContent);
                        mLogger.appendLine(tagContent);
                        break;
                    case "url":
                        url = tagContent;
                        break;
                    case "title":
                        paper.setTitle(tagContent);
                        mLogger.appendLine("title:" + tagContent);
                        break;
                    case "subtitle":
                        paper.setTitle(paper.getTitle() + " " + tagContent);
                        mLogger.appendLine("subtitle:" + tagContent);
                        break;
                    case "par":
                        paper.setAbstraction(tagContent);
                        mLogger.appendLine("abstract:" + tagContent);
                        break;
                    case "kw":
                        keywords = keywords + "," + tagContent;
                        break;
                    case "keywords":
                        // Remove the first ","
                        if (keywords.startsWith(",")) {
                            keywords = keywords.substring(1);
                        }
                        paper.setKeywords(keywords);
                        mLogger.appendLine("keyword:" + keywords);
                        break;
                    case "first_name":
                        authors = authors + tagContent;
                        break;
                    case "middle_name":
                        if (tagContent.equals("")) {
                            break;
                        }
                        authors = authors + " " + tagContent;
                        break;
                    case "last_name":
                        authors = authors + " " + tagContent + ",";
                        break;
                    case "authors":
                        authors = authors.substring(0, authors.length() - 1);
                        paper.setAuthors(authors);
                        mLogger.appendLine("authors:" + authors);
                        break;
                    case "ft_body":
                        // Use full article as abstraction if abstraction itself is not available.
                        if (paper.getAbstraction() == null) {
                            paper.setAbstraction(tagContent);
                            mLogger.appendLine("abstract_ft_body:" + tagContent);
                        }
                        break;
                    case "article_rec":
                        paper.setYear(year);
                        mLogger.appendLine("year:" + String.valueOf(year));
                        
                        // Validate papers. Only add papers with complete information
                        if (paper.validate()) {
                            papersList.add(paper);
                        } else {
                            mLogger.appendErrMsg(url);
                        }
                        break;
                    }

                    break;

                } // switch

            } // while
            
            mLogger.appendLine("");
            mLogger.appendLine("");
            mLogger.appendLine("");
            
        } // for traverse all files in data/siggraph folder

        mLogger.close();
        
        // Convert to array for bulk insert
        Paper[] papersArray = new Paper[papersList.size()];
        papersList.toArray(papersArray);
        
        // Initialize self-wrapped mongocollection
        MyMongoCollection<Paper> mPapersCollection = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_COLLECTION);

        mPapersCollection.insert(papersArray);
        
    } // main

}
