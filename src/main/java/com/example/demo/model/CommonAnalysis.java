package com.example.demo.model;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "commonAnalysis")
public class CommonAnalysis {
    @Column(name = "emc")
    private String emc;
    @Column(name = "fio")
    private String fio;
    @Column(name = "code")
    private String code;
    @Column(name = "date_bio")
    private String date_bio;
    @Column(name = "label")
    private String label;



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "commonAnalysis", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Assignment> assignments;
    @Id
    @GeneratedValue
    private Long id;

    public CommonAnalysis() {
    }

    public CommonAnalysis(String emc, String fio, String code, String date_bio, String label, Set<Assignment> assignments) {
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

    public Set<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<Assignment> assignments) {
        this.assignments = assignments;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonAnalysis that = (CommonAnalysis) o;
        return Objects.equals(emc, that.emc) && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emc, code);
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
