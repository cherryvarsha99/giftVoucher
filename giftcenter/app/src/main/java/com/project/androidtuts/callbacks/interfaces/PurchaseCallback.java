package com.project.androidtuts.callbacks.interfaces;


public interface PurchaseCallback {

    void update(int pin, String message, double amount, String currency );
    void swapToRedeem();

}
