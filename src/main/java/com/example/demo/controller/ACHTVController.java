package com.example.demo.controller;

import com.example.demo.dao.UptakeDao;
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
}
