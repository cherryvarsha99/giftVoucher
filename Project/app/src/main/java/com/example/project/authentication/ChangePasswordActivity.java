package com.example.project.authentication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.utils.SharedPrefs;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    EditText emailEt;
    EditText passwordEt;
    EditText confirmPasswordEt;
    private SharedPrefs sharedPrefs;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        sharedPrefs = SharedPrefs.getInstance(this);
        id = sharedPrefs.getValue("id");
        emailEt =findViewById(R.id.editTextRegisteredEmail);
        passwordEt =findViewById(R.id.editTextNewPassword);
        confirmPasswordEt =findViewById(R.id.editTextConfirmPassword);
        findViewById(R.id.btnSubmitNewPassword).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        
    }
}
