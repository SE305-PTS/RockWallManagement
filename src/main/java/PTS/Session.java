package PTS;

public class Session {
    private String sessionID;
    private String patronID;
    private String checkIn;
    private String checkOut;

    Session(String sessionID, String patronID, String checkIn, String checkOut) {
        this.sessionID = sessionID;
        this.patronID = patronID;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getPatronID() {
        return patronID;
    }

    public void setPatronID(String patronID) {
        this.patronID = patronID;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }
}
