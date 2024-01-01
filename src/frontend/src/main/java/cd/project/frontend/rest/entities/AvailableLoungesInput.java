package cd.project.frontend.rest.entities;

import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "AvailableLoungesInput")
public class AvailableLoungesInput {
    private char beachId;
    String date;
    String fromTime;
    String toTime;

    public AvailableLoungesInput() {}

    public AvailableLoungesInput(char beachId, String date, String fromTime, String toTime) {
        this.beachId = beachId;
        this.date = date;
        this.fromTime = fromTime;
        this.toTime = toTime;
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
}
