package cd.project.frontend.soap.entities;

import cd.project.backend.domain.Lounge;

import java.util.ArrayList;

public class BookingSoap {
    private int id;
    private char beachID;
    private String date;
    private String fromTime;
    private String toTime;
    private String createdAt;
    private int userID;
    private ArrayList<Lounge> lounges;

    public BookingSoap() {}

    public BookingSoap(
            int id,
            char beachID,
            String date,
            String fromTime,
            String toTime,
            String createdAt,
            int userID,
            ArrayList<Lounge> lounges
    ) {
        this.id = id;
        this.beachID = beachID;
        this.date = date;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.createdAt = createdAt;
        this.userID = userID;
        this.lounges = lounges;
    }

    public int getId() {
        return id;
    }

    public char getBeachID() {
        return beachID;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public int getUserID() {
        return userID;
    }

    public ArrayList<Lounge> getLounges() {
        return lounges;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBeachID(char beachID) {
        this.beachID = beachID;
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

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setLounges(ArrayList<Lounge> lounges) {
        this.lounges = lounges;
    }
}
