package com.example.dutyandtax.apiData;

import com.example.dutyandtax.apiData.requestData.RequestGoods;
import com.example.dutyandtax.apiData.requestData.RequestPrice;
import com.example.dutyandtax.apiData.requestData.RequestRoot;
import com.example.dutyandtax.testData.CurrenciesList;

import java.util.ArrayList;
import java.util.UUID;

public class TestRequestAndResponseGenerator {
    
    private final ArrayList<RequestRoot> requestRoot;

    public TestRequestAndResponseGenerator() {
        this.requestRoot = generateOkRequestRoot();
    }
    
    private ArrayList<RequestRoot> generateOkRequestRoot() {
        ArrayList<RequestRoot> requestRootList = new ArrayList<>();
        RequestRoot requestRoot = new RequestRoot("123e4567-e89b-12d3-a456-426655440000", CurrenciesList.returnRandomCurrency().toString(),
                5000, 100, 15.55, "CN",
                "CA", "CA-MB", "MANUAL", generateOkRequestGoods());
        requestRootList.add(0, requestRoot);
        return requestRootList;
    }

    private ArrayList<RequestGoods> generateOkRequestGoods() {
        ArrayList<RequestGoods> goods = new ArrayList<>();
        String stuff = "Some stuff";
        RequestGoods rGoods = new RequestGoods("123e4567-e89b-12d3-a456-426655440000", stuff, stuff, generateOkRequestPrice());
        goods.add(0, rGoods);
        return goods;
    }

    private RequestPrice generateOkRequestPrice() {
        return new RequestPrice(CurrenciesList.returnRandomCurrency().toString(), 100);
    }

    public ArrayList<RequestRoot> getRequestRoot() {
        return requestRoot;
    }
}
