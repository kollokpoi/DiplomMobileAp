package com.example.diplommobileapp.data.models.news;

public class Section {
    private int id;
    private String title;
    private String description;
    private byte[] image;
    private News news;


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
    public byte[] getImage() {
        return image;
    }
    public News getNews() {
        return news;
    }
}
