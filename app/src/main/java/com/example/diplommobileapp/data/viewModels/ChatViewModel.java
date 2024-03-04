package com.example.diplommobileapp.data.viewModels;

import com.google.gson.annotations.SerializedName;

import java.util.Base64;

public class ChatViewModel {
    private int id;
    @SerializedName("previewImage")
    private String imageString;
    private String name;
    private String lastMessage;
    private String lastMessageTime;
    private int notCheckedCount;



    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getNotCheckedCount() {
        return notCheckedCount;
    }
    public String getLastMessageTime() {
        return lastMessageTime;
    }
    public String getLastMessage() {
        return lastMessage;
    }
    public byte[] getPreviewImage() {
        if (imageString!=null){
            return  Base64.getDecoder().decode(imageString);
        }
        return null;
    }
}
