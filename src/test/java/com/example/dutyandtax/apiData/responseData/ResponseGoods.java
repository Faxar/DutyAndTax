package com.example.dutyandtax.apiData.responseData;

import java.util.ArrayList;

public class ResponseGoods {

    private String externalId;
    private String description;
    private double vat;
    private double duty;
    private double vatRate;
    private double dutyRate;
    private int hsCodeCorrectness;
    private String calculatedHsCode;
    private ArrayList<String> calculatedHsCodeRestrictions;
    private ArrayList<String> providedHsCodeRestrictions;

    public ResponseGoods() {
    }

    public ResponseGoods(String externalId, String description, double vat, double duty, double vatRate, double dutyRate, int hsCodeCorrectness, String calculatedHsCode, ArrayList<String> calculatedHsCodeRestrictions, ArrayList<String> providedHsCodeRestrictions) {
        this.externalId = externalId;
        this.description = description;
        this.vat = vat;
        this.duty = duty;
        this.vatRate = vatRate;
        this.dutyRate = dutyRate;
        this.hsCodeCorrectness = hsCodeCorrectness;
        this.calculatedHsCode = calculatedHsCode;
        this.calculatedHsCodeRestrictions = calculatedHsCodeRestrictions;
        this.providedHsCodeRestrictions = providedHsCodeRestrictions;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getDescription() {
        return description;
    }

    public double getVat() {
        return vat;
    }

    public double getDuty() {
        return duty;
    }

    public double getVatRate() {
        return vatRate;
    }

    public double getDutyRate() {
        return dutyRate;
    }

    public int getHsCodeCorrectness() {
        return hsCodeCorrectness;
    }

    public String getCalculatedHsCode() {
        return calculatedHsCode;
    }

    public ArrayList<String> getCalculatedHsCodeRestrictions() {
        return calculatedHsCodeRestrictions;
    }

    public ArrayList<String> getProvidedHsCodeRestrictions() {
        return providedHsCodeRestrictions;
    }
}
