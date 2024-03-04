package com.example.diplommobileapp.data.models.division;

import com.example.diplommobileapp.data.models.event.Event;
import com.example.diplommobileapp.data.models.measure.MeasureDivisionsInfo;
import com.example.diplommobileapp.services.DateWorker;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class Division {
    private int id;
    @SerializedName("previewImage")
    private String previewImageBase64;
    private String name;
    private String description;
    private String dateOfStart;
    private String dateOfEnd;
    private String placeName;
    private double longitude;
    private double latitude;
    private int eventId;
    private Event event;
    private List<MeasureDivisionsInfo> measureDivisionsInfos;
    private List<DivisionUsers> divisionMembers;


    public int getId() {
        return id;
    }

    public byte[] getPreviewImage() {
        if (previewImageBase64!=null){
            return  Base64.getDecoder().decode(previewImageBase64);
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateOfStart() throws ParseException {
        if (dateOfStart!=null){
            return DateWorker.parseDate(dateOfStart);
        }
        else{
            return null;
        }
    }
    public Date getDateOfEnd() throws ParseException {
        if (dateOfEnd!=null){
            return DateWorker.parseDate(dateOfEnd);
        }
        else{
            return null;
        }
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public String getPlaceName() {
        return placeName;
    }

    public int getEventId() {
        return eventId;
    }

    public Event getEvent() {
        return event;
    }

    public List<MeasureDivisionsInfo> getMeasureDivisionsInfos() {
        return measureDivisionsInfos;
    }

    public List<DivisionUsers> getDivisionMembers() {
        return divisionMembers;
    }


}
