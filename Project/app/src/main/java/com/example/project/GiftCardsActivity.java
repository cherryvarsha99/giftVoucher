package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.adapter.GiftAdapter;
import com.example.project.authentication.LoginActivity;
import com.example.project.fragments.Gift;
import com.example.project.utils.SharedPrefs;
import com.example.project.utils.ViewUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class GiftCardsActivity extends AppCompatActivity {

    private RecyclerView giftCardsRecyclerView;
    private GiftAdapter giftAdapter;
    private LinearLayoutManager giftsLinearLayoutManager;
    private SharedPrefs sharedPrefs;
    private List<Gift> gifts = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_cards);
        sharedPrefs =SharedPrefs.getInstance(GiftCardsActivity.this);
        giftCardsRecyclerView = (RecyclerView) findViewById(R.id.giftCardsRecyclerView);
        giftsLinearLayoutManager = new LinearLayoutManager(GiftCardsActivity.this, LinearLayoutManager.VERTICAL, false);
        giftCardsRecyclerView.setLayoutManager(giftsLinearLayoutManager);
        //fetch user's gifts
        String userId = sharedPrefs.getValue("id");
        DatabaseReference db= FirebaseDatabase.getInstance().getReference("gifts");
        Query checkUser = db.orderByChild("userId");
        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String storedUserId = childSnapshot.child("userId").getValue(String.class);
                    if (storedUserId != null && storedUserId.equals(userId)) {
                        Log.d("found user gift", " with id="+childSnapshot.child("id").getValue(String.class));
                        Log.d(" gift is:", snapshot.toString());
                        String amountFromDb = childSnapshot.child("amount").getValue(String.class);
                        String codeFromDb = childSnapshot.child("code").getValue(String.class);
                        String idFromDb = childSnapshot.child("id").getValue(String.class);
                        String msgFromDb = childSnapshot.child("message").getValue(String.class);
                        String pinNumberFromDb = childSnapshot.child("pin").getValue(String.class);
                        String isRedeemedFromDb = childSnapshot.child("redeemed").getValue(String.class);
                        String currencyFromDb = childSnapshot.child("currency").getValue(String.class);
                        Gift gift = new Gift(idFromDb, msgFromDb, amountFromDb, currencyFromDb
                                , pinNumberFromDb, codeFromDb, isRedeemedFromDb, userId);
                        gifts.add(gift);
                        sharedPrefs.setBooleanValue("wasFetched",true);
                    }
                }
                giftAdapter = new GiftAdapter(GiftCardsActivity.this, gifts);
                giftCardsRecyclerView.setAdapter(giftAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("error while trying", " to fetch gifts from server"+error.getMessage());
                ViewUtils.showToast(GiftCardsActivity.this, "Could not connect to backend service, try again later!!");
            }
        });

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
                startActivity(new Intent(GiftCardsActivity.this, LoginActivity.class));
                finish();
                return true;
            case R.id.home_link:
                startActivity(new Intent(GiftCardsActivity.this, MainMenuActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}