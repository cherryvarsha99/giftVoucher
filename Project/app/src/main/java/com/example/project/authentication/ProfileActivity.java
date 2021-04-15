package com.example.project.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.utils.SharedPrefs;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityProfileBinding binding;
    private SharedPrefs sharedPrefs;
    private String email;
    private String name;
    private String lastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPrefs = SharedPrefs.getInstance(this);
        email = sharedPrefs.getValue("email");
        name = sharedPrefs.getValue("name");
        lastname = sharedPrefs.getValue("lastname");
        findViewById(R.id.editProfileTextView).setOnClickListener(this);
        findViewById(R.id.changePasswordTextView).setOnClickListener(this);
        findViewById(R.id.supportTextView).setOnClickListener(this);
        findViewById(R.id.logoutTextView).setOnClickListener(this);
        TextView emailTv= findViewById(R.id.emailAddressTextView);
        emailTv.setText(email);
        TextView fullNameTv= findViewById(R.id.fullNameTextView);
        fullNameTv.setText(name+" "+lastname);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editProfileTextView:
                Intent intent = new Intent(this, EditProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.changePasswordTextView:
                Intent changePass = new Intent(this, ChangePasswordActivity.class);
                startActivity(changePass);
                break;
            case R.id.logoutTextView:
                sharedPrefs.clearAllPreferences();
                startActivity(new Intent(this, LoginActivity.class));
                break;
            default:
                Intent defaultFrag = new Intent(this, ProfileActivity.class);
                startActivity(defaultFrag);
                break;
        }
    }

}
