package com.example.demo.model.json;

public class CriteriaData {
    private String lot;
    private String test;
    private String date;

    public CriteriaData(String lot, String test, String date) {
        this.lot = lot;
        this.test = test;
        this.date = date;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CriteriaData{" +
                "lot='" + lot + '\'' +
                ", test='" + test + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
