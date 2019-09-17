package com.example.myapplication.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.models.RegisterUser;
import com.example.myapplication.networking.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPassword;
    private Button btnRegister;
    private TextView txLoginView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.register_username);
        etEmail = findViewById(R.id.register_email);
        etPassword = findViewById(R.id.register_password);
        btnRegister = findViewById(R.id.button_register_user);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });


    }

    private void registerUser()
    {
        String username = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        RegisterUser user = new RegisterUser(username, email, password);

        if (username.isEmpty() || email.isEmpty() || password.isEmpty())
        {
            Toast.makeText(RegisterActivity.this,
                    "Ensure all fields are filled", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Call<RegisterUser> call = RetrofitClient.getInstance(this)
                    .getApiConnector()
                    .registerUser(user);

            call.enqueue(new Callback<RegisterUser>() {
                @Override
                public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {
                    if (response.code() == 201)
                    {
                        Toast.makeText(RegisterActivity.this, "registration successful",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,
                                EventActivity.class));
                    }
                    else if(response.code() == 400)
                    {
                        Toast.makeText(RegisterActivity.this,
                                "check your credentials and try again",Toast.LENGTH_SHORT)
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<RegisterUser> call, Throwable t) {
                    if (t instanceof IOException){
                        Toast.makeText(RegisterActivity.this, "network failure",
                                Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this,
                                "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
