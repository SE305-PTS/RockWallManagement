package PTS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        patronReport(patrons, "Patron Report at " + getTime() + ".csv");
    }

    public static void patronReport(List<Patron> patrons, String filename) {
        StringBuilder contents = new StringBuilder();
        contents.append(" ID,First Name,Last Name,Gender,Email,Subscriber,Belay Certified,Lead Certified,Suspension\n");
        for(Patron i : patrons) {
            contents.append(i.toString());
        }
        writeFile(filename, contents.toString().replaceAll("null", ""));
    }

    public static void inventoryReport(List<Item> items) {
        StringBuilder contents = new StringBuilder();
        contents.append(" ID,Type,Retire Date,Price\n");
        for(Item i : items) {
            contents.append(i.toString());
        }
        writeFile("Inventory Report at " + getTime() + ".csv", contents.toString().replaceAll("null", ""));
    }

    public static void sessionReport(String startDate, String endDate) {
        List<Session> allSessions = SessionTableDAO.selectByDate(startDate, endDate);
        StringBuilder contents = new StringBuilder();
        contents.append(" ID,Check-In,Check-Out,Patron ID,First Name,Last Name,Gender,Email,Subscriber,Belay Certified,Lead Certified,Suspension\n");
        for(Session i : allSessions) {
            contents.append(i.toStringNoPatron());
            contents.append(PatronTableDAO.getByID(i.getPatronID()).toString());
        }
        writeFile("Sessions Report at " + getTime() + ".csv", contents.toString().replaceAll("null", ""));

        List<Integer> allPatrons = new ArrayList<>();
        for(Session i : allSessions) {
            allPatrons.add(i.getPatronID());
        }
        List<Integer> distinctPatronIDs = allPatrons.stream().distinct().collect(Collectors.toList());
        List<Patron> distinctPatrons = new ArrayList<>();
        for(Integer i : distinctPatronIDs) {
            distinctPatrons.add(PatronTableDAO.getByID(i));
        }
        patronReport(distinctPatrons, "Unique Patrons in Session Range at " + getTime() + ".csv");
    }

    private static String getTime() {
        LocalDateTime time = LocalDateTime.now();
        String month;
        String day;
        String hour;
        String minute;
        String second;
        String suffix;
        if (time.getMonthValue() < 10) {
            month = "0" + time.getMonthValue();
        } else {
            month = "" + time.getMonthValue();
        }
        if (time.getDayOfMonth() < 10) {
            day = "0" + time.getDayOfMonth();
        } else {
            day = "" + time.getDayOfMonth();
        }
        if (time.getHour() < 10) {
            hour = "0" + time.getHour();
            suffix = "AM";
        } else {
            if (time.getHour() == 12) {
                hour = "" + time.getHour();
                suffix = "PM";
            } else if (time.getHour() > 12) {
                hour = "" + (time.getHour() - 12);
                suffix = "PM";
            } else {
                hour = "" + time.getHour();
                suffix = "AM";
            }
        }
        if (time.getMinute() < 10) {
            minute = "0" + time.getMinute();
        } else {
            minute = "" + time.getMinute();
        }
        if (time.getSecond() < 10) {
            second = "0" + time.getSecond();
        } else {
            second = "" + time.getSecond();
        }
        return "" + time.getYear() + "-" + month + "-" + day + " " + hour + "." + minute + "." + second + " " + suffix;
    }
}
