package com.example.demo.model.real;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Root {

    @JsonProperty("readerName")
    private String readerName;

    @JsonProperty("readerSerial")
    private String readerSerial;

    @JsonProperty("methodCode")
    private String methodCode;

    @JsonProperty("worksheetName")
    private String worksheetName;

    @JsonProperty("readOn")
    private String readOn;

    @JsonProperty("createdOn")
    private String createdOn;

    @JsonProperty("kitSeries")
    private String kitSeries;

    @JsonProperty("kitExpiryOn")
    private String kitExpiryOn;

    @JsonProperty("samples")
    private List<Sample> samples;

    @JsonProperty("controls")
    private List<Control> controls;

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getReaderSerial() {
        return readerSerial;
    }

    public void setReaderSerial(String readerSerial) {
        this.readerSerial = readerSerial;
    }

    public String getMethodCode() {
        return methodCode;
    }

    public void setMethodCode(String methodCode) {
        this.methodCode = methodCode;
    }

    public String getWorksheetName() {
        return worksheetName;
    }

    public void setWorksheetName(String worksheetName) {
        this.worksheetName = worksheetName;
    }

    public String getReadOn() {
        return readOn;
    }

    public void setReadOn(String readOn) {
        this.readOn = readOn;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getKitSeries() {
        return kitSeries;
    }

    public void setKitSeries(String kitSeries) {
        this.kitSeries = kitSeries;
    }

    public String getKitExpiryOn() {
        return kitExpiryOn;
    }

    public void setKitExpiryOn(String kitExpiryOn) {
        this.kitExpiryOn = kitExpiryOn;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    public List<Control> getControls() {
        return controls;
    }

    public void setControls(List<Control> controls) {
        this.controls = controls;
    }

    @Override
    public String toString() {
        return "Root{" +
                "readerName='" + readerName + '\'' +
                ", readerSerial='" + readerSerial + '\'' +
                ", methodCode='" + methodCode + '\'' +
                ", worksheetName='" + worksheetName + '\'' +
                ", readOn='" + readOn + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", kitSeries='" + kitSeries + '\'' +
                ", kitExpiryOn='" + kitExpiryOn + '\'' +
                ", samples=" + samples +
                ", controls=" + controls +
                '}';
    }
}
