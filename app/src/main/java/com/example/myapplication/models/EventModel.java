package com.example.myapplication.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("eventimage")
    @Expose
    private String eventimage;
    @SerializedName("entrance")
    @Expose
    private Integer entrance;
    @SerializedName("date_time")
    @Expose
    private String dateTime;

    public EventModel(String title, String location, String eventimage, Integer entrance, String dateTime) {
        this.title = title;
        this.location = location;
        this.eventimage = eventimage;
        this.entrance = entrance;
        this.dateTime = dateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEventimage() {
        return eventimage;
    }

    public void setEventimage(String eventimage) {
        this.eventimage = eventimage;
    }

    public Integer getEntrance() {
        return entrance;
    }

    public void setEntrance(Integer entrance) {
        this.entrance = entrance;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
