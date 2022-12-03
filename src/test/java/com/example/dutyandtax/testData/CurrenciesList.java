package com.example.dutyandtax.testData;

import java.util.Random;
public enum CurrenciesList {
    EUR, DZD, USD, XCD, AUD, JPY;
    private static final Random RND = new Random();
    public static CurrenciesList returnRandomCurrency()  {
        CurrenciesList[] currencies = values();
        return currencies[RND.nextInt(currencies.length)];
    }

}
