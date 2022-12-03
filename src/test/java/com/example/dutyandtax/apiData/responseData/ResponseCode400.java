package com.example.dutyandtax.apiData.responseData;

import java.util.ArrayList;

public class ResponseCode400 {
    public String type;
    public ArrayList<Row> rows;

    public static class Row {
        public String field;
        public String reason;
        public String message;
    }

    public ResponseCode400(String type, ArrayList<Row> rows) {
        this.type = type;
        this.rows = rows;
    }

    public ResponseCode400() {
    }

    public String getType() {
        return type;
    }

    public ArrayList<Row> getRows() {
        return rows;
    }
}
