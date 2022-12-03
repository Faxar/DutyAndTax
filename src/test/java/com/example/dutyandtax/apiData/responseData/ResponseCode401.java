package com.example.dutyandtax.apiData.responseData;

public class ResponseCode401 {

    public Integer status;

    public String message;

    public ResponseCode401(Integer status, String responseMessage) {
        this.status = status;
        this.message = responseMessage;
    }

    public ResponseCode401() {
    }
    public String getResponseMessage() {
        return message;
    }
}
