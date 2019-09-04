package com.example.myapplication.networking;

import com.example.myapplication.models.EventModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JsonPlaceholderInterface {
    @POST("eventeazy_api/addevent/")
    Call<EventModel> addYourEvent(@Body EventModel eventModel);

}
