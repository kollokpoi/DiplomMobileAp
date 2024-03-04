package com.example.diplommobileapp.data.viewModels;

public class MessageViewModel {
    private int id;
    private boolean selfSender;
    private String content;
    private String sendDateTime;
    private String senderName;
    private String senderImage;

    public int getId() {
        return id;
    }
    public String getContent() {
        return content;
    }
    public boolean isSelfSender() {
        return selfSender;
    }
    public String getSendDateTime() {
        return sendDateTime;
    }
    public String getSenderImage() {
        return senderImage;
    }
    public String getSenderName() {
        return senderName;
    }



    public void setSelfSender(boolean selfSender) {
        this.selfSender = selfSender;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setSendDateTime(String sendDateTime) {
        this.sendDateTime = sendDateTime;
    }
    public void setSenderImage(String senderImage) {
        this.senderImage = senderImage;
    }
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
