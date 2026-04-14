package com.example.demo.model.real;

import com.example.demo.model.qc.amg.AMGMeasure;
import com.example.demo.model.qc.hbs.HBsAgMeasure;
import com.example.demo.model.qc.hcv.Measure;
import com.example.demo.model.qc.hiv.HIVAbMeasure;
import com.example.demo.model.qc.hydroxyprog.HydroMeasure;
import com.example.demo.model.qc.syph.SyphMeasure;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static String directory = "\\\\192.168.7.100\\ifa\\";

    public static Map<String, Parameters> params = getParams();

    public static Map<String, Parameters> getParams() {
        Map<String, Parameters> p = new HashMap<>();
        p.put("24310", new Parameters("ВИЧ", "VLC", "tube_name",
                "optical_density", "Результат_Ti", "HIVAb", true, new HIVAbMeasure()));
        p.put("2560", new Parameters("rubM", "VLC", "tube_name",
                "optical_density", "Результат_Ti", "", false, new HIVAbMeasure()));
        p.put("0773", new Parameters("Гепатит С", "VLC", "tube_name",
                "optical_density", "КП_Ti", "anti_HCV", true, new Measure()));
        p.put("0557", new Parameters("Гепатит В", "VLC", "tube_name",
                "optical_density", "Результат_Ti", "HBsAg", true, new HBsAgMeasure()));
        p.put("323", new Parameters("СИФИЛИС", "VLC", "tube_name",          "optical_density",
                "КП_Ti",  "SyphIFA",true, new SyphMeasure()));
        p.put("2552", new Parameters("rubG", "VLC", "tube_name",
                "optical_density", "Концентрация", "", false, new Measure()));
        p.put("79765", new Parameters("AMG", "S", "tube_name",
                "optical_density", "Концентрация", "AMG", true, new AMGMeasure()));
        p.put("1982", new Parameters("хламG", "VLC", "tube_name",
                "optical_density", "Результат_Ti", "хламG", false, new AMGMeasure()));
        p.put("1968", new Parameters("хламA", "VLC", "tube_name",
                "optical_density", "Результат_Ti", "хламA", false, new AMGMeasure()));
        p.put("1972", new Parameters("хламHSP", "VLC", "tube_name",
                "optical_density", "Результат_Ti", "хламHSP", false, new AMGMeasure()));

        p.put("100 31", new Parameters("17-OH", "S", "optical_density",
                "optical_density", "Концентрация", "17-OH", false, new HydroMeasure()));
        System.out.println("params completed!!!");
        return p;
    }
}
