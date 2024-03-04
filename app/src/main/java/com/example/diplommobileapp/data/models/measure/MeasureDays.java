package com.example.diplommobileapp.data.models.measure;

import java.time.LocalTime;

public class MeasureDays {
    private int id;
    private String timeSpan;
    private int dayNumber;
    private int measureDivisionsInfoId;
    private MeasureDivisionsInfo measureDivisionsInfo;
    private String place;


    //getters
    public int getId() {
        return id;
    }
    public String getTimeSpan() {
        return timeSpan;
    }
    public int getDayNumber() {
        return dayNumber;
    }
    public int getMeasureDivisionsInfoId() {
        return measureDivisionsInfoId;
    }
    public MeasureDivisionsInfo getMeasureDivisionsInfo() {
        return measureDivisionsInfo;
    }
    public String getPlace() {
        return place;
    }
}
