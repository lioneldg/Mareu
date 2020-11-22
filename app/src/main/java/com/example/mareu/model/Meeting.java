package com.example.mareu.model;

import java.sql.Timestamp;
import java.util.List;

public class Meeting {
    private long id;
    private Timestamp time;
    private int location;               // 1 to 10
    private String subject;
    private List<String> participants;

    public Meeting(long id, Timestamp time, int location, String subject, List<String> participants){
        this.id = id;
        this.time = time;
        this.location = location;
        this.subject = subject;
        this.participants= participants;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }
}
