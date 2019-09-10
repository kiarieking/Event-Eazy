package com.example.myapplication.ui.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.models.EventModel;
import com.example.myapplication.models.EventRecyclerAdapter;
import com.example.myapplication.networking.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayEventFragmet extends Fragment {


    public DisplayEventFragmet() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getEvents();
    }



    private RecyclerView eventRecyclerView;
    private ArrayList<EventModel> mArrayList = new ArrayList<>();
    private List<EventModel> mList = new ArrayList<>();
    EventRecyclerAdapter eventRecyclerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_event_fragmet,
                container, false);
        eventRecyclerView = view.findViewById(R.id.event_recyclerveiew);
        eventRecyclerView.hasFixedSize();
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventRecyclerAdapter = new EventRecyclerAdapter(getActivity(),mArrayList);
        eventRecyclerView.setAdapter(eventRecyclerAdapter);
        return view;
    }

    private void getEvents() {
        Call<ArrayList<EventModel>> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .getAllEvents();

        call.enqueue(new Callback<ArrayList<EventModel>>() {
            @Override
            public void onResponse(Call<ArrayList<EventModel>> call, Response<ArrayList<EventModel>> response) {
                if(response.code() == 200)
                {
                    mArrayList.clear();
                    mArrayList.addAll(response.body());
                    eventRecyclerAdapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(getActivity(),
                            "Error : "+response.code(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<EventModel>> call, Throwable t) {
                if(t instanceof IOException)
                {
                    Toast.makeText(getActivity(), "network failure", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "on failure method called",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
