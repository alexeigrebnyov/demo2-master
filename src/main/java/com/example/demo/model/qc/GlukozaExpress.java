package com.example.demo.model.qc;

import java.time.LocalDate;

public class GlukozaExpress {
    private LocalDate dateAnaliz;
    private float valueAnaliz;
    private String filial;
    private String serialNumber;
    public GlukozaExpress(LocalDate dateAnaliz, float valueAnaliz, String filial, String serialNumber){
        this.dateAnaliz = dateAnaliz;
        this.valueAnaliz = valueAnaliz;
        this.filial = filial;
        this.serialNumber = serialNumber; /* для теста фрагментации это ФИО сотрудника*/
    }
    public LocalDate getDateAnaliz(){return dateAnaliz;}
    public void setDateAnaliz(LocalDate dateAnaliz) {this.dateAnaliz = dateAnaliz;}
    public float getValueAnaliz(){return valueAnaliz;}
    public void setValueAnaliz(float valueAnaliz){this.valueAnaliz=valueAnaliz;}
    public String getFilial(){return filial;}
    public void setFilial(String filial){this.filial=filial;}
    public String getSerialNumber(){return serialNumber;}
    public void setSerialNumber(String serialNumber){this.serialNumber=serialNumber;}

    @Override
    public String toString() {
        return "GlukozaExpress{" +
                "dateAnaliz=" + dateAnaliz +
                ", valueAnaliz=" +  valueAnaliz +
                ", filial='" + filial +
                ", serialNumber='" + serialNumber    + '\'' +
                '}';
    }
}
