package com.example.demo.model.qc;

import java.time.LocalDate;

public class CounterByValue {
//Класс в который загружаются данные из медиалога
    private LocalDate dateCons;
    private float valueParam;
    private String analizator;

    public CounterByValue(LocalDate dateCons, float valueParam, String analizator ) {
        this.dateCons = dateCons;
        this.valueParam= valueParam;
        this.analizator = analizator;
    }

    public LocalDate getDateCons() {
        return dateCons;
    }

    public void setDateCons(LocalDate dateCons) {
        this.dateCons = dateCons;
    }

    public float getvalueParam() {
        return valueParam;
    }

    public void setvalueParam(float valueParam) {
        this.valueParam = valueParam;
    }

    public String getAnalizator() {return analizator;}

    public void setAnalizator(String analizator) {
        this.analizator = analizator;
    }

    @Override
    public String toString() {
        return "AchtvCounter{" +
                "dateCons=" + dateCons +
                ", valueACHTV=" +  valueParam +
                ", analizator='" + analizator + '\'' +
                '}';
    }
}
