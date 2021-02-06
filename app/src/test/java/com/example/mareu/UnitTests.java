package com.example.mareu;

import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.InterfaceMeetingApiService;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import static org.junit.Assert.*;

public class UnitTests {
    private final InterfaceMeetingApiService service;
    private List<Meeting> meetingList, filteredMeetingList;
    private final Meeting meeting1;
    private final Meeting meeting2;
    private final Meeting meeting3;

    public UnitTests(){
        service = DI.getMeetingApiService();
        ArrayList<String> participants = new ArrayList<>(Arrays.asList("lioneldegan@gmail.com", "lionel.degans@shadline.com"));
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021, 1, 2, 15, 0);
        meeting1 = new Meeting(calendar, 1, "Sujet du meeting", participants);
        calendar = Calendar.getInstance();
        calendar.set(2021, 1, 1, 15, 0);
        meeting2 = new Meeting(calendar, 1, "Sujet du meeting", participants);
        meeting3 = new Meeting(calendar, 2, "Sujet du meeting", participants);
    }

    @Test
    public void add_a_meeting() {
        meetingList = service.getMeetings();
        assertFalse(meetingList.contains(meeting1));
        service.createMeeting(meeting1);
        assertTrue(meetingList.contains(meeting1));
    }

    @Test
    public void delete_a_meeting() {
        meetingList = service.getMeetings();
        service.createMeeting(meeting1);
        assertTrue(meetingList.contains(meeting1));
        service.deleteMeeting(meeting1);
        assertFalse(meetingList.contains(meeting1));
    }

    @Test
    public void sort_by_date() {
        service.clearMeetings();
        meetingList = service.getMeetings();
        //création de 2 réunions rangées par ordre décroissant
        service.createMeeting(meeting1);
        service.createMeeting(meeting2);
        //valide que les réunions ne sont pas rangées par ordre croissant
        Meeting before = meetingList.get(1);
        Meeting after = meetingList.get(0);
        assertTrue(before.getCalendar().before(after.getCalendar()));
        //valide que les réunions sont rangées par ordre croissant
        service.sortByDate();
        before = meetingList.get(0);
        after = meetingList.get(1);
        assertTrue(before.getCalendar().before(after.getCalendar()));
    }

    @Test
    public void filter_by_date() {
        service.clearMeetings();
        meetingList = service.getMeetings();
        filteredMeetingList = service.getFilteredMeetings();
        //création de 2 réunions
        service.createMeeting(meeting1);
        service.createMeeting(meeting2);
        //teste si les 2 réunions sont présentes
        assertEquals(meetingList.size(), 2);
        service.initFilterByDate(2021,1,1);
        //vérifie que le filtre ait conservé seulement meeting2
        assertTrue(filteredMeetingList.contains(meeting2));
        assertEquals(filteredMeetingList.size(),1);
    }

    @Test
    public void filter_by_location() {
        service.clearMeetings();
        meetingList = service.getMeetings();
        filteredMeetingList = service.getFilteredMeetings();
        //création de 2 réunions
        service.createMeeting(meeting2);
        service.createMeeting(meeting3);
        //teste si les 2 réunions sont présentes
        assertEquals(meetingList.size(), 2);
        service.initFilterByLocation(2);
        //vérifie que le filtre ait conservé seulement meeting3
        assertTrue(filteredMeetingList.contains(meeting3));
        assertEquals(filteredMeetingList.size(),1);
    }

    @Test
    public void filter_both_date_and_location() {
        service.clearMeetings();
        //création de 3 réunions
        service.createMeeting(meeting1);
        service.createMeeting(meeting2);
        service.createMeeting(meeting3);
        meetingList = service.getMeetings();
        //teste si les 3 réunions sont présentes
        assertEquals(meetingList.size(), 3);
        service.initFilterByDate(2021,1,1);
        service.initFilterByLocation(2);
        filteredMeetingList = service.getFilteredMeetings();
        //vérifie que le filtre ait conservé seulement meeting3
        assertTrue(filteredMeetingList.contains(meeting3));
        assertEquals(filteredMeetingList.size(),1);
    }

    @Test
    public void filter_by_date_and_add_a_meeting() {
        service.clearMeetings();
        meetingList = service.getMeetings();
        filteredMeetingList = service.getFilteredMeetings();
        //création de 2 réunions
        service.createMeeting(meeting1);
        service.createMeeting(meeting2);
        //teste si les 2 réunions sont présentes
        assertEquals(meetingList.size(), 2);
        service.initFilterByDate(2021,1,1);
        //vérifie que le filtre ait conservé seulement meeting2
        assertTrue(filteredMeetingList.contains(meeting2));
        assertEquals(filteredMeetingList.size(),1);
        //ajouter une réunion qui passe le filtre et vérifier sa présence dans les 2 listes
        assertFalse(meetingList.contains(meeting3));
        assertFalse(filteredMeetingList.contains(meeting3));
        service.createMeeting(meeting3);
        service.filterByDate();
        filteredMeetingList = service.getFilteredMeetings();
        assertTrue(meetingList.contains(meeting3));
        assertTrue(filteredMeetingList.contains(meeting3));
        assertEquals(meetingList.size(),3);
        assertEquals(filteredMeetingList.size(),2);
    }

    @Test
    public void filter_by_location_and_add_a_meeting() {
        service.clearMeetings();
        meetingList = service.getMeetings();
        filteredMeetingList = service.getFilteredMeetings();
        //création de 2 réunions
        service.createMeeting(meeting2);
        service.createMeeting(meeting3);
        //teste si les 2 réunions sont présentes
        assertEquals(meetingList.size(), 2);
        service.initFilterByLocation(1);
        //vérifie que le filtre ait conservé seulement meeting2
        assertTrue(filteredMeetingList.contains(meeting2));
        assertEquals(filteredMeetingList.size(),1);
        //ajouter une réunion qui passe le filtre et vérifier sa présence dans les 2 listes
        assertFalse(meetingList.contains(meeting1));
        assertFalse(filteredMeetingList.contains(meeting1));
        service.createMeeting(meeting1);
        service.filterByLocation();
        filteredMeetingList = service.getFilteredMeetings();
        assertTrue(meetingList.contains(meeting1));
        assertTrue(filteredMeetingList.contains(meeting1));
        assertEquals(meetingList.size(),3);
        assertEquals(filteredMeetingList.size(),2);
    }

    @Test
    public void filter_both_date_and_location_and_add_a_meeting() {
        service.clearMeetings();
        //création de 2 réunions
        service.createMeeting(meeting1);
        service.createMeeting(meeting2);
        meetingList = service.getMeetings();
        //teste si les 2 réunions sont présentes
        assertEquals(meetingList.size(), 2);
        service.initFilterByDate(2021,1,1);
        service.initFilterByLocation(2);
        filteredMeetingList = service.getFilteredMeetings();
        //vérifie que le filtre n'ait conservé aucun meeting
        assertEquals(filteredMeetingList.size(),0);
        //ajouter une réunion qui passe le filtre et vérifier sa présence dans les 2 listes
        assertFalse(meetingList.contains(meeting3));
        assertFalse(filteredMeetingList.contains(meeting3));
        service.createMeeting(meeting3);
        service.filterBoth();
        filteredMeetingList = service.getFilteredMeetings();
        assertTrue(meetingList.contains(meeting3));
        assertTrue(filteredMeetingList.contains(meeting3));
        assertEquals(meetingList.size(),3);
        assertEquals(filteredMeetingList.size(),1);
    }

    @Test
    public void filter_by_date_and_delete_a_meeting() {
        service.clearMeetings();
        meetingList = service.getMeetings();
        filteredMeetingList = service.getFilteredMeetings();
        //création de 2 réunions
        service.createMeeting(meeting1);
        service.createMeeting(meeting2);
        //teste si les 2 réunions sont présentes
        assertEquals(meetingList.size(), 2);
        service.initFilterByDate(2021,1,1);
        //vérifie que le filtre ait conservé seulement meeting2
        assertTrue(filteredMeetingList.contains(meeting2));
        assertEquals(filteredMeetingList.size(),1);
        //supprimer une réunion qui passe le filtre et vérifier son abscence dans les 2 listes
        service.deleteMeeting(meeting2);
        service.filterByDate();
        filteredMeetingList = service.getFilteredMeetings();
        assertFalse(meetingList.contains(meeting2));
        assertFalse(filteredMeetingList.contains(meeting2));
        assertEquals(meetingList.size(),1);
        assertEquals(filteredMeetingList.size(),0);
    }

    @Test
    public void filter_by_location_and_delete_a_meeting() {
        service.clearMeetings();
        meetingList = service.getMeetings();
        filteredMeetingList = service.getFilteredMeetings();
        //création de 2 réunions
        service.createMeeting(meeting2);
        service.createMeeting(meeting3);
        //teste si les 2 réunions sont présentes
        assertEquals(meetingList.size(), 2);
        service.initFilterByLocation(1);
        //vérifie que le filtre ait conservé seulement meeting2
        assertTrue(filteredMeetingList.contains(meeting2));
        assertEquals(filteredMeetingList.size(),1);
        //supprimer une réunion qui passe le filtre et vérifier son abscence dans les 2 listes
        service.deleteMeeting(meeting2);
        service.filterByLocation();
        filteredMeetingList = service.getFilteredMeetings();
        assertFalse(meetingList.contains(meeting2));
        assertFalse(filteredMeetingList.contains(meeting2));
        assertEquals(meetingList.size(),1);
        assertEquals(filteredMeetingList.size(),0);
    }

    @Test
    public void filter_both_date_and_location_and_delete_a_meeting() {
        service.clearMeetings();
        //création de 2 réunions
        service.createMeeting(meeting1);
        service.createMeeting(meeting2);
        service.createMeeting(meeting3);
        meetingList = service.getMeetings();
        //teste si les 3 réunions sont présentes
        assertEquals(meetingList.size(), 3);
        service.initFilterByDate(2021,1,1);
        service.initFilterByLocation(2);
        filteredMeetingList = service.getFilteredMeetings();
        //vérifie que le filtre ait conservé seulement meeting3
        assertTrue(filteredMeetingList.contains(meeting3));
        assertEquals(filteredMeetingList.size(),1);
        //supprimer une réunion qui passe le filtre et vérifier son abscence dans les 2 listes
        service.deleteMeeting(meeting3);
        service.filterBoth();
        filteredMeetingList = service.getFilteredMeetings();
        assertFalse(meetingList.contains(meeting3));
        assertFalse(filteredMeetingList.contains(meeting3));
        assertEquals(meetingList.size(),2);
        assertEquals(filteredMeetingList.size(),0);
    }
}