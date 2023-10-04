package com.example.demo.model.qc.hiv;

import com.example.demo.model.qc.hbs.HBsAgLot;
import com.example.demo.model.qc.hbs.HBsAgTest;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "hivAbmeasure", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class HIVAbMeasure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String measure_type;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime measure_date;
    @Column
    private String measure_val;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hivAblot_fk")
    @JsonBackReference(value = "measure-lot")
    private HIVAbLot lot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hivAbtest_fk")
    @JsonBackReference(value = "measure-test")
    private HIVAbTest test;

    public HIVAbMeasure() {
    }

    public HIVAbMeasure(Long id, String measure_type, LocalDateTime measure_date, String measure_val, HIVAbLot lot, HIVAbTest test) {
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

    public LocalDateTime getMeasure_date() {
        return measure_date;
    }

    public void setMeasure_date(LocalDateTime measure_date) {
        this.measure_date = measure_date;
    }

    public String getMeasure_val() {
        return measure_val;
    }

    public void setMeasure_val(String measure_val) {
        this.measure_val = measure_val;
    }

    public HIVAbLot getLot() {
        return lot;
    }

    public void setLot(HIVAbLot lot) {
        this.lot = lot;
    }

    public HIVAbTest getTest() {
        return test;
    }

    public void setTest(HIVAbTest test) {
        this.test = test;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HIVAbMeasure measure = (HIVAbMeasure) o;
        return getId().equals(measure.getId()) && Objects.equals(getMeasure_type(), measure.getMeasure_type()) && Objects.equals(getMeasure_date(), measure.getMeasure_date());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMeasure_type(), getMeasure_date());
    }

    @Override
    public String toString() {
        return "HIVAbMeasure{" +
                "id=" + id +
                ", measure_type='" + measure_type + '\'' +
                ", measure_date=" + measure_date +
                ", measure_val='" + measure_val + '\'' +
                ", lot=" + lot.getLot() +
                ", test=" + test.getLot() +
                '}';
    }
}

