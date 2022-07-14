package com.example.demo.model;

import java.util.Map;


public class CommonAnalysis {
    private String emc;
    private String fio;
    private String code;
    private String date_bio;
    private String label;
    private Map<String, String> assignments;

    public CommonAnalysis() {
    }

    public CommonAnalysis(String emc, String fio, String code, String date_bio, String label, Map<String, String> assignments) {
        this.emc = emc;
        this.fio = fio;
        this.code = code;
        this.date_bio = date_bio;
        this.label = label;
        this.assignments = assignments;
    }

    public String getEmc() {
        return emc;
    }

    public void setEmc(String emc) {
        this.emc = emc;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate_bio() {
        return date_bio;
    }

    public void setDate_bio(String date_bio) {
        this.date_bio = date_bio;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Map<String, String> getAssignments() {
        return assignments;
    }

    public void setAssignments(Map<String, String> assignments) {
        this.assignments = assignments;
    }

    @Override
    public String toString() {
        return "CommonAnalysis{" +
                "emc='" + emc + '\'' +
                ", fio='" + fio + '\'' +
                ", code='" + code + '\'' +
                ", date_bio='" + date_bio + '\'' +
                ", label='" + label + '\'' +
                ", assignments=" + assignments +
                '}';
    }
}
