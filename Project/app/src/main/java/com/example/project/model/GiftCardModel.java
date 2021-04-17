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

        public double getAmount() {
            return amount;
        }

        public String getMessage() {
            return message;
        }

        public int getPin() {
            return pin;
        }

        public boolean isRedeemed() {
            return redeemed;
        }

        public void setRedeemed(boolean redeemed) {
            this.redeemed = redeemed;
        }
    }

    public int getNumOfCardsPurchased() {
        return numOfCardsPurchased;
    }

    public void setNumOfCardsPurchased(int numOfCardsPurchased) {
        this.numOfCardsPurchased = numOfCardsPurchased;
    }

    public int getNumOfCardsRedeemed() {
        return numOfCardsRedeemed;
    }

    public void setNumOfCardsRedeemed(int numOfCardsRedeemed) {
        this.numOfCardsRedeemed = numOfCardsRedeemed;
    }

    public double getTotalPurchasedAmount() {
        return totalPurchasedAmount;
    }

    public void setTotalPurchasedAmount(double totalPurchasedAmount) {
        this.totalPurchasedAmount = totalPurchasedAmount;
    }

    public double getTotalRedeemedAmount() {
        return totalRedeemedAmount;
    }

    public void setTotalRedeemedAmount(double totalRedeemedAmount) {
        this.totalRedeemedAmount = totalRedeemedAmount;
    }

    public List<GiftCard> getGiftCards() {
        return giftCards;
    }


    public int purchaseCard(String message, double amount, int pin) {
        this.giftCards.add(new GiftCard(amount, pin, message));
        this.numOfCardsPurchased = numOfCardsPurchased+1;
        this.totalPurchasedAmount = totalPurchasedAmount + amount;
        return giftCards.size() - 1;
    }

    public String redeemCard(int code, int pin) {
        if (code > -1 && code < getGiftCards().size()) {
            GiftCard giftCard = giftCards.get(code);
            if (pin != giftCard.getPin()) {
                return "Failed to redeem, invalid gift card pin";
            }
            if (!giftCard.isRedeemed()) {
                giftCard.setRedeemed(true);
                this.numOfCardsRedeemed = numOfCardsRedeemed+1;
                this.totalRedeemedAmount = totalRedeemedAmount + giftCard.getAmount();
                return code+" gift card worth" + giftCard.getAmount() + " redeemed successfully.";
            } else {
                return "Failed to redeem, gift card has been redeemed.";
            }
        }
        return "Failed to redeem, invalid gift card code";
    }
}
