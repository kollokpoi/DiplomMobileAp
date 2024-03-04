package com.example.diplommobileapp.data.models.measure;

import com.example.diplommobileapp.data.models.division.Division;

import java.util.List;

public class MeasureDivisionsInfo {
    private int id;
    private boolean oneTime = true;
    private boolean weekDays = false;
    private String length;
    private String place = "";
    private boolean sameForAll = false;
    private Integer divisionId;
    private Division division;
    private int measureId;
    private Measure measure;
    private List<MeasureDates> measureDates;
    private List<MeasureDays> measureDays;

    public int getId() {
        return id;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public boolean isWeekDays() {
        return weekDays;
    }

    public String getLength() {
        return length;
    }

    public String getPlace() {
        return place;
    }

    public boolean isSameForAll() {
        return sameForAll;
    }

    public Integer getDivisionId() {
        return divisionId;
    }

    public Division getDivision() {
        return division;
    }

    public int getMeasureId() {
        return measureId;
    }

    public Measure getMeasure() {
        return measure;
    }

    public List<MeasureDates> getMeasureDates() {
        return measureDates;
    }

    public List<MeasureDays> getMeasureDays() {
        return measureDays;
    }
}
