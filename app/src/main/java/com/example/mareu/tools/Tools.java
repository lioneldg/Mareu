package com.example.mareu.tools;

import java.util.ArrayList;

public class Tools {

    public static boolean validateEmail(String email){
        return email.matches("[\\w._\\-]{2,20}@(\\w){2,20}(\\.)[a-z]{1,4}");
    }

    public static String formatStringTime(int time){
        return time > 9 ? String.valueOf(time) : '0' + String.valueOf(time);
    }

    public static boolean textIsEmpty(String text){
        return !(text.length() > 0);
    }

    public static boolean participantsIsNotEmpty(ArrayList<String> participants){
        return !participants.isEmpty();
    }

    public static boolean validateDate(String dayStr, String monthStr, String yearStr){
        if(dayStr != null && monthStr != null && yearStr != null) {
            int day = Integer.parseInt(dayStr);
            int month = Integer.parseInt(monthStr);
            int year = Integer.parseInt(yearStr);
            return day > 0 && day < 32 && month > 0 && month < 13 && year > 2000 && year < 2030;
        }else{
            return false;
        }
    }
    public static boolean validateTime(String hourStr, String minuteStr){
        if(hourStr != null && minuteStr != null) {
            int hour = Integer.parseInt(hourStr);
            int minute = Integer.parseInt(minuteStr);
            return hour > -1 && hour < 25 && minute > -1 && minute < 61;
        }else{
            return false;
        }
    }
}
