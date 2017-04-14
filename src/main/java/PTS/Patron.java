package PTS;

public class Patron {
    private int ID;
    private String firstName;
    private String lastName;
    private String gender;
    private String emailAddress;
    private boolean emailOptIn;
    private boolean belayCertified;
    private boolean leadCertified;
    private String suspended;

    Patron() {
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.emailAddress = null;
        this.emailOptIn = false;
        this.belayCertified = false;
        this.leadCertified = false;
        this.suspended = "";
    }

    Patron(int ID, String firstName, String lastName, String gender, String emailAddress, boolean optIn) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.emailAddress = emailAddress;
        this.emailOptIn = optIn;
        this.belayCertified = false;
        this.leadCertified = false;
        this.suspended = "";
    }

    Patron(int ID, String firstName, String lastName) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = null;
        this.emailAddress = null;
        this.emailOptIn = false;
        this.belayCertified = false;
        this.leadCertified = false;
        this.suspended = "";
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean getEmailOptIn() {
        return emailOptIn;
    }

    public void setEmailOptIn(boolean emailOptIn) {
        this.emailOptIn = emailOptIn;
    }

    public boolean getBelayCertified() {
        return belayCertified;
    }

    public void setBelayCertified(boolean belayCertified) {
        this.belayCertified = belayCertified;
    }

    public boolean getLeadCertified() {
        return leadCertified;
    }

    public void setLeadCertified(boolean leadCertified) {
        this.leadCertified = leadCertified;
    }

    public String getSuspended() {
        return suspended;
    }

    public void setSuspended(String suspended) {
        this.suspended = suspended;
    }

    public String toString() {
        String str = Integer.toString(ID)+","+firstName+","+lastName+","+gender+","+emailAddress+",";
        str = str+Boolean.toString(emailOptIn)+","+Boolean.toString(belayCertified)+","+Boolean.toString(leadCertified)+",";
        str = str+suspended+"\n";
        return str;
    }
}
