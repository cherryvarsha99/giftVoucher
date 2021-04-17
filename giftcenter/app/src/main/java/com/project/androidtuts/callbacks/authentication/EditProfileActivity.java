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
import com.project.androidtuts.callbacks.databinding.ActivityEditProfileBinding;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityEditProfileBinding binding;
    private SharedPrefs sharedPrefs;
    private String id;
    private String name;
    private String lastname;
    EditText nameEt;
    EditText lastnameEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        sharedPrefs = SharedPrefs.getInstance(this);
        id = sharedPrefs.getValue("id");
        name = sharedPrefs.getValue("name");
        lastname = sharedPrefs.getValue("lastname");
         nameEt =findViewById(R.id.etFirstName);
        nameEt.setText(name);
         lastnameEt =findViewById(R.id.etLastName);
        lastnameEt.setText(lastname);
        findViewById(R.id.btnUpdateProfile).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if( view.getId()== R.id.btnUpdateProfile){
            name = nameEt.getText().toString();
            lastname = lastnameEt.getText().toString();
            if (!isValidName(name) || !isValidName(lastname)) {
                nameEt.setError("Invalid firstname or lastname. All fields are required!!");
            }  else {
                ViewUtils.showProgressDialog(this,false,"Updating user details....");
                Log.d("Searching for user"," with ID, "+id);
                DatabaseReference dbSchedulesRef = FirebaseDatabase.getInstance().getReference("users");
                Query query = dbSchedulesRef.orderByChild("id").equalTo(id);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        ViewUtils.pauseProgressDialog();
                        if(snapshot.exists() ){
                            Log.d("found user"," {}, "+snapshot.toString());
                            snapshot.child(String.valueOf(id)).child("firstname").getRef().setValue(name);
                            snapshot.child(String.valueOf(id)).child("lastname").getRef().setValue(lastname);
                            ViewUtils.showDialog(view.getContext(), "Success", "Profile updated successfully", null, true);
                            sharedPrefs.setValue("name", name);
                            sharedPrefs.setValue("lastname", lastname);
                            Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                            startActivity(intent);

                        }else {
                            Log.d("Could not find user"," with ID "+id);
                            ViewUtils.showDialog(view.getContext(), "Failed", "The email entered is not registered", null, true);
                            Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                            startActivity(intent);
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


    }

    private boolean isValidName(String user) {
        return user != null && user.length() > 0;
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
                startActivity(new Intent(EditProfileActivity.this, LoginActivity.class));
                finish();
                return true;
            case R.id.home_link:
                startActivity(new Intent(EditProfileActivity.this, MainMenuActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}