package cd.project.frontend.rest.entities;

public class BookingAvailabilityInput {
    private char beachId;
    private String date;
    private String fromTime;
    private String toTime;
    private int individuals;

    public BookingAvailabilityInput() {}

    public BookingAvailabilityInput(char beachId, String date, String fromTime, String toTime, int individuals) {
        this.beachId = beachId;
        this.date = date;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.individuals = individuals;
    }

    public char getBeachId() {
        return beachId;
    }

    public String getDate() {
        return date;
    }

    public String getFromTime() {
        return fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public int getIndividuals() {
        return individuals;
    }

    public void setBeachId(char beachId) {
        this.beachId = beachId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public void setIndividuals(int individuals) {
        this.individuals = individuals;
    }
}
