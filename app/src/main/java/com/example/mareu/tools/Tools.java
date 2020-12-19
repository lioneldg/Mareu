package com.example.mareu.tools;

public class Tools {
    public static boolean validateEmail(String email){
        return email.matches("(\\w){2,20}@(\\w){2,20}(\\.)[a-z]{1,4}");
    }
}
