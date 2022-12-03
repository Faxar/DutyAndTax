package com.example.dutyandtax.apiData.responseData;

import java.util.ArrayList;

public class ResponseRoot {

    private String externalId;
    private double totalVat;
    private double totalDuties;
    private ArrayList<ResponseGoods> goods;

    public ResponseRoot() {
    }

    public ResponseRoot(String externalId, ArrayList<ResponseGoods> goods) {
        this.externalId = externalId;
        this.goods = goods;
    }

    public String getExternalId() {
        return externalId;
    }

    public double getTotalVat() {
        return totalVat;
    }

    public double getTotalDuties() {
        return totalDuties;
    }

    public ArrayList<ResponseGoods> getGoods() {
        return goods;
    }
}
