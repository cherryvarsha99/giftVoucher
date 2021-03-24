package com.example.project;

import android.os.Bundle;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GiftCardFragment extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_card_fragment);

        mTextView = (TextView) findViewById(R.id.text);

        // Enables Always-on

    }
}