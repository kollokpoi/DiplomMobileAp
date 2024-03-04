package com.example.diplommobileapp.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateWorker {
    public static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.parse(dateString);
    }
    public static Date parseSimpleDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.parse(dateString);
    }
    public static String getShortDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, HH:mm", Locale.getDefault());
        return sdf.format(date);
    }
    public static String getFullDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date);
    }
    public static String formatDate(String dateString){
        try{
            Date date = parseSimpleDate(dateString);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            return dateFormat.format(date);
        }catch(ParseException ex){
            return "";
        }
    }
}
