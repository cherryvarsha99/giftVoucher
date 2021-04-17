package com.example.project.fragments;

public class Gift {

    private String id;
    private String message;
    private String amount;
    private String currency;
    private String pin;
    private String code;
    private String redeemed;
    private String userId;


    public Gift(String id, String message, String amount,String currency, String pin, String code, String redeemed, String userId) {
        this.id = id;
        this.message = message;
        this.amount = amount;
        this.pin = pin;
        this.code = code;
        this.redeemed = redeemed;
        this.userId = userId;
        this.currency=currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRedeemed() {
        return redeemed;
    }

    public void setRedeemed(String redeemed) {
        this.redeemed = redeemed;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

