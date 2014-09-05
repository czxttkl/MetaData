package io.metadata.download;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import io.metadata.misc.Globals;
import io.metadata.misc.Logger;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Xml Parser for SIGGRAPH. Xmls for SIGGRAPH are available in data/siggraph.
 * 
 * @author Zhengxing Chen
 * 
 */
public class SIGGRAPH {

    public static final String VENUE = "SIGGRAPH";

    public static void main(String[] args) throws XMLStreamException, IOException {
        // Initialize logger
        Logger mLogger = new Logger("logSIGGRAPH", true);

        String tagContent = "";
        Paper paper = new Paper();
        String keywords = "";
        String authors = "";
        String url = "";
        long year = 0;

        ArrayList<Paper> papersList = new ArrayList<Paper>();

        for (File file : new File("data/siggraph").listFiles()) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(new FileReader(file));
            mLogger.appendLine(file.getName());

            while (reader.hasNext()) {
                int event = reader.next();
                switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if (reader.getLocalName().equals("article_rec")) {
                        mLogger.appendLine("new article");
                        paper = new Paper();
                        paper.setVenue(VENUE);
                        mLogger.appendLine("venue:" + VENUE);
                        keywords = "";
                        authors = "";
                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                    tagContent = reader.getText().trim();
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    // Trim the string
                    tagContent = tagContent.trim();
                    // Convert html encode to unicode
                    tagContent = StringEscapeUtils.unescapeHtml(tagContent);
                    // Remove html tags in tagcontent
                    tagContent = tagContent.replaceAll("\\<.*?>", "");

                    switch (reader.getLocalName()) {
                    // Only have year information for conference. Set year at the end of one article.
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
                            mLogger.appendLine("///////////////////////////////////////////////////////////////////////");
                            mLogger.appendLine(url);
                            mLogger.appendLine("///////////////////////////////////////////////////////////////////////");
                        }
                        break;
                    }

                    mLogger.appendLine("");
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
