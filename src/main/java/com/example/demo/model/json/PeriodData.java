package com.example.demo.model.json;

public class PeriodData {
   public int filial;
   public int from;
   public int to;

    public PeriodData() {
    }

    public PeriodData(int filial, int from, int to) {
        this.filial = filial;
        this.from = from;
        this.to = to;
    }

    public int getFilial() {
        return filial;
    }

    public void setFilial(int filial) {
        this.filial = filial;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

}
