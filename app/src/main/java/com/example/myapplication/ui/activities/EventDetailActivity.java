package com.example.myapplication.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.Attendance;
import com.example.myapplication.models.EventModel;
import com.example.myapplication.models.EventRecyclerAdapter;
import com.example.myapplication.networking.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.core.os.LocaleListCompat.create;

public class EventDetailActivity extends AppCompatActivity {
    private ImageView mImageView;
    private TextView txEventName, txEventLocation, txEventDate,txEventShortDescription,
            txEventLongDescription;
    private Button btnAttendEvent, btnShareEvent;
    private EventModel eventModel;
    private int btn_controller = 1;
    int event;
    SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        mImageView = findViewById(R.id.img_event_detail);
        txEventName = findViewById(R.id.ed_eventname);
        txEventLocation = findViewById(R.id.ed_eventlocation);
        txEventDate = findViewById(R.id.ed_eventdatetime);
        txEventShortDescription = findViewById(R.id.event_short_description);
        txEventLongDescription = findViewById(R.id.event_long_description);
        btnAttendEvent = findViewById(R.id.btn_attend_event);
        btnShareEvent = findViewById(R.id.btn_share_event);
        mSharedPreferences = getApplicationContext().getSharedPreferences("myPref",0);

        Intent intent = getIntent();
        eventModel = intent.getParcelableExtra(EventRecyclerAdapter.CURRENT_POSITION);
        Glide.with(EventDetailActivity.this).load("http://192.168.1.5:8000"+eventModel.getEventimage())
                .into(mImageView);
        txEventName.setText(eventModel.getTitle());
        txEventDate.setText(eventModel.getDateTime());
        txEventLocation.setText(eventModel.getLocation());
        txEventShortDescription.setText(eventModel.getShortDescription());
        txEventLongDescription.setText(eventModel.getLongDescription());



        btnAttendEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = mSharedPreferences.getString("token",null);
                if (token==null)
                {
                    loginRegisterAlertDialog();
                }
                else
                {
                    attendOrCancelEvent();
                }

            }
        });
    }

    private void attendOrCancelEvent() {
        if (btn_controller == 1)
        {
            attendEvent();
            btnAttendEvent.setText("CANCEL EVENT");
            btn_controller = 2;
        }
        else if(btn_controller == 2)
        {
            cancelEvent();
            btnAttendEvent.setText("ATTEND EVENT");
            btn_controller = 1;
        }
    }

    private void loginRegisterAlertDialog()
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(
                EventDetailActivity.this).create();
        alertDialog.setTitle("Login");
        alertDialog.setMessage("you need to be logged in before uploading your event");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Login",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(
                                EventDetailActivity.this, LoginActivity.class));
                    }
                });


        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Register",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(EventDetailActivity.this, RegisterActivity.class));
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void cancelEvent() {
        Toast.makeText(this, "event canceled: "+String.valueOf(event),
                Toast.LENGTH_SHORT).show();

        Call<Attendance> call = RetrofitClient.getInstance(EventDetailActivity.this)
                .getApiConnector()
                .cancelEvent(event);

        call.enqueue(new Callback<Attendance>() {
            @Override
            public void onResponse(Call<Attendance> call, Response<Attendance> response) {

            }

            @Override
            public void onFailure(Call<Attendance> call, Throwable t) {

            }
        });

    }

    private void attendEvent() {
        event = eventModel.getId();
        int user = 1;
        Toast.makeText(this, String.valueOf(event), Toast.LENGTH_SHORT).show();
        Attendance attendance = new Attendance(user, event);

        Call<Attendance> call = RetrofitClient.getInstance(EventDetailActivity.this)
                .getApiConnector()
                .attendEvent(attendance);

        call.enqueue(new Callback<Attendance>() {
            @Override
            public void onResponse(Call<Attendance> call, Response<Attendance> response) {
                if(response.code() == 201)
                {
                    Toast.makeText(EventDetailActivity.this, "confirmed to attend event",
                            Toast.LENGTH_SHORT).show();
                    btnAttendEvent.setClickable(false);
                    btnAttendEvent.setBackgroundColor(getResources().getColor(R.color.colorGray));
                }
                else
                {
                    Toast.makeText(EventDetailActivity.this, "failed to confirm " +
                            "attendance :( Please try again later" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Attendance> call, Throwable t) {
                if (t instanceof IOException){
                    Toast.makeText(EventDetailActivity.this, "network failure!!",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(EventDetailActivity.this, "onFailure method called",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
