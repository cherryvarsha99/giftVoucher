package com.example.project.utils;

import java.text.DecimalFormat;

public class CurrencyConversion {

    public static DecimalFormat decimalFormatTo2 = new DecimalFormat("#.##");

    public static double convertEuroToDollar(double amount) {
        double amountInDollars = amount*1.5;
        String res =String.format("%.2f", amountInDollars);
        return Double.parseDouble(res);
    }
    public static double convertRandToDollar(double amount) {
        double amountInDollars = amount/16;
        String res =String.format("%.2f", amountInDollars);
        return Double.parseDouble(res);
    }
}
