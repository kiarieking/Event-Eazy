package com.example.myapplication.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapters.ScheduledRecyclerAdapter;
import com.example.myapplication.models.EventModel;
import com.example.myapplication.networking.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduledEventsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ScheduledRecyclerAdapter mAdapter;
    private ArrayList<EventModel> mArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_events);
        getScheduledEvents();
        mRecyclerView = findViewById(R.id.scheduled_recy);
        mRecyclerView.hasFixedSize();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ScheduledRecyclerAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void getScheduledEvents() {
        int user=1;
        Call<ArrayList<EventModel>> call = RetrofitClient.getInstance(this)
                .getApiConnector()
                .scheduledEvent(user);
        call.enqueue(new Callback<ArrayList<EventModel>>() {
            @Override
            public void onResponse(Call<ArrayList<EventModel>> call, Response<ArrayList<EventModel>> response) {
                if (response.code()==200)
                {
                    mArrayList.clear();
                    mArrayList.addAll(response.body());
                    mAdapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(ScheduledEventsActivity.this, "failed to get your events",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<EventModel>> call, Throwable t) {
                if(t instanceof IOException)
                {
                    Toast.makeText(ScheduledEventsActivity.this, "network failure",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ScheduledEventsActivity.this,
                            "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
