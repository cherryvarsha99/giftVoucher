package com.project.androidtuts.callbacks.utils;

import java.text.DecimalFormat;

/**
 * Created by Maureen Sindiso Mpofu  on 18/10/2020.
 */
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
