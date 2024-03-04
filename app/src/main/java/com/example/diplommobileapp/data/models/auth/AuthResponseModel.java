package com.example.diplommobileapp.data.models.auth;

import com.google.gson.annotations.SerializedName;

public class AuthResponseModel {
    @SerializedName("timeToNextRequest")
    private String timeToNextRequest;

    public String getTimeToNextRequest() {
        return timeToNextRequest;
    }
}
