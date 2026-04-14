package com.example.demo.model.qc.protein;

import com.example.demo.model.qc.hbs.HBsAgLot;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "urine")
public class Urine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime measure_date;

    @Column
    private double measure_val;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "k1lot_fk")
    @JsonBackReference(value = "measure-lot")
    private K1Lot lot;

    @Column
    private int org;

    public Urine() {
    }

    public Urine(LocalDateTime measure_date, double measure_val, K1Lot lot, int org) {
        this.measure_date = measure_date;
        this.measure_val = measure_val;
        this.lot = lot;
        this.org = org;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getMeasure_date() {
        return measure_date;
    }

    public void setMeasure_date(LocalDateTime measure_date) {
        this.measure_date = measure_date;
    }

    public double getMeasure_val() {
        return measure_val;
    }

    public void setMeasure_val(double measure_val) {
        this.measure_val = measure_val;
    }

    public K1Lot getLot() {
        return lot;
    }

    public void setLot(K1Lot lot) {
        this.lot = lot;
    }

    public int getOrg() {
        return org;
    }

    public void setOrg(int org) {
        this.org = org;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Urine urine = (Urine) o;
        return getOrg() == urine.getOrg() && getId().equals(urine.getId()) && getMeasure_date().equals(urine.getMeasure_date()) && lot.equals(urine.lot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMeasure_date(), lot, getOrg());
    }

    @Override
    public String toString() {
        return "Urine{" +
                "id=" + id +
                ", measure_date=" + measure_date +
                ", measure_val=" + measure_val +
                ", lot=" + lot +
                ", org=" + org +
                '}';
    }
}
