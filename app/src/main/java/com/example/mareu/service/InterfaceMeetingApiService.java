package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.List;

public interface InterfaceMeetingApiService {
    List<Meeting> getMeetings();

    List<Meeting> getFilteredMeetings();

    void createMeeting(Meeting meeting);

    void deleteMeeting(Meeting meeting);

    void clearMeetings();

    void sortByDate();

    void initFilterByDate(int year, int monthOfYear, int dayOfMonth);

    void filterByDate();

    void initFilterByLocation(int location);

    void filterByLocation();

    void filterBoth();

    void redoFilter();

    MeetingApiService.EnumFilterType getFilterType();

    void setFilterType(MeetingApiService.EnumFilterType type);

}
