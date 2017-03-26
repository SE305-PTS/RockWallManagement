package PTS;


public class Item {
    private String ID;
    private String type;
    private String retireDate;
    private String purchaseDate;
    private String notes;

    Item(String ID, String type, String retireDate, String purchaseDate, String notes) {
        this.ID = ID;
        this.type = type;
        this.retireDate = retireDate;
        this.purchaseDate = purchaseDate;
        this.notes = notes;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRetireDate() {
        return retireDate;
    }

    public void setRetireDate(String retireDate) {
        this.retireDate = retireDate;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
