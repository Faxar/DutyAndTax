package com.example.dutyandtax.apiData.requestData;

public class RequestPrice {
    private String currency;
    private double value;

    public RequestPrice(String currency, double value) {
        this.currency = currency;
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public double getValue() {
        return value;
    }
}
