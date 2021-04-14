package com.example.project.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.MainMenuActivity;
import com.example.project.R;
import com.example.project.utils.ViewUtils;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nameEt, lastnameEt, emailEt, passEt, confirmPassEt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById(R.id.btnRegister).setOnClickListener(this);
        findViewById(R.id.txtViewLoginLink).setOnClickListener(this);
        nameEt =findViewById(R.id.editTextfirstname);
        lastnameEt =findViewById(R.id.editTextSurname);
        emailEt =findViewById(R.id.editTextEmail);
        passEt =findViewById(R.id.editTextPassword);
        confirmPassEt =findViewById(R.id.editTextPasswordConfirm);


    }
    public void login(View v) {
        Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);

        startActivity(myIntent);

    }

    public boolean isValidInput() {
        if (!ViewUtils.isEditTextFilled(new EditText[]{ nameEt, lastnameEt,emailEt})) {
            ViewUtils.showToast(this, "ALL_FIELDS_ARE_REQUIRED");
            return false;
        }
        String email = ViewUtils.getEditTextValue(emailEt);
        if (!ViewUtils.isEmailValid(email)) {
            ViewUtils.showToast(this, "INVALID_EMAIL");
            return false;
        }
        if (!ViewUtils.isPasswordLengthCorrect(passEt)) {
            ViewUtils.showToast(this, "PASSWORD_LENGTH_POLICY");
            return false;
        }
        String password = ViewUtils.getEditTextValue(passEt);
        if (ViewUtils.isPasswordValid(password)) {
            ViewUtils.showToast(this, "PASSWORD_CONTENT_POLICY");
            return false;
        }
        if (!password.equals(ViewUtils.getEditTextValue(confirmPassEt))) {
            ViewUtils.showToast(this, "PASSWORD_MISMATCH");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {

    }
}
