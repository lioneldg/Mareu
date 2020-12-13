package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.List;

public interface InterfaceMeetingApiService {
    List<Meeting> getMeetings();

    void createMeeting(Meeting meeting);

    void deleteMeeting(Meeting meeting);

    void setDate(int day, int month, int year);

    void setTime(int hour, int minute);
}
