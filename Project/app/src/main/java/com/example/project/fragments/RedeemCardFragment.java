package com.example.project.fragments;


import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.example.project.interfaces.RedeemCallback;
import com.example.project.utils.SharedPrefs;
import com.example.project.utils.ViewUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;


public class RedeemCardFragment extends Fragment implements View.OnClickListener {

    private TextInputEditText editTextMessage;
    private TextInputEditText editTextPin;
    private TextInputLayout textInputLayoutPin;
    private TextInputLayout textInputLayoutCode;
    private TextInputEditText editTextCode;
    private Button buttonRedeem;
    private SharedPrefs sharedPrefs;
    private String messageFromDb;

    private RedeemCallback redeemCallback;
    private static final String FRAGMENT_TAG = "fragmentTag";
    private static final String FRAGMENT_NAME = "RedeemCardFragment";

    public RedeemCardFragment() {
        // Required empty public constructor
    }
    public static RedeemCardFragment newInstance( ) {
        RedeemCardFragment fragment = new RedeemCardFragment();
        Bundle args = new Bundle();
        args.putString(FRAGMENT_TAG, FRAGMENT_NAME);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPrefs =SharedPrefs.getInstance(getActivity());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_redeem_card, container, false);
        //message view
        editTextMessage = view.findViewById(R.id.messageEditText);
        editTextMessage.setFocusable(false);
        //pin view
        textInputLayoutPin = view.findViewById(R.id.pinInputLayout);
        editTextPin = view.findViewById(R.id.pinEditText);
        //code
        textInputLayoutCode = view.findViewById(R.id.codeInputLayout);
        editTextCode = view.findViewById(R.id.codeEditText);
        editTextCode.addTextChangedListener(new EditTextWatcher(getActivity(), textInputLayoutCode, editTextCode));
        //button redeem
        buttonRedeem = view.findViewById(R.id.btnRedeem);
        buttonRedeem.setOnClickListener(this);
        messageFromDb=null;
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRedeem:
                if (textInputLayoutCode.isErrorEnabled() || textInputLayoutPin.isErrorEnabled()) {
                    Toast.makeText(getContext(), "Error: Pin and code are required.", Toast.LENGTH_LONG).show();
                }else {
                    ViewUtils.showProgressDialog(view.getContext(),false,"Redeeming....");
                    String pin = editTextPin.getText().toString();
                    String code = editTextCode.getText().toString();
                    String userId = sharedPrefs.getValue("id");
                    //redeemCallback.update(code, pin);
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("gifts");
                    Query checkUser = db.orderByChild("userId");
                    checkUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            ViewUtils.pauseProgressDialog();
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                String storedUserId = childSnapshot.child("userId").getValue(String.class);
                                String storedCode = childSnapshot.child("code").getValue(String.class);
                                String storedPin = childSnapshot.child("pin").getValue(String.class);
                                String storedRedeemed = childSnapshot.child("redeemed").getValue(String.class);
                                if (storedRedeemed!=null && storedUserId != null && storedCode!=null && storedPin!=null
                                        && storedUserId.equalsIgnoreCase(userId) && storedCode.equalsIgnoreCase(code) &&
                                        storedPin.equalsIgnoreCase(pin) && storedRedeemed.equalsIgnoreCase("false")) {
                                    Log.d("valid redeem", "for user");
                                    messageFromDb = childSnapshot.child("message").getValue(String.class);
                                    String idFromDb = childSnapshot.child("id").getValue(String.class);
                                    assert idFromDb != null;
                                    snapshot.child(idFromDb).child("redeemed").getRef().setValue("true");
                                    editTextMessage.setText(messageFromDb);
                                    ViewUtils.showToast(view.getContext(), "Redeem was successful");
                                    redeemCallback.update(idFromDb);
                                }
                            }
                            if(messageFromDb==null) {
                                Log.d("Invalid redeem", "for user");
                                ViewUtils.showDialog(view.getContext(), "Redeem Error", "Invalid pin and/or code or Gift card has been redeemed", null, true);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            ViewUtils.pauseProgressDialog();
                            ViewUtils.showDialog(view.getContext(), "Could not connect to remote server", error.getMessage(), null, true);
                        }
                    });
                }

        }
    }


    class EditTextWatcher implements TextWatcher {
        private TextInputLayout layout;
        private TextInputEditText view;
        private Context context;

        public EditTextWatcher(Context con, TextInputLayout layout, TextInputEditText view) {
            this.view = view;
            this.layout = layout;
            this.context = con;
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.codeEditText:
                    if (!isValid(editable.toString())) {
                        layout.setError("Code is required!!");
                        view.requestFocus();
                        editTextMessage.getText().clear();
                    }
                    break;
            }
        }
    }



    public static boolean isValid(String value) {
        return value != null && !value.equalsIgnoreCase("") && !value.isEmpty();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof RedeemCallback){
            this.redeemCallback = (RedeemCallback) context;
        }else {
            throw new RuntimeException(context.toString() + " is invalid, it must implement RedeemCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        redeemCallback = null;
    }

}