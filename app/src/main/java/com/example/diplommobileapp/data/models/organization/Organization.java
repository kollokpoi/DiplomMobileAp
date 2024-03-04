package com.example.diplommobileapp.data.models.organization;

import com.example.diplommobileapp.data.models.division.Division;
import com.example.diplommobileapp.data.models.event.Event;
import com.google.gson.annotations.SerializedName;

import java.util.Base64;
import java.util.List;

public class Organization {
    private int id;
    private String name;
    private String email;
    private String description;
    private boolean readyToShow = false;
    @SerializedName("preview")
    private String previewImageBase64;
    private List<Event> events;
    private String mimeType;


    //getters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getDescription() {
        return description;
    }
    public boolean isReadyToShow() {
        return readyToShow;
    }
    public byte[] getPreview() {
        if (previewImageBase64!=null){
            return  Base64.getDecoder().decode(previewImageBase64);
        }
        return null;
    }
    public String getMimeType() {
        return mimeType;
    }
    public List<Event> getEvents() {
        return events;
    }
}
