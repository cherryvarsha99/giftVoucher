package com.example.project.model;

import java.util.ArrayList;
import java.util.List;

public class GiftCardModel {

    private static GiftCardModel giftCardModel;

    public static GiftCardModel getSingleton(){
        if(giftCardModel==null){
            giftCardModel=new GiftCardModel();
        }
        return giftCardModel;
    }


    private int numOfCardsPurchased;
    private int numOfCardsRedeemed;
    private double totalPurchasedAmount;
    private double totalRedeemedAmount;
    private List<GiftCard> giftCards;

    private GiftCardModel() {
        giftCards = new ArrayList<>();
    }

    public static class GiftCard {
        private final double amount;
        private boolean redeemed;
        private final String message;
        private final int pin;

        public GiftCard(double amount, int pin, String message) {
            this.amount = amount;
            this.redeemed = false;
            this.message = message;
            this.pin = pin;
        }


    }
}
