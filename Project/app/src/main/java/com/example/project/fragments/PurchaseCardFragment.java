package com.example.project.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.example.project.R;
import com.example.project.interfaces.PurchaseCallback;
import com.example.project.utils.CurrencyConversion;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;

public class PurchaseCardFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private PurchaseCallback purchaseCallback;

    private static final String FRAGMENT_TAG = "fragmentTag";
    private static final String FRAGMENT_NAME = "PurchaseFragment";
    private TextInputEditText editTextMessage;
    private TextInputLayout textInputLayoutMessage;
    private TextInputEditText editTextPin;
    private TextInputLayout textInputLayoutPin;
    private TextInputLayout textInputLayoutAmount;
    private TextInputEditText editTextAmount;
    private TextView usdAmountTextView;
    private SmartMaterialSpinner currencyDropDown;
    private SmartMaterialSpinner usdDropDown;
    private LinearLayout amountInputLinearLayout;
    private LinearLayout usdSpinnerLinearLayout;
    private Button buttonPurchase;
    private Double usdAmount = 0.00;
    private String selectedCurrency;
    private String selectedAmount;
    String[] currencies = {"USD", "ZAR", "EURO"};
    String[] usdAmounts = {"5", "10", "15", "25", "other"};

    public PurchaseCardFragment() {
        // Required empty public constructor
    }

    public static PurchaseCardFragment newInstance() {
        PurchaseCardFragment fragment = new PurchaseCardFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_purchasecard__fragment, container, false);
        //message view
        textInputLayoutMessage = view.findViewById(R.id.messageInputLayout);
        editTextMessage = view.findViewById(R.id.messageEditText);
        editTextMessage.addTextChangedListener(new EditTextWatcher(getActivity(), textInputLayoutMessage, editTextMessage));
        //pin view
        textInputLayoutPin = view.findViewById(R.id.pinInputLayout);
        editTextPin = view.findViewById(R.id.pinEditText);
        editTextPin.addTextChangedListener(new EditTextWatcher(getActivity(), textInputLayoutPin, editTextPin));
        //currency
        currencyDropDown = view.findViewById(R.id.currencySpinnerDropdown);
        currencyDropDown.setItem(Arrays.asList(currencies));
        selectedCurrency = currencies[0];
        currencyDropDown.setOnItemSelectedListener(this);
        //usdAmounts
        usdSpinnerLinearLayout =view.findViewById(R.id.usdSpinnerLinearLayout);
        usdDropDown = view.findViewById(R.id.usdSpinner);
        usdDropDown.setItem(Arrays.asList(usdAmounts));
        selectedAmount = usdAmounts[0];
        usdDropDown.setOnItemSelectedListener(this);
        //amount
        amountInputLinearLayout =view.findViewById(R.id.amountInputLinearLayout);
        textInputLayoutAmount = view.findViewById(R.id.amountInputLayout);
        editTextAmount = view.findViewById(R.id.amountEditText);
        editTextAmount.addTextChangedListener(new EditTextWatcher(getActivity(), textInputLayoutAmount, editTextAmount));
        //usd equivalent
        usdAmountTextView = view.findViewById(R.id.usdAmountTextView);
        usdAmountTextView.setText(String.format("%.2f", usdAmount));
        //button purchase
        buttonPurchase = view.findViewById(R.id.btnPurchase);
        buttonPurchase.setOnClickListener(this::onClick);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPurchase:
                if (usdAmount < 0.001) {
                    Toast.makeText(getContext(), "Error: Invalid amount", Toast.LENGTH_LONG).show();
                } else if (textInputLayoutMessage.isErrorEnabled() ||
                        textInputLayoutAmount.isErrorEnabled() ||
                        textInputLayoutPin.isErrorEnabled() || textInputLayoutAmount.isErrorEnabled()) {
                    Toast.makeText(getContext(), "Error: Fix errors.", Toast.LENGTH_LONG).show();
                } else {
                    if(editTextPin.getText().toString().equalsIgnoreCase("") ||
                            editTextMessage.getText().toString().equalsIgnoreCase("") ){
                        Toast.makeText(getContext(), "Error: Invalid pin or message!", Toast.LENGTH_LONG).show();
                    }else{
                        int pin = Integer.parseInt(editTextPin.getText().toString());
                        String message = editTextMessage.getText().toString();
                        purchaseCallback.update(pin, message, usdAmount, selectedCurrency);
                        purchaseCallback.swapToRedeem();
                    }
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        switch (adapterView.getId()){
            case R.id.currencySpinnerDropdown:
                selectedCurrency =currencies[pos];
                if(selectedCurrency.equalsIgnoreCase("USD")){
                    //hide textInput and only make spinner visible
                    amountInputLinearLayout.setVisibility(View.GONE);
                    amountInputLinearLayout.setActivated(false);
                    usdSpinnerLinearLayout.setVisibility(View.VISIBLE);
                    usdSpinnerLinearLayout.setActivated(true);
                }else{
                    //hide usdSpinner and only make AmountTextInput visible
                    amountInputLinearLayout.setVisibility(View.VISIBLE);
                    usdSpinnerLinearLayout.setVisibility(View.GONE);
                    usdSpinnerLinearLayout.setActivated(false);
                    amountInputLinearLayout.setActivated(true);
                }
                editTextAmount.getText().clear();
                usdAmountTextView.setText(String.format("%.2f", 0.00));
                break;
            case R.id.usdSpinner:
                selectedAmount= usdAmounts[pos];
                if(selectedAmount.equalsIgnoreCase("other")){
                    //hide usdSpinner and only make AmountTextInput visible
                    amountInputLinearLayout.setVisibility(View.VISIBLE);
                    amountInputLinearLayout.setActivated(true);
                    usdSpinnerLinearLayout.setVisibility(View.GONE);
                    usdSpinnerLinearLayout.setActivated(false);
                    editTextAmount.getText().clear();
                    usdAmountTextView.setText(String.format("%.2f", 0.00));
                }else{
                    //hide input layout then getAmount
                    amountInputLinearLayout.setVisibility(View.GONE);
                    amountInputLinearLayout.setActivated(false);
                    usdAmountTextView.setText(String.format("%.2f", Double.parseDouble(selectedAmount)));
                    calculateAndSetAmount();
                }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
                case R.id.messageEditText:
                    if (!isValid(editable.toString())) {
                        layout.setError("Message is required!!");
                        view.requestFocus();
                    } else {
                        layout.setErrorEnabled(false);
                    }
                    break;
                case R.id.pinEditText:
                    if (!isValid(editable.toString())) {
                        layout.setError("Pin is required!!");
                        view.requestFocus();
                    } else if (!isPinLengthValid(editable.toString())) {
                        layout.setError("Pin must contain 3 characters!!");
                        view.requestFocus();
                    } else {
                        layout.setErrorEnabled(false);
                    }
                    break;
                case R.id.amountEditText:
                    if (!isValid(editable.toString())) {
                        layout.setError("Amount is required!!");
                        view.requestFocus();
                    } else {
                        layout.setErrorEnabled(false);
                        selectedAmount=editable.toString();
                        calculateAndSetAmount();
                    }
                    break;
            }
        }
    }

    private void calculateAndSetAmount() {
        String currency = selectedCurrency;
        String userAmount2dp = String.format("%.2f", Double.parseDouble(selectedAmount));
        double currencyAmount = Double.parseDouble(userAmount2dp);
        if (currency.equalsIgnoreCase("euro")) {
            usdAmount = CurrencyConversion.convertEuroToDollar(currencyAmount);
        } else if (currency.equalsIgnoreCase("zar")) {
            usdAmount = CurrencyConversion.convertRandToDollar(currencyAmount);
        } else {
            usdAmount = currencyAmount;
        }
        usdAmountTextView.setText(String.valueOf(usdAmount));
    }

    public static boolean isValid(String value) {
        return value != null && !value.equalsIgnoreCase("") && !value.isEmpty();
    }

    public boolean isPinLengthValid(String pin) {
        return pin.length() == 3;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PurchaseCallback) {
            this.purchaseCallback = (PurchaseCallback) context;
        } else {
            throw new RuntimeException(context.toString() + " is invalid, it must implement PurchaseCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        purchaseCallback = null;
    }
}
