package com.example.diplommobileapp.data.models.event;

import com.example.diplommobileapp.data.models.division.Division;
import com.example.diplommobileapp.data.models.measure.Measure;
import com.example.diplommobileapp.data.models.news.News;
import com.example.diplommobileapp.data.models.organization.Organization;
import com.example.diplommobileapp.services.DateWorker;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Event {
    private int id;
    @SerializedName("priviewImage")
    private String previewImageBase64;
    private String name;
    private String description;
    private String dateOfStart;
    private String dateOfEnd;
    private boolean publicEvent;
    private boolean readyToShow;
    private boolean divisionsExist;
    private List<Organization> organizations;
    private List<Measure> measures;
    private List<Division> divisions;
    private List<News> news;
    private String mimeType;




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
    public boolean isPublicEvent() {
        return publicEvent;
    }
    public boolean isReadyToShow() {
        return readyToShow;
    }
    public boolean isDivisionsExist() {
        return divisionsExist;
    }
    public List<Organization> getOrganizations() {
        return organizations;
    }
    public List<Measure> getMeasures() {
        return measures;
    }
    public List<Division> getDivisions() {
        return divisions;
    }
    public List<News> getNews() {
        return news;
    }
    public String getMimeType() {
        return mimeType;
    }
    public int getEventMembers() {
        int result = 0;

        for (Division item : divisions) {
            result += item.getDivisionMembers().size();
        }

        return result;
    }

}


