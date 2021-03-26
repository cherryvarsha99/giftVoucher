package com.example.project.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.MainMenuActivity;
import com.example.project.R;


public class LoginActivity extends AppCompatActivity {

    EditText emailEt, passwordEt;
    CheckBox checkBox;
    private Button btnLogin;
    TextView RegisterTV;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activty);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        RegisterTV = (TextView) findViewById(R.id.txtRegisterLink);

    }

    public void login(View v) {
        Intent myIntent = new Intent(getApplicationContext(), MainMenuActivity.class);

        startActivity(myIntent);

    }
    public void Register(View view)
    {
        Intent myIntent = new Intent(getApplicationContext(), RegisterActivity.class);

        startActivity(myIntent);


    }

}
