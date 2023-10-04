package com.example.demo.model.qc.achtv;

import java.time.LocalDate;

public class AchtvCounter {
    private LocalDate dateCons;
    private int total;
    private int higher;
    private String filial;

    public AchtvCounter(LocalDate dateCons, int total, int higher, String filial) {
        this.dateCons = dateCons;
        this.total = total;
        this.higher = higher;
        this.filial = filial;
    }

    public LocalDate getDateCons() {
        return dateCons;
    }

    public void setDateCons(LocalDate dateCons) {
        this.dateCons = dateCons;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getHigher() {
        return higher;
    }

    public void setHigher(int higher) {
        this.higher = higher;
    }

    public String getFilial() {
        return filial;
    }

    public void setFilial(String filial) {
        this.filial = filial;
    }

    @Override
    public String toString() {
        return "AchtvCounter{" +
                "dateCons=" + dateCons +
                ", total=" + total +
                ", higher=" + higher +
                ", filial='" + filial + '\'' +
                '}';
    }
}
