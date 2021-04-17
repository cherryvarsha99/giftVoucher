package com.project.androidtuts.callbacks.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.project.androidtuts.callbacks.MainMenuActivity;
import com.project.androidtuts.callbacks.utils.SharedPrefs;
import com.project.androidtuts.callbacks.utils.ViewUtils;
import com.project.androidtuts.callbacks.R;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    EditText emailEt;
    EditText passwordEt;
    EditText confirmPasswordEt;
    private SharedPrefs sharedPrefs;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        sharedPrefs = SharedPrefs.getInstance(this);
        id = sharedPrefs.getValue("id");
        emailEt =findViewById(R.id.editTextRegisteredEmail);
        passwordEt =findViewById(R.id.editTextNewPassword);
        confirmPasswordEt =findViewById(R.id.editTextConfirmPassword);
        findViewById(R.id.btnSubmitNewPassword).setOnClickListener(this);
        
    }

    @Override
    public void onClick(View view) {
        if(isValidInput()){
            ViewUtils.showProgressDialog(this, false, "updating password ....");
            String email=ViewUtils.getEditTextValue(emailEt);
            String password=ViewUtils.getEditTextValue(passwordEt);
            Log.d("Searching for user"," with ID, "+id);
            DatabaseReference dbSchedulesRef = FirebaseDatabase.getInstance().getReference("users");
            Query query = dbSchedulesRef.orderByChild("id").equalTo(id);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    ViewUtils.pauseProgressDialog();
                    if(snapshot.exists() ){
                        Log.d("found user"," {}, "+snapshot.toString());
                        snapshot.child(String.valueOf(id)).child("password").getRef().setValue(password);
                        ViewUtils.showDialog(view.getContext(), "Success", "Password updated successfully", null, true);
                        Intent intent = new Intent(ChangePasswordActivity.this, ProfileActivity.class);
                        startActivity(intent);

                    }else {
                        Log.d("Could not find user"," with ID "+id);
                        ViewUtils.showDialog(view.getContext(), "Failed", "The email entered is not registered", null, true);
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    ViewUtils.pauseProgressDialog();
                    ViewUtils.showDialog(view.getContext(), "Error", error.getMessage(), null, true);
                }
            });

        }
    }

    public boolean isValidInput() {
        if (!ViewUtils.isEditTextFilled(new EditText[]{emailEt, passwordEt,
                confirmPasswordEt})) {
            ViewUtils.showToast(this, "All fields are mandatory");
            return false;
        }
        String email = ViewUtils.getEditTextValue(emailEt);
        if (!ViewUtils.isEmailValid(email)) {
            ViewUtils.showToast(this, "Invalid email");
            return false;
        }
        if (!ViewUtils.isPasswordLengthCorrect(passwordEt)) {
            ViewUtils.showToast(this, "Password must have at least 6 characters");
            return false;
        }
        String password = ViewUtils.getEditTextValue(passwordEt);

        if (!password.equals(ViewUtils.getEditTextValue(confirmPasswordEt))) {
            ViewUtils.showToast(this, "Passwords must match");
            return false;
        }
        return true;
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
                startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
                finish();
                return true;
            case R.id.home_link:
                startActivity(new Intent(ChangePasswordActivity.this, MainMenuActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}