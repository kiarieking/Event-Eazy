package com.example.myapplication.ui.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.myapplication.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadEventFragment extends Fragment {


    public UploadEventFragment() {
        // Required empty public constructor
    }

    DatePickerDialog mPicker;
    EditText etName, etVenue, etTime, etEntrance;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_event, container, false);
        etName = view.findViewById(R.id.eventname_input_text);
        etVenue = view.findViewById(R.id.eventlocation_input_text);
        etTime = view.findViewById(R.id.eventdatetime_input_text);
        etEntrance = view.findViewById(R.id.evententrance_input_text);

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

        return view;
    }

}
