package PTS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Reports {
    private static final Logger log = LoggerFactory.getLogger(Reports.class);

    public static void writeFile(Path dir, String contents) {
        try {
            BufferedWriter writer = Files.newBufferedWriter(dir, Charset.defaultCharset());
            writer.write(contents);
            writer.close();
        }
        catch(IOException e) {
            log.error("Could not write Export File: ", e);
        }
    }

    public static void patronReport(List<Patron> patrons) {
        List<String> lines = new ArrayList<>();
        StringBuilder contents = new StringBuilder();
        for(Patron i : patrons) {
            lines.add(i.toString());
        }
        for(String i : lines) {
            contents.append(i);
        }
        try {
            writeFile(DBInterface.getExportPath(), contents.toString());
        }
        catch(IOException e) {
            log.error("Could not export Patron file: ", e);
        }
    }

    public static void inventoryReport(List<Item> items) {
        List<String> lines = new ArrayList<>();
        StringBuilder contents = new StringBuilder();
        for(Item i : items) {
            lines.add(i.toString());
        }
        for(String i : lines) {
            contents.append(i);
        }
        try {
            writeFile(DBInterface.getExportPath(), contents.toString());
        }
        catch(IOException e) {
            log.error("Could not export Inventory file: ", e);
        }
    }
}
