package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class MeetingApiService implements InterfaceMeetingApiService{
    private List<Meeting> meetings = new ArrayList<Meeting>();
    private int day = -1;
    private int month = -1;
    private int year = -1;
    private int hour = -1;
    private int minute = -1;

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    @Override
    public void setDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public void setTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }
}
