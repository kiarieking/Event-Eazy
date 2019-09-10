package com.example.myapplication.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventModel implements Parcelable {

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
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("long_description")
    @Expose
    private String longDescription;

    public EventModel(String title, String location, String eventimage, Integer entrance,
                      String dateTime, String shortDescription, String longDescription) {
        this.title = title;
        this.location = location;
        this.eventimage = eventimage;
        this.entrance = entrance;
        this.dateTime = dateTime;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
    }

    protected EventModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
        location = in.readString();
        eventimage = in.readString();
        if (in.readByte() == 0) {
            entrance = null;
        } else {
            entrance = in.readInt();
        }
        dateTime = in.readString();
        shortDescription = in.readString();
        longDescription = in.readString();
    }

    public static final Creator<EventModel> CREATOR = new Creator<EventModel>() {
        @Override
        public EventModel createFromParcel(Parcel in) {
            return new EventModel(in);
        }

        @Override
        public EventModel[] newArray(int size) {
            return new EventModel[size];
        }
    };

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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(title);
        parcel.writeString(location);
        parcel.writeString(eventimage);
        if (entrance == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(entrance);
        }
        parcel.writeString(dateTime);
        parcel.writeString(shortDescription);
        parcel.writeString(longDescription);
    }
}
