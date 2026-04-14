package com.example.demo.model.real;

public class Parameters {

    private String fileName;
    private String controlType;
    private String controlName;
    private String controlMarker;
    private String sampleMarker;
    private String vlcName;
    private boolean checkControl;
    private InterMeasure measure;

    public Parameters(String fileName, String controlType, String controlName, String controlMarker, String sampleMarker, String vlcName, boolean checkControl, InterMeasure measure) {
        this.fileName = fileName;
        this.controlType = controlType;
        this.controlName = controlName;
        this.controlMarker = controlMarker;
        this.sampleMarker = sampleMarker;
        this.vlcName = vlcName;
        this.checkControl = checkControl;
        this.measure = measure;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public String getControlMarker() {
        return controlMarker;
    }

    public void setControlMarker(String controlMarker) {
        this.controlMarker = controlMarker;
    }

    public String getSampleMarker() {
        return sampleMarker;
    }

    public void setSampleMarker(String sampleMarker) {
        this.sampleMarker = sampleMarker;
    }

    public boolean isCheckControl() {
        return checkControl;
    }

    public void setCheckControl(boolean checkControl) {
        this.checkControl = checkControl;
    }

    public String getVlcName() {
        return vlcName;
    }

    public void setVlcName(String vlcName) {
        this.vlcName = vlcName;
    }

    public InterMeasure getMeasure() {
        return measure;
    }

    public void setMeasure(InterMeasure measure) {
        this.measure = measure;
    }
}
