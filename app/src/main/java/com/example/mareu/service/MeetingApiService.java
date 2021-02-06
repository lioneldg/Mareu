package com.example.mareu.service;

import com.example.mareu.model.Meeting;
import com.example.mareu.tools.Tools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MeetingApiService implements InterfaceMeetingApiService{
    private List<Meeting> meetings = new ArrayList<>();
    private List<Meeting> filteredMeetings = new ArrayList<>();
    public enum EnumFilterType{NONE, LOCATION, DATE, BOTH}

    private EnumFilterType filterType = EnumFilterType.NONE;
    private int yearFiltered;
    private int monthOfYearFiltered;
    private int dayOfMonthFiltered;
    private int locationFiltered;

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public List<Meeting> getFilteredMeetings() {
        return filteredMeetings;
    }

    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
        if(filterType != EnumFilterType.NONE){
            redoFilter();
        }
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
        filteredMeetings.remove(meeting);
    }

    @Override
    public void clearMeetings() {
        meetings = new ArrayList<>();
        filteredMeetings = new ArrayList<>();
        setFilterType(EnumFilterType.NONE);
    }

    @Override
    public void sortByDate() {
        if(meetings.size() > 1) {
            Collections.sort(meetings, Meeting.meetingDateComparator);
        }
    }

    @Override
    public void initFilterByDate(int year, int monthOfYear, int dayOfMonth) {
        this.yearFiltered = year;
        this.monthOfYearFiltered = monthOfYear;
        this.dayOfMonthFiltered = dayOfMonth;
        if(filterType == EnumFilterType.BOTH){
            filterBoth();
        } else {
            filterByDate();
        }
    }

    @Override
    public void filterByDate() {
        if(filteredMeetings.size() > 0){
            filteredMeetings = new ArrayList<>();
        }
        for (Meeting meeting: meetings) {
            boolean isSameYear = meeting.getCalendar().get(Calendar.YEAR) == this.yearFiltered;
            boolean isSameMonth = meeting.getCalendar().get(Calendar.MONTH) == this.monthOfYearFiltered;
            boolean isSameDay = meeting.getCalendar().get(Calendar.DAY_OF_MONTH) == this.dayOfMonthFiltered;
            if(isSameYear && isSameMonth && isSameDay){
                filteredMeetings.add(meeting);
            }
        }
    }

    @Override
    public void initFilterByLocation(int location) {
        this.locationFiltered = location;
        if(filterType == EnumFilterType.BOTH){
            filterBoth();
        } else {
            filterByLocation();
        }
    }

    @Override
    public void filterByLocation() {
        if(filteredMeetings.size() > 0){
            filteredMeetings = new ArrayList<>();
        }
        for (Meeting meeting: meetings) {
            if(meeting.getLocation() == this.locationFiltered){
                filteredMeetings.add(meeting);
            }
        }
    }

    @Override
    public void filterBoth() {
        if(filteredMeetings.size() > 0){
            filteredMeetings = new ArrayList<>();
        }
        for (Meeting meeting: meetings) {
            boolean isSameYear = meeting.getCalendar().get(Calendar.YEAR) == this.yearFiltered;
            boolean isSameMonth = meeting.getCalendar().get(Calendar.MONTH) == this.monthOfYearFiltered;
            boolean isSameDay = meeting.getCalendar().get(Calendar.DAY_OF_MONTH) == this.dayOfMonthFiltered;
            boolean isSameLocation = meeting.getLocation() == this.locationFiltered;
            if(isSameYear && isSameMonth && isSameDay && isSameLocation){
                filteredMeetings.add(meeting);
            }
        }
    }

    @Override
    public void redoFilter() {
        switch (filterType){
            case BOTH:
                filterBoth();
                break;
            case DATE:
                filterByDate();
            case LOCATION:
                filterByLocation();
        }
    }

    @Override
    public Boolean testMeetingAvailability(Calendar calendar, int rdvRoom) {
        long rdvTimeMinutes = Tools.millisToMinutes(calendar.getTimeInMillis());
        Boolean available = true;
        for (Meeting meeting: meetings) {
            //une réunion dure en moyenne 45 min la salle est bloquée 44 min avant et jusqu'à 44 minutes après l'heure de début. On peut valider une réunion 46 minutes avant ou après
            int roomMeeting = meeting.getLocation();
            long startTimeMinutes = Tools.millisToMinutes(meeting.getCalendar().getTimeInMillis());
            long beforeTimeMinutes = startTimeMinutes - 44;
            long endTimeMinutes = startTimeMinutes + 44;

            if(rdvTimeMinutes >= beforeTimeMinutes && rdvTimeMinutes <= endTimeMinutes && roomMeeting == rdvRoom){
                available = false;
            }
        }
        return available;
    }


    @Override
    public EnumFilterType getFilterType() {
        return filterType;
    }

    @Override
    public void setFilterType(EnumFilterType type) {
        this.filterType = type;
    }
}
