package com.example.demo.model.real;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Control {

    @JsonProperty("sequence")
    private Integer sequence;

    @JsonProperty("index")
    private Integer index;

    @JsonProperty("type")
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Formula> getFormulas() {
        return formulas;
    }

    public void setFormulas(List<Formula> formulas) {
        this.formulas = formulas;
    }


    @Override
    public String toString() {
        return "Control{" +
                "sequence=" + sequence +
                ", index=" + index +
                ", type='" + type + '\'' +
                ", formulas=" + formulas +
                '}';
    }
}
