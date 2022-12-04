package com.example.dutyandtax.apiData.requestData;

import java.time.LocalDate;
import java.util.ArrayList;

public class RequestRoot {
    public String externalId;
    public String orderCurrency;
    public double transportationPrice;
    private final int insurancePrice;
    private final double otherAdditionalCosts;
    private final String originCountry;
    private final String destinationCountry;
    private final String destinationRegion;
    private String additionalValueShare;
    private final ArrayList<RequestGoods> goods;
    private final String date;
    private final boolean useDeMinimis;
    private final boolean hsCodeReplaceAllowed;

    public RequestRoot(String externalId, String orderCurrency, double transportationPrice, int insurancePrice, double otherAdditionalCosts, String originCountry, String destinationCountry, String destinationRegion, String additionalValueShare, ArrayList<RequestGoods> goods) {
        this.externalId = externalId;
        this.orderCurrency = orderCurrency;
        this.transportationPrice = transportationPrice;
        this.insurancePrice = insurancePrice;
        this.otherAdditionalCosts = otherAdditionalCosts;
        this.originCountry = originCountry;
        this.destinationCountry = destinationCountry;
        this.destinationRegion = destinationRegion;
        this.additionalValueShare = additionalValueShare;
        this.goods = goods;
        this.date = LocalDate.now().toString();
        this.useDeMinimis = true;
        this.hsCodeReplaceAllowed = true;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getOrderCurrency() {
        return orderCurrency;
    }

    public double getTransportationPrice() {
        return transportationPrice;
    }

    public int getInsurancePrice() {
        return insurancePrice;
    }

    public double getOtherAdditionalCosts() {
        return otherAdditionalCosts;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public String getDestinationRegion() {
        return destinationRegion;
    }

    public String getAdditionalValueShare() {
        return additionalValueShare;
    }

    public ArrayList<RequestGoods> getGoods() {
        return goods;
    }

    public String getDate() {
        return date;
    }

    public boolean isUseDeMinimis() {
        return useDeMinimis;
    }

    public boolean isHsCodeReplaceAllowed() {
        return hsCodeReplaceAllowed;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public void setOrderCurrency(String orderCurrency) {
        this.orderCurrency = orderCurrency;
    }

    public void setTransportationPrice(double transportationPrice) {
        this.transportationPrice = transportationPrice;
    }

    public void setAdditionalValueShare(String addValueShare) {
        this.additionalValueShare = addValueShare;
    }
}
