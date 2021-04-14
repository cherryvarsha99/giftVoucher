package com.example.project.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.MainMenuActivity;
import com.example.project.R;
import com.example.project.utils.SharedPrefs;
import com.example.project.utils.ViewUtils;

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
