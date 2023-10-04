package com.example.demo.model.qc.hbs;

import com.example.demo.model.qc.hcv.Lot;
import com.example.demo.model.qc.hcv.Test;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "hbsAgmeasure", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class HBsAgMeasure {
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
    @JoinColumn(name = "hbsAglot_fk")
    @JsonBackReference(value = "measure-lot")
    private HBsAgLot lot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hbsAgtest_fk")
    @JsonBackReference(value = "measure-test")
    private HBsAgTest test;

    public HBsAgMeasure() {
    }

    public HBsAgMeasure(Long id, String measure_type, LocalDateTime measure_date, String measure_val, HBsAgLot lot, HBsAgTest test) {
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

    public HBsAgLot getLot() {
        return lot;
    }

    public void setLot(HBsAgLot lot) {
        this.lot = lot;
    }

    public HBsAgTest getTest() {
        return test;
    }

    public void setTest(HBsAgTest test) {
        this.test = test;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HBsAgMeasure measure = (HBsAgMeasure) o;
        return getId().equals(measure.getId()) && Objects.equals(getMeasure_type(), measure.getMeasure_type()) && Objects.equals(getMeasure_date(), measure.getMeasure_date());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMeasure_type(), getMeasure_date());
    }

    @Override
    public String toString() {
        return "HBsAgMeasure{" +
                "id=" + id +
                ", measure_type='" + measure_type + '\'' +
                ", measure_date=" + measure_date +
                ", measure_val='" + measure_val + '\'' +
                ", lot=" + lot.getLot() +
                ", test=" + test.getLot() +
                '}';
    }
}

