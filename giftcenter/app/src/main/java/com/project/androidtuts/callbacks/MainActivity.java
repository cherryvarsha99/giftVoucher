package com.project.androidtuts.callbacks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.androidtuts.callbacks.fragments.Gift;
import com.project.androidtuts.callbacks.fragments.PurchaseCardFragment;
import com.project.androidtuts.callbacks.model.GiftCardModel;
import com.project.androidtuts.callbacks.utils.SharedPrefs;
import com.project.androidtuts.callbacks.utils.ViewUtils;
import com.project.androidtuts.callbacks.R;
import com.project.androidtuts.callbacks.authentication.LoginActivity;
import com.project.androidtuts.callbacks.fragments.RedeemCardFragment;
import com.project.androidtuts.callbacks.interfaces.PurchaseCallback;
import com.project.androidtuts.callbacks.interfaces.RedeemCallback;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements PurchaseCallback, RedeemCallback, View.OnClickListener {

    private GiftCardModel giftCardModel;
    private FragmentManager fragmentManager;
    String fragmentName;
    private Button btnSwitchFragments;
    private Button btnGoToListView;
    private SharedPrefs sharedPrefs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPrefs =SharedPrefs.getInstance(this);
        giftCardModel = GiftCardModel.getSingleton();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PurchaseCardFragment purchaseCardFragment = PurchaseCardFragment.newInstance();
        purchaseCardFragment.setArguments(getIntent().getExtras());
        fragmentTransaction.add(R.id.fragment_container, purchaseCardFragment);
        fragmentTransaction.commit();
        fragmentName = "PurchaseFragment";
        btnSwitchFragments = (Button) findViewById(R.id.btnSwitchFragment);
        btnGoToListView = (Button) findViewById(R.id.btnCardsListViewLink);
        btnGoToListView.setOnClickListener(this);
        btnSwitchFragments.setOnClickListener(this);


    }

    @Override
    public void update(int pin, String message, double amount,String currency) {
        //int code = giftCardModel.purchaseCard(message, amount, pin);
        int code = new Random().nextInt();
        String toastMessage = code + " gift card worth " + amount + " purchased successfully";
        makeToast(toastMessage, getApplicationContext());
        String totalAmount= String.format("%.2f", amount);
        ViewUtils.showProgressDialog(this, false, "Purchasing gift....");
        DatabaseReference dbUserRef = FirebaseDatabase.getInstance().getReference("gifts");
        String userId = sharedPrefs.getValue("id");
        Long id= new Random().nextLong();
        Gift gift = new Gift(String.valueOf(id), message, totalAmount, currency, String.valueOf(pin), String.valueOf(code), "false", userId);
        dbUserRef.child(String.valueOf(id)).setValue(gift);
        ViewUtils.pauseProgressDialog();
        ViewUtils.showToast(this, "Purchase confirmed");
        Intent regIntent = new Intent(this, MainMenuActivity.class);
        this.startActivity(regIntent);
    }

    @Override
    public void swapToRedeem() {
        btnSwitchFragments.setText("Purchase");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RedeemCardFragment redeemCardFragment = RedeemCardFragment.newInstance();
        fragmentTransaction.replace(R.id.fragment_container, redeemCardFragment);
        fragmentTransaction.commit();
        fragmentName= redeemCardFragment.getArguments().getString("fragmentTag");

    }

    @Override
    public void update(String idFromDb) {
        Intent intent = new Intent(MainActivity.this, GiftCardsActivity.class);
        startActivity(intent);
    }

    @Override
    public void swapToPurchase() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PurchaseCardFragment purchaseCardFragment = PurchaseCardFragment.newInstance();
        fragmentTransaction.replace(R.id.fragment_container, purchaseCardFragment);
        fragmentTransaction.commit();
        fragmentName= purchaseCardFragment.getArguments().getString("fragmentTag");
        btnGoToListView.setText("Gift Cards");
        btnSwitchFragments.setText("Redeem");
    }

    private void makeToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View view) {
        if( view.getId()==R.id.btnSwitchFragment) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (btnSwitchFragments.getText().toString().equalsIgnoreCase("Purchase")) {
                btnSwitchFragments.setText("Redeem");
                btnGoToListView.setText("Gift Cards");
                PurchaseCardFragment purchaseCardFragment = PurchaseCardFragment.newInstance();
                purchaseCardFragment.setArguments(getIntent().getExtras());
                fragmentTransaction.replace(R.id.fragment_container, purchaseCardFragment);
                fragmentTransaction.commit();
            } else {
                btnSwitchFragments.setText("Purchase");
                btnGoToListView.setText("Gift Cards");
                RedeemCardFragment redeemCardFragment = RedeemCardFragment.newInstance();
                redeemCardFragment.setArguments(getIntent().getExtras());
                fragmentTransaction.replace(R.id.fragment_container, redeemCardFragment);
                fragmentTransaction.commit();
            }
        } else{
            if(btnGoToListView.getText().toString().equalsIgnoreCase("Gift Cards")){
                btnGoToListView.setText("Purchase");
                btnSwitchFragments.setText("Redeem");
                Intent intent = new Intent(view.getContext(), GiftCardsActivity.class);
                startActivity(intent);
            }

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
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                return true;
            case R.id.home_link:
                startActivity(new Intent(MainActivity.this, MainMenuActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
