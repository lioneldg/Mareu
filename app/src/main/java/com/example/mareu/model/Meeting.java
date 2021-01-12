package com.example.mareu.model;

import java.util.Calendar;
import java.util.List;
import java.util.Comparator;

public class Meeting{
    private Calendar calendar;
    private int location;               // 1 to 10
    private String subject;
    private List<String> participants;

    public Meeting(Calendar calendar, int location, String subject, List<String> participants){
        this.calendar = calendar;
        this.location = location;
        this.subject = subject;
        this.participants= participants;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
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

    public static Comparator<Meeting> meetingDateComparator = new Comparator<Meeting>() {
        public int compare(Meeting m1, Meeting m2) {
            long dateM1 = m1.getCalendar().getTimeInMillis();
            long dateM2 = m2.getCalendar().getTimeInMillis();
            if(dateM2 >= dateM1){
                return -1;
            }else{
                return 1;
            }
        }
    };

    public static Comparator<Meeting> meetingLocationComparator = new Comparator<Meeting>() {
        public int compare(Meeting m1, Meeting m2) {
            int locationM1 = m1.getLocation();
            int locationM2 = m2.getLocation();
            if(locationM2 >= locationM1){
                return -1;
            }else{
                return 1;
            }
        }
    };
}
