package PTS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PatronTableDAO {
    private static final Logger log = LoggerFactory.getLogger(PatronTableDAO.class);

    public static void add(Patron pat) {
        String query = "INSERT INTO Patron(id,firstname,lastname,gender,email,subscriber,belaycert,leadcert,suspension) VALUES ("
        +pat.getID()
        +", \""+pat.getFirstName()+"\""
        +", \""+pat.getLastName()+"\""
        +", \""+pat.getGender()+"\""
        +", \""+pat.getEmailAddress()+"\""
        +", "+DBInterface.BoolToString(pat.getEmailOptIn())
        +", "+DBInterface.BoolToString(pat.getBelayCertified())
        +", "+DBInterface.BoolToString(pat.getLeadCertified())
        +", \""+pat.getSuspended()+"\""
        +")";
        log.info("Running query: "+query);
        DBInterface.executeUpdate(query.toString());
    }

    public static void add(Patron patrons[]) {
        for(int i = 0; i < patrons.length; i++) {
            add(patrons[i]);
        }
    }
}
