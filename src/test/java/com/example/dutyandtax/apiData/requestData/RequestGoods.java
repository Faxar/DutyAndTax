package com.example.dutyandtax.apiData.requestData;

public class RequestGoods {
    private String externalId;
    private String gtin;
    private String title;
    private String description;
    private String hsCode;
    private RequestPrice price;
    private double weight;
    private int quantity;
    private int additionalValueShareRatio;

    public RequestGoods() {
    }

    public RequestGoods(String externalId, String title, String description, RequestPrice price) {
        this.externalId = externalId;
        this.gtin = "00012345600012";
        this.title = title;
        this.description = description;
        this.hsCode = "0101000000";
        this.price = price;
        this.weight = 0.15;
        this.quantity = 200;
        this.additionalValueShareRatio = 1;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getGtin() {
        return gtin;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getHsCode() {
        return hsCode;
    }

    public RequestPrice getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAdditionalValueShareRatio() {
        return additionalValueShareRatio;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
