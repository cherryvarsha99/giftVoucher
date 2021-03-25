package com.example.project.authentication;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;


public class LoginActivity extends AppCompatActivity {

    EditText emailEt, passwordEt;
    CheckBox checkBox;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activty);

    }
}
