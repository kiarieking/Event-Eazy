package com.example.myapplication.models;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.ui.activities.EventDetailActivity;

import java.util.ArrayList;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder> {
    Context mContext;
    public final static String CURRENT_POSITION = "com.example.myapplication.models";
    private ArrayList<EventModel> mArrayList;
    public EventRecyclerAdapter(Context context, ArrayList<EventModel> arrayList) {
        mContext = context;
        mArrayList = arrayList;
    }

    @NonNull
    @Override
    public EventRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_event, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventModel eventModel = mArrayList.get(position);
        holder.eventName.setText(eventModel.getTitle());
        holder.eventLocation.setText(eventModel.getLocation());
        holder.eventTime.setText(eventModel.getDateTime());
        Glide.with(mContext).load("http://192.168.1.8:8000"+eventModel.getEventimage())
                .into(holder.eventImage);
        holder.current_position = position;
    }



    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView eventImage;
        TextView eventName,eventLocation,eventTime;
        public int current_position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventImage = itemView.findViewById(R.id.event_image);
            eventName = itemView.findViewById(R.id.event_name);
            eventLocation = itemView.findViewById(R.id.event_location);
            eventTime = itemView.findViewById(R.id.event_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, EventDetailActivity.class);
                    intent.putExtra(CURRENT_POSITION,mArrayList.get(current_position));
                    mContext.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }
}
