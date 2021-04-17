package com.example.project.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.MainMenuActivity;
import com.example.project.R;
import com.example.project.utils.User;
import com.example.project.utils.ViewUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nameEt, lastnameEt, emailEt, passEt, confirmPassEt;

    @Override
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                if (isValidInput()) {
                    ViewUtils.showProgressDialog(this, false, "Registering....");
                    String name=ViewUtils.getEditTextValue(nameEt);
                    String lastname=ViewUtils.getEditTextValue(lastnameEt);
                    String email=ViewUtils.getEditTextValue(emailEt);
                    String password=ViewUtils.getEditTextValue(passEt);
                    DatabaseReference dbUserRef = FirebaseDatabase.getInstance().getReference("users");
                    //todo check if user phone exists
                    Long id= new Random().nextLong();
                    User user = new User(String.valueOf(id), name, lastname, email, password);
                    dbUserRef.child(String.valueOf(id)).setValue(user);
                    ViewUtils.pauseProgressDialog();
                    ViewUtils.showToast(this, "Registration successful");
                    Intent regIntent = new Intent(this, LoginActivity.class);
                    this.startActivity(regIntent);
                }
                break;
            case R.id.txtViewLoginLink:
                Intent regIntent = new Intent(this, LoginActivity.class);
                this.startActivity(regIntent);
                break;
        }

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
}