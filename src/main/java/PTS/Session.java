package PTS;

public class Session {
    private int sessionID;
    private int patronID;
    private String checkIn;
    private String checkOut;

    Session(int sessionID, int patronID, String checkIn, String checkOut) {
        this.sessionID = sessionID;
        this.patronID = patronID;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public int getPatronID() {
        return patronID;
    }

    public void setPatronID(int patronID) {
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
