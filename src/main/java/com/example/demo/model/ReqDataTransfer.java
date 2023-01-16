package com.example.demo.model;

public class ReqDataTransfer {
    private int id;
    private String fio;
    private String result;
    private String label;
    private String date_bio;
    private int lab_methods_id;
    private int bio_code;
    private int sex;
    private String adres;
    private int patdirec;

    public ReqDataTransfer(int id, String fio, String result, String label, String date_bio, int lab_methods_id, int bio_code, int sex, String adres, int patdirec) {
        this.id = id;
        this.fio = fio;
        this.result = result;
        this.label = label;
        this.date_bio = date_bio;
        this.lab_methods_id = lab_methods_id;
        this.bio_code = bio_code;
        this.sex = sex;
        this.adres = adres;
        this.patdirec = patdirec;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDate_bio() {
        return date_bio;
    }

    public void setDate_bio(String date_bio) {
        this.date_bio = date_bio;
    }

    public int getLab_methods_id() {
        return lab_methods_id;
    }

    public void setLab_methods_id(int lab_methods_id) {
        this.lab_methods_id = lab_methods_id;
    }

    public int getBio_code() {
        return bio_code;
    }

    public void setBio_code(int bio_code) {
        this.bio_code = bio_code;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public int getPatdirec() {
        return patdirec;
    }

    public void setPatdirec(int patdirec) {
        this.patdirec = patdirec;
    }

    @Override
    public String toString() {
        return "ReqDataTransfer{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", result='" + result + '\'' +
                ", label='" + label + '\'' +
                ", date_bio='" + date_bio + '\'' +
                ", lab_methods_id=" + lab_methods_id +
                ", bio_code=" + bio_code +
                ", sex=" + sex +
                ", adres='" + adres + '\'' +
                ", patdirec=" + patdirec +
                '}';
    }
}
