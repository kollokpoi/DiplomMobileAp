package com.example.diplommobileapp.data.models.measure;

import com.example.diplommobileapp.services.DateWorker;

import java.text.ParseException;
import java.util.Date;

public class MeasureDates {
    private int id;
    private String datetime;
    private int measureDivisionsInfosId;
    private MeasureDivisionsInfo measureDivisionsInfos;
    private String place;


    //getters
    public int getId() {
        return id;
    }
    public Date getDatetime() throws ParseException {
        if (datetime!=null){
            return DateWorker.parseDate(datetime);
        }
        else{
            return null;
        }
    }
    public int getMeasureDivisionsInfosId() {
        return measureDivisionsInfosId;
    }
    public MeasureDivisionsInfo getMeasureDivisionsInfos() {
        return measureDivisionsInfos;
    }
    public String getPlace() {
        return place;
    }
}
