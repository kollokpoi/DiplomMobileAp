package com.example.diplommobileapp.data.models.division;

public class DivisionUsers {
    private int Id;
    private int DivisionId;
    private Division Division;
    private String UserId;
    private boolean DivisionDirector;


    //getters
    public int getId() {
        return Id;
    }
    public int getDivisionId() {
        return DivisionId;
    }
    public Division getDivision() {
        return Division;
    }
    public String getUserId() {
        return UserId;
    }
    public boolean isDivisionDirector() {
        return DivisionDirector;
    }
}
