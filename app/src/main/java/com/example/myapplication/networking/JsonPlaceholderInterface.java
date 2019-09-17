package com.example.myapplication.networking;

import com.example.myapplication.models.Attendance;
import com.example.myapplication.models.EventModel;
import com.example.myapplication.models.LoginUser;
import com.example.myapplication.models.RegisterUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceholderInterface {
    @POST("eventeazy_api/addevent/")
    Call<EventModel> addYourEvent(@Body EventModel eventModel);

    @GET("eventeazy_api/getevent")
    Call<ArrayList<EventModel>> getAllEvents();

    @POST("eventeazy_api/attendevent/")
    Call<Attendance> attendEvent(@Body Attendance attendance);

    @POST("eventeazy_api/cancelEvent/{event_id}")
    Call<Attendance> cancelEvent(@Path("event_id ") int event_id);

    @GET("eventeazy_api/scheduledevent/{user}")
    Call<ArrayList<EventModel>> scheduledEvent(@Path("user") int user);

    @POST("eventeazy_api/registeruser/")
    Call<RegisterUser> registerUser(@Body RegisterUser user);

    @POST("eventeazy_api/loginuser/")
    Call<LoginUser> loginUser(@Body LoginUser user);
}
