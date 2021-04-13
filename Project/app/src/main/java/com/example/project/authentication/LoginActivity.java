package com.example.project.authentication;


import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.MainMenuActivity;
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
        sharedPrefs =SharedPrefs.getInstance(this);
        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.txtForgotPasswordLink).setOnClickListener(this);
        findViewById(R.id.txtRegisterLink).setOnClickListener(this);
        CheckBox checkBox = findViewById(R.id.checkBoxShowHidePassword);
        emailEt= findViewById(R.id.editTextEmail);
        passwordEt = findViewById(R.id.editTextPassword);
        checkBox.setOnCheckedChangeListener((button, isChecked) -> {
            // If it is checked then show password else hide password
            if (isChecked) { //show password
                checkBox.setText("hide");
                passwordEt.setInputType(InputType.TYPE_CLASS_TEXT);
                passwordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else { //hide password
                checkBox.setText("show");
                passwordEt.setInputType(InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}