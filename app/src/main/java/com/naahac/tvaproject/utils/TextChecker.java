package com.naahac.tvaproject.utils;

import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextChecker {

    public static boolean nameChecker(String text, int min, int max){
        if(text.length() < min || text.length() > max)
            return false;
        return true;
    }

    public static boolean emailChecker(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean dateChecker(String date){
        String regex = "\\d{1,2}\\.\\d{1,2}\\.\\d{4}";
        Matcher matcherObj = Pattern.compile(regex).matcher(date);
        return matcherObj.matches();
    }

    public static boolean birthdayChecker(String date) {
        Calendar c = Calendar.getInstance();
        final Calendar today = Calendar.getInstance();
        try {
            Date d = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(date);
            c.setTime(d);
        } catch (ParseException e) {
            return false;
        }
        return !(c.equals(today) && c.after(today));
    }

}
