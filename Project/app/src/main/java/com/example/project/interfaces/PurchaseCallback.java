package com.example.project.interfaces;

public interface PurchaseCallback {
    void update(int pin, String message, double amount, String currency );
    void swapToRedeem();

}
