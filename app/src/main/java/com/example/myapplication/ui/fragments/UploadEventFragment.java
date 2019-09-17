package com.example.myapplication.ui.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
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
import com.example.myapplication.ui.activities.LoginActivity;
import com.example.myapplication.ui.activities.RegisterActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadEventFragment extends Fragment {


    private Bitmap bm;

    public UploadEventFragment() {
        // Required empty public constructor
    }

    public static final int GALLERY_REQUEST_CODE = 361;
    DatePickerDialog mPicker;
    EditText etName, etVenue, etTime, etEntrance, etShortDescription, etLongDescription;
    Button btnUploadEvent, btnSelectImage;
    String imgDecodableString,imageString, encodedImage;
    Bitmap selectedImage;
    SharedPreferences mSharedPreferences;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_event, container, false);
        etName = view.findViewById(R.id.eventname_input_text);
        etVenue = view.findViewById(R.id.eventlocation_input_text);
        etTime = view.findViewById(R.id.eventdatetime_input_text);
        etEntrance = view.findViewById(R.id.evententrance_input_text);
        etShortDescription = view.findViewById(R.id.et_event_short_description);
        etLongDescription = view.findViewById(R.id.et_event_long_description);
        btnUploadEvent = view.findViewById(R.id.button_upload_event);
        btnSelectImage = view.findViewById(R.id.button_select_image);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
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
                String token = mSharedPreferences.getString("token", null);
                if(token==null)
                {
                    loginRegisterAlertDialog();
                }
                else
                {
                    uploadEventDetails();

                }
            }
        });

        return view;
    }

    private void loginRegisterAlertDialog()
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Login");
        alertDialog.setMessage("you need to be logged in before uploading your event");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Login",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });


        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Register",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(getActivity(), RegisterActivity.class));
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
                    Uri imageUri = data.getData();

                    final InputStream imageStream;
                    try {
                        imageStream = getActivity().getContentResolver()
                                .openInputStream(imageUri);
                        selectedImage = BitmapFactory.decodeStream(imageStream);
                        encodedImage = encodeImage(selectedImage);
                        Toast.makeText(getActivity(), encodedImage, Toast.LENGTH_SHORT).show();
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

//                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
//                            filePathColumn, null, null, null);
//                    cursor.moveToFirst();
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    imgDecodableString = cursor.getString(columnIndex);
//                    Toast.makeText(getActivity(),imgDecodableString,Toast.LENGTH_LONG).show();
//                    cursor.close();

                    break;
            }
        }
    }

    private String encodeImage(Bitmap selectedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        selectedImage.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    private void uploadEventDetails() {
        String name = etName.getText().toString().trim();
        String location = etVenue.getText().toString().trim();
        int entrance = Integer.valueOf(etEntrance.getText().toString());
        String dateTime = etTime.getText().toString().trim();
        String shortDescription = etShortDescription.getText().toString().trim();
        String longDescription = etLongDescription.getText().toString().trim();

        if (name.isEmpty()||location.isEmpty()||dateTime.isEmpty()||
                shortDescription.isEmpty()||longDescription.isEmpty())
        {
            Toast.makeText(getActivity(), "Ensure all fields are filled",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            EventModel eventModel = new EventModel(name, location, encodedImage, entrance, dateTime,
                    shortDescription, longDescription);

            Call<EventModel> call = RetrofitClient.getInstance(getActivity())
                    .getApiConnector()
                    .addYourEvent(eventModel);

            call.enqueue(new Callback<EventModel>() {
                @Override
                public void onResponse(Call<EventModel> call, Response<EventModel> response) {
                    if(response.code() == 201)
                    {
                        Toast.makeText(getActivity(),"Event successfuly posted",
                                Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "failed to upload event",
                                Toast.LENGTH_SHORT).show();
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

}
