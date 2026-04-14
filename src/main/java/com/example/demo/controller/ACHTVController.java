package com.example.demo.controller;

import com.example.demo.dao.UptakeDao;
import com.example.demo.model.qc.CounterByValue;
import com.example.demo.model.qc.GlukozaExpress;
import com.example.demo.model.qc.achtv.AchtvCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/achtv")
public class ACHTVController {
    @Autowired
    UptakeDao uptakeDao;
    @PostMapping("/data")
    public List<AchtvCounter> getACHTVData(@RequestBody String end) throws SQLException {
        return uptakeDao.getACHTVData(end);
    }


    @PostMapping("/byValue")
    public  List<CounterByValue> getACHTVDataByValue(@RequestBody String[] end) throws SQLException{
        return uptakeDao.getACHTVDataByValue(end[0],end[1],end[2]);
    }

    @PostMapping("/pTime")
    public  List<CounterByValue> getPtimeDataByValue(@RequestBody String[] end) throws SQLException{
        return uptakeDao.getPtimeDataByValue(end[0],end[1],end[2]);
    }

    @PostMapping("/fibr")
    public  List<CounterByValue> getFibrDataByValue(@RequestBody String[] end) throws SQLException{
        return uptakeDao.getFibrDataByValue(end[0],end[1],end[2]);
    }

    @PostMapping("/glukozaExpress")
    public  List<GlukozaExpress> getGlukozaExpressValue(@RequestBody String[] end) throws SQLException{
        return uptakeDao.getGlukozaExpressValue(end[0],end[1],end[2]);
    }

    @PostMapping("/testFragmentacii")
    public  List<GlukozaExpress> getTestFragmentaciiValue(@RequestBody String[] end) throws SQLException{
        return uptakeDao.getTestFragmentaciiValue(end[0],end[1],end[2]);
    }
}
