package com.example.project.authentication;


import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.utils.SharedPrefs;



public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPrefs sharedPrefs;
    EditText emailEt, passwordEt;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activty);
    }


    @Override
    public void onClick(View v) {

    }
}