package com.example.diplommobileapp.data.models.news;

import com.example.diplommobileapp.data.models.event.Event;

import java.util.Date;
import java.util.List;

public class News {
    private int id;
    private String title;
    private String description;
    private Date dateTime;
    private String author;
    private byte[] image;
    private List<Section> sections;
    private Integer eventId;
    private Event event;
    private String mimeType;


    //getters
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Date getDateTime() {
        return dateTime;
    }
    public String getAuthor() {
        return author;
    }
    public byte[] getImage() {
        return image;
    }
    public List<Section> getSections() {
        return sections;
    }
    public Integer getEventId() {
        return eventId;
    }
    public Event getEvent() {
        return event;
    }
    public String getMimeType() {
        return mimeType;
    }
}
