package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "assignment")
public class Assignment {
    @Column(name = "name")
    private String name;
    @Column(name = "date")
    private String date;
    @Column(name = "pat")
    private String pat;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commonAnalysis_id")

    private CommonAnalysis commonAnalysis;
    @Id
    @GeneratedValue
    private Long id;

    public Assignment() {
    }


    public Assignment(String name, String date, String pat, CommonAnalysis commonAnalysis) {
        this.name = name;
        this.date = date;
        this.pat = pat;
        this.commonAnalysis=commonAnalysis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPat() {
        return pat;
    }

    public void setPat(String pat) {
        this.pat = pat;
    }

    public CommonAnalysis getCommonAnalysis() {
        return commonAnalysis;
    }

    public void setCommonAnalysis(CommonAnalysis commonAnalysis) {
        this.commonAnalysis = commonAnalysis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", pat='" + pat + '\'' +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
