package io.metadata.misc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Self-implemented logger for logging crawling information.
 * @author Zhengxing Chen
 */
public class Logger {

    PrintWriter pw;
    
    @SuppressWarnings("unused")
    private Logger() {
        
    }
    
    /**
     * @param filePath
     * @param append Whether the log should be appended to the end of the log file.
     * @throws IOException
     */
    public Logger(String filePath, boolean append) throws IOException {
        pw = new PrintWriter(new FileWriter(filePath, append), true);
    }
    
    /**
     * Append the error to the log file and also display it to the console
     * @param msg
     */
    public void appendErrMsg(String msg) {
        appendErrMsg(msg, true);
    }
    
    public void appendErrMsg(String msg, boolean toConsole) {
        appendLine("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\", toConsole);
        appendLine(msg, toConsole);
        appendLine("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\", toConsole);
    }
    
    /**
     * @param line
     * @param toConsole If true, the line to be appended is also displayed in the console of Java
     */
    public void appendLine(String line, boolean toConsole) {
        pw.println(line);
        if (toConsole) {
            System.out.println(line);
        }
    }
    
    /**
     * @param line
     * append the line to the log and also display the line to the console
     */
    public void appendLine(String line) {
        appendLine(line, true);
    }
    
    /** append lines to the log and also display the lines to the console*/
    public void appendLines(String... lines) {
        for (String line : lines) {
            appendLine(line);
        }
    }
    
    public void close() {
        pw.close();
    }
}
