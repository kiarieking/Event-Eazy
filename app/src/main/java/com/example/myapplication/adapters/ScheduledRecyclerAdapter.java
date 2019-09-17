package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.EventModel;
import com.example.myapplication.networking.RetrofitClient;

import java.util.ArrayList;

public class ScheduledRecyclerAdapter extends RecyclerView.Adapter<ScheduledRecyclerAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<EventModel> mArrayList;

    public ScheduledRecyclerAdapter(Context context, ArrayList<EventModel> arrayList) {
        mContext = context;
        mArrayList = arrayList;
    }

    @NonNull
    @Override
    public ScheduledRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventModel eventModel = mArrayList.get(position);
        holder.sEventName.setText(eventModel.getTitle());
        holder.sEventLocation.setText(eventModel.getTitle());
        holder.sEventDate.setText(eventModel.getDateTime());
        Glide.with(mContext).load("http://192.168.1.5:8000"+eventModel.getEventimage())
                .into(holder.sEventImage);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView sEventImage;
        TextView sEventName, sEventDate, sEventLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sEventImage = itemView.findViewById(R.id.event_image);
            sEventName = itemView.findViewById(R.id.event_name);
            sEventLocation = itemView.findViewById(R.id.event_location);
            sEventDate = itemView.findViewById(R.id.event_time);
        }
    }
}
