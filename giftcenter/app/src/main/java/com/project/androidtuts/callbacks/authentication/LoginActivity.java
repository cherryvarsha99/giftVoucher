package com.project.androidtuts.callbacks.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
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
import com.project.androidtuts.callbacks.utils.User;
import com.project.androidtuts.callbacks.utils.ViewUtils;
import com.project.androidtuts.callbacks.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

        private SharedPrefs sharedPrefs;
       EditText emailEt, passwordEt;
       CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPrefs =SharedPrefs.getInstance(this);
        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.txtForgotPasswordLink).setOnClickListener(this);
        findViewById(R.id.txtRegisterLink).setOnClickListener(this);
        CheckBox checkBox = findViewById(R.id.checkBoxShowHidePassword);
        emailEt= findViewById(R.id.editTextEmail);
        passwordEt = findViewById(R.id.editTextPassword);
        checkBox.setOnCheckedChangeListener((button, isChecked) -> {
                    // If it is checked then show password else hide password
                    if (isChecked) { //show password
                        checkBox.setText("hide");
                        passwordEt.setInputType(InputType.TYPE_CLASS_TEXT);
                        passwordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else { //hide password
                        checkBox.setText("show");
                        passwordEt.setInputType(InputType.TYPE_CLASS_TEXT
                                | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        passwordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                String email = ViewUtils.getEditTextValue(emailEt);
                String password = ViewUtils.getEditTextValue(passwordEt);
                boolean isValid =applyValidation(email,password);
                if(isValid){
                    ViewUtils.showProgressDialog(this,false,"Authenticating....");
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("users");
                    Query checkUser = db.orderByChild("email");
                    checkUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            ViewUtils.pauseProgressDialog();
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                String storedPass = childSnapshot.child("password").getValue(String.class);
                                String storedEmail = childSnapshot.child("email").getValue(String.class);
                                if (storedEmail != null && storedPass != null &&
                                        storedPass.equals(password) && storedEmail.equalsIgnoreCase(email)) {
                                    Log.d("found user", "with email and pass provided");
                                    Log.d(" user:", snapshot.toString());
                                    String nameFromDb = childSnapshot.child("firstname").getValue(String.class);
                                    String surnameFromDb = childSnapshot.child("lastname").getValue(String.class);
                                    String emailFromDb = childSnapshot.child("email").getValue(String.class);
                                    String passFromDb = childSnapshot.child("password").getValue(String.class);
                                    String idNumberFromDb = childSnapshot.child("id").getValue(String.class);
                                    User user = new User(String.valueOf(idNumberFromDb), nameFromDb, surnameFromDb, emailFromDb, passFromDb);
                                    sharedPrefs.setUser(user, "user");
                                    sharedPrefs.setBooleanValue("isLoggedIn", true);
                                    sharedPrefs.setValue("id", idNumberFromDb);
                                    sharedPrefs.setValue("email", emailFromDb);
                                    sharedPrefs.setValue("name", nameFromDb);
                                    sharedPrefs.setValue("lastname", surnameFromDb);
                                    ViewUtils.showToast(view.getContext(), "Login successful");
                                    Intent intent = new Intent(view.getContext(), MainMenuActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            if(!sharedPrefs.getBooleanValue("isLoggedIn")){
                                ViewUtils.showDialog(view.getContext(), "Login Error", "Wrong username and password", null, true);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            ViewUtils.pauseProgressDialog();
                            ViewUtils.showDialog(view.getContext(), "Login Error", error.getMessage(), null, true);
                        }
                    });
                }
                break;
            case R.id.txtForgotPasswordLink:
                Intent resetPasswordIntent = new Intent(this, ChangePasswordActivity.class);
                this.startActivity(resetPasswordIntent);
                break;
            case R.id.txtRegisterLink:
                Intent regIntent = new Intent(this, RegisterActivity.class);
                this.startActivity(regIntent);
                break;
        }

    }



    public boolean applyValidation(String email, String password){
        if (email==null || email.isEmpty()|| !ViewUtils.isEmailValid(email) ) {
            ViewUtils.showDialog(this,"Login Error", "Email is invalid",null,true);
            return false;
        }
        if(password==null || password.isEmpty()){
            ViewUtils.showDialog(this,"Login Error", "PASSWORD_IS_REQUIRED",null,true);
            return false;
        }
        return true;
    }
}