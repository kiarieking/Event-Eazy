package com.example.myapplication.ui.fragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.models.EventModel;
import com.example.myapplication.networking.RetrofitClient;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadEventFragment extends Fragment {



    public UploadEventFragment() {
        // Required empty public constructor
    }

    public static final int GALLERY_REQUEST_CODE = 361;
    DatePickerDialog mPicker;
    EditText etName, etVenue, etTime, etEntrance;
    Button btnUploadEvent, btnSelectImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_event, container, false);
        etName = view.findViewById(R.id.eventname_input_text);
        etVenue = view.findViewById(R.id.eventlocation_input_text);
        etTime = view.findViewById(R.id.eventdatetime_input_text);
        etEntrance = view.findViewById(R.id.evententrance_input_text);
        btnUploadEvent = view.findViewById(R.id.button_upload_event);
        btnSelectImage = view.findViewById(R.id.button_select_image);

        etTime.setInputType(InputType.TYPE_NULL);
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                mPicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        etTime.setText(i2 + "/" + (i1+1) + "/" + i);
                    }
                },year,month,day);
                mPicker.show();
            }
        });

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFromGallery();
            }
        });

        btnUploadEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadEventDetails();
            }
        });

        return view;
    }

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK)
        {
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    Toast.makeText(getActivity(),imgDecodableString,Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    private void uploadEventDetails() {
        String name = etName.getText().toString().trim();
        String location = etVenue.getText().toString().trim();
        int entrance = Integer.parseInt(etEntrance.getText().toString().trim());
        String dateTime = etTime.getText().toString().trim();
        String imageUrl = "pictures/eventpicture";


        EventModel eventModel = new EventModel(name,location,imageUrl,entrance,dateTime);
        
        Call<EventModel> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .addYourEvent(eventModel);

        call.enqueue(new Callback<EventModel>() {
            @Override
            public void onResponse(Call<EventModel> call, Response<EventModel> response) {
                if(response.code() == 201)
                {
                    Toast.makeText(getActivity(),"Event successfuly posted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "failed to upload event", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventModel> call, Throwable t) {
                if(t instanceof IOException)
                {
                    Toast.makeText(getActivity(), "network failure", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "onfailure method called", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
