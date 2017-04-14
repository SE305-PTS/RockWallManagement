package PTS;


public class Item {
    private int ID;
    private String type;
    private String retireDate;
    private Double price;
    private String notes;

    Item() {
        this.type = null;
        this.retireDate = null;
        this.price = null;
        this.notes = null;
    }

    Item(int ID, String type, String retireDate, Double price, String notes) {
        this.ID = ID;
        this.type = type;
        this.retireDate = retireDate;
        this.price = price;
        this.notes = notes;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
