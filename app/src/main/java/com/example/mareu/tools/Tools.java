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

    public static boolean participantsIsEmpty(ArrayList<String> participants){
        return participants.isEmpty();
    }

    public static boolean validateDate(int day, int month, int year){
            return day >= 1 && day <= 31 && month >= 1 && month <= 12 && year > 1999 && year < 2030;
    }

    public static boolean validateTime(int hour, int minute){
            return hour >= 0 && hour <= 24 && minute >= 0 && minute <= 59;
    }

    public static long millisToMinutes(long millis){
        double minDouble = millis/(1000*60);
        return Math.round(minDouble);
    }
}
