package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.authentication.LoginActivity;
import com.example.project.authentication.ProfileActivity;
import com.example.project.utils.SharedPrefs;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPrefs sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        sharedPrefs =SharedPrefs.getInstance(MainMenuActivity.this);
        findViewById(R.id.btnManageProfile).setOnClickListener(this);
        findViewById(R.id.btnViewProfile).setOnClickListener(this);
        findViewById(R.id.btnManageGiftCards).setOnClickListener(this);
        findViewById(R.id.btnListGifts).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnManageProfile:
            case R.id.btnViewProfile:
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                this.startActivity(profileIntent);
                break;
            case R.id.btnManageGiftCards:
                Intent giftCards = new Intent(this, MainActivity.class);
                this.startActivity(giftCards);
                break;
            case R.id.btnListGifts:
                Intent cardList = new Intent(this, GiftCardsActivity.class);
                this.startActivity(cardList);
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout_link:
                sharedPrefs.clearAllPreferences();
                startActivity(new Intent(MainMenuActivity.this, LoginActivity.class));
                finish();
                return true;
            case R.id.home_link:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}