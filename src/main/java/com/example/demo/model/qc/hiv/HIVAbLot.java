package com.example.demo.model.qc.hiv;

import com.example.demo.model.qc.hbs.HBsAgMeasure;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "hivAblot", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class HIVAbLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String lot;
    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateFrom;
    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateTo;



    @Column
    private int number;
    @OneToMany(fetch = FetchType.LAZY, mappedBy ="lot", cascade = CascadeType.DETACH)
    @JsonManagedReference(value = "measure-lot")
    private List<HIVAbMeasure> measureList;
    @Column
    private String inuse;

    public HIVAbLot() {
    }

    public HIVAbLot(Long id, String name, String lot, LocalDateTime dateFrom, LocalDateTime dateTo, int number, List<HIVAbMeasure> measureList,
                    String inuse) {
        this.id = id;
        this.name = name;
        this.lot = lot;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.number = number;
        this.measureList = measureList;
        this.inuse = inuse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<HIVAbMeasure> getMeasureList() {
        return measureList;
    }

    public void setMeasureList(List<HIVAbMeasure> measureList) {
        this.measureList = measureList;
    }

    public String getInuse() {
        return inuse;
    }

    public void setInuse(String inuse) {
        this.inuse = inuse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HIVAbLot lot1 = (HIVAbLot) o;
        return getId().equals(lot1.getId()) && Objects.equals(getName(), lot1.getName()) && Objects.equals(getLot(), lot1.getLot());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getLot());
    }

    @Override
    public String toString() {
        return "HIVAbLot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lot='" + lot + '\'' +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", number=" + number +
                ", measureList=" + measureList +
                ", inuse=" + inuse +
                '}';
    }
}
