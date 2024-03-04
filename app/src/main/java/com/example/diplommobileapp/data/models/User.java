package com.example.diplommobileapp.data.models;

import static com.example.diplommobileapp.services.DateWorker.formatDate;

import androidx.annotation.NonNull;

import com.example.diplommobileapp.services.DateWorker;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

public class User {
    @SerializedName("image")
    private String imageBase64;
    private String name;
    private String secondName;
    private String lastName;
    private String phoneNumber;
    private String course;
    private String birthday;
    private boolean phoneUpdated = false;


    public String getName() {
        return name;
    }
    public String getSecondName() {
        return secondName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public Date getBirthDay() throws ParseException {
        if (birthday!=null){
            return DateWorker.parseDate(birthday);
        }
        else{
            return null;
        }
    }
    public String getCourse() {
        return course;
    }
    public boolean isPhoneUpdated() {
        return phoneUpdated;
    }
    public byte[] getImage() {
        if (imageBase64!=null){
            return  Base64.getDecoder().decode(imageBase64);
        }
        return null;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public boolean setBirthDay(String birthDay) {
        birthDay = formatDate(birthDay);
        if (birthDay!=""){
            this.birthday = birthDay;
            return true;
        }else return false;

    }
    public void setCourse(String course) {
        this.course = course;
    }
    public boolean setFullName(@NonNull String name){
        String[] parts = name.split(" ");
        if (parts.length==3){
            if (parts[0].length()>0 && parts[1].length()>0 && parts[2].length()>0){
                this.name = parts[0];
                secondName = parts[1];
                lastName = parts[2];
                return true;
            }else return false;
        }else return false;
    }
    public boolean setPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.replace(" ","").replace("(","").replace(")","").replace("-","").replace("+","");
        if (phoneNumber.length()==11){
            if (phoneNumber.charAt(0)=='7')
                phoneNumber.replaceFirst("7","8");
            if (phoneNumber.charAt(0)=='8'){
                for (int i = 0; i<phoneNumber.length();i++){
                    if (!Character.isDigit(phoneNumber.charAt(i)))
                        return false;
                }
                if (!this.phoneNumber.equalsIgnoreCase(phoneNumber))
                    phoneUpdated=true;
                this.phoneNumber=phoneNumber;
                return true;
            }else return false;
        }else return false;
    }
    public String getFullName(){
        return secondName + " " + name + " " + lastName;
    }
}
