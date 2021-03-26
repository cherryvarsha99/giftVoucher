package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.authentication.ProfileActivity;

public class MainMenuActivity extends AppCompatActivity {

    private TextView mTextView;
    private Button btnViewProfile;

    private Button btnManageGiftCards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mTextView = (TextView) findViewById(R.id.text);
        btnViewProfile= (Button) findViewById(R.id.btnViewProfile);
        btnManageGiftCards= (Button) findViewById(R.id.btnManageGiftCards);





    }

    public void profile(View v)
    {
        Intent myIntent = new Intent(getApplicationContext(), ProfileActivity.class);

        startActivity(myIntent);
    }

    public void manageGifts(View v)
    {
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(myIntent);
    }



}