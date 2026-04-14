package com.example.demo.model.real;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Formula {

        @JsonProperty("key")
        private String key;

        @JsonProperty("name")
        private String name;

        @JsonProperty("value")
        private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Formula formula = (Formula) o;
        return Objects.equals(getKey(), formula.getKey()) && Objects.equals(getName(), formula.getName()) && Objects.equals(getValue(), formula.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getName(), getValue());
    }

    @Override
    public String toString() {
        return "Formula{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
