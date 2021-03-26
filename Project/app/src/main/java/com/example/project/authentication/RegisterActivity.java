package com.example.project.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.MainMenuActivity;
import com.example.project.R;

public class RegisterActivity extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


    }
    public void login(View v) {
        Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);

        startActivity(myIntent);

    }



}
