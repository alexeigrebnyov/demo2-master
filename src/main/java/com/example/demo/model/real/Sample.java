package com.example.demo.model.real;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class Sample {

    @JsonProperty("sequence")
    private Integer sequence;

    @JsonProperty("index")
    private Integer index;

    @JsonProperty("barcode")
    private String barcode;

    @JsonProperty("formulas")
    private List<Formula> formulas;

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public List<Formula> getFormulas() {
        return formulas;
    }

    public void setFormulas(List<Formula> formulas) {
        this.formulas = formulas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sample sample = (Sample) o;
        return Objects.equals(getSequence(), sample.getSequence()) && Objects.equals(getBarcode(), sample.getBarcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSequence(), getBarcode());
    }

    @Override
    public String toString() {
        return "Sample{" +
                "sequence=" + sequence +
                ", index=" + index +
                ", barcode='" + barcode + '\'' +
                ", formulas=" + formulas +
                '}';
    }
}
