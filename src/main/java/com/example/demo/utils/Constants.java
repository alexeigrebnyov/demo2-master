package com.example.demo.utils;

public class Constants {
    public static String SERVERENDPOINT="192.168.30.104:8099";
//    public static String SERVERENDPOINT="localhost:8099";

    public Constants() {
    }

    public static void setSERVERENDPOINT(String SERVERENDPOINT) {
        Constants.SERVERENDPOINT = SERVERENDPOINT;
    }
}
