package com.example.myapplication.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.models.LoginUser;
import com.example.myapplication.networking.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText etLoginName, etLoginPassword;
    private Button btn_login;
    private TextView txRegisterView;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginName = findViewById(R.id.login_username);
        etLoginPassword = findViewById(R.id.login_password);
        btn_login = findViewById(R.id.button_login_user);
        txRegisterView = findViewById(R.id.login_click_here);
        mSharedPreferences = getApplicationContext().getSharedPreferences("myPref", 0);
        edit = mSharedPreferences.edit();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        String name = etLoginName.getText().toString().trim();
        String password = etLoginPassword.getText().toString().trim();
        LoginUser user = new LoginUser(name, password);

        if (name.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this, "ensure all fields are filled",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Call<LoginUser> call = RetrofitClient.getInstance(this)
                    .getApiConnector()
                    .loginUser(user);

            call.enqueue(new Callback<LoginUser>() {
                @Override
                public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                    if (response.code() == 200)
                    {
                        startActivity(new Intent(LoginActivity.this,
                                EventActivity.class));
//                        Toast.makeText(LoginActivity.this, response.body().getToken(),
//                                Toast.LENGTH_SHORT).show();
                        edit.putString("token", response.body().getToken());
                        edit.commit();
                    }
                    else if (response.code() == 400)
                    {
                        Toast.makeText(LoginActivity.this,
                                "Error: invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginUser> call, Throwable t) {
                    if (t instanceof IOException)
                    {
                        Toast.makeText(LoginActivity.this,
                                "network failure", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Error: "+t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
