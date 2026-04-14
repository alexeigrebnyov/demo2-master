package com.example.demo.model.dto;


public class AMGMeasureDTO {

    private Long id;

    private String measure_type;


    private String measure_date;

    private String measure_val;

    private String lot;

    private String test;

    public AMGMeasureDTO() {
    }

    public AMGMeasureDTO(Long id, String measure_type, String measure_date, String measure_val, String lot, String test) {
        this.id = id;
        this.measure_type = measure_type;
        this.measure_date = measure_date;
        this.measure_val = measure_val;
        this.lot = lot;
        this.test = test;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeasure_type() {
        return measure_type;
    }

    public void setMeasure_type(String measure_type) {
        this.measure_type = measure_type;
    }

    public String getMeasure_date() {
        return measure_date;
    }

    public void setMeasure_date(String measure_date) {
        this.measure_date = measure_date;
    }

    public String getMeasure_val() {
        return measure_val;
    }

    public void setMeasure_val(String measure_val) {
        this.measure_val = measure_val;
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

    @Override
    public String toString() {
        return "AMGMeasureDTO{" +
                "id=" + id +
                ", measure_type='" + measure_type + '\'' +
                ", measure_date='" + measure_date + '\'' +
                ", measure_val='" + measure_val + '\'' +
                ", lot='" + lot + '\'' +
                ", test='" + test + '\'' +
                '}';
    }
}

