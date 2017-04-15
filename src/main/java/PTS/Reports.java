package PTS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Reports {
    private static final Logger log = LoggerFactory.getLogger(Reports.class);

    public static void writeFile(String filename, String contents) {
        try {
            Path file = DBInterface.getExportPath(filename);
            BufferedWriter writer = Files.newBufferedWriter(file, Charset.defaultCharset());
            writer.write(contents);
            writer.close();
        }
        catch(IOException e) {
            log.error("Could not export"+filename+": ", e);
        }
    }

    public static void patronReport(List<Patron> patrons) {
        List<String> lines = new ArrayList<>();
        StringBuilder contents = new StringBuilder();
        for(Patron i : patrons) {
            lines.add(i.toString());
        }
        contents.append(" ID,First Name,Last Name,Gender,Email,Subscriber,Belay Certified,Lead Certified,Suspension\n");
        for(String i : lines) {
            contents.append(i);
        }
        writeFile("Patron.csv", contents.toString());
    }

    public static void inventoryReport(List<Item> items) {
        List<String> lines = new ArrayList<>();
        StringBuilder contents = new StringBuilder();
        for(Item i : items) {
            lines.add(i.toString());
        }
        contents.append(" ID,Type,Retire Date,Price\n");
        for(String i : lines) {
            contents.append(i);
        }
        writeFile("Inventory.csv", contents.toString());
    }
}
