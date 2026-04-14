package com.example.demo.service;

import com.example.demo.dao.AMGTestRepo;
import com.example.demo.dao.HydroTestRepo;
import com.example.demo.model.qc.amg.AMGTest;
import com.example.demo.model.qc.hydroxyprog.HydroTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HydroTestService {
    HydroTestRepo testRepo;
    @Autowired
    public HydroTestService(HydroTestRepo testRepo) {
        this.testRepo = testRepo;
    }
    public void save(HydroTest test) {
        testRepo.save(test);
    }

    public HydroTest getByUse(String in_use, String name) {
       return testRepo.findDistinctByInuseAndName(in_use, name).orElseThrow();
    }

    public HydroTest findByNameAndLotAndNumber(String name, String lot, int number) {
       return testRepo.findByNameAndLotAndNumber(name, lot, number);
    }

    public HydroTest findByInuse(String inuse) {
        return testRepo.findByInuse(inuse);
    }
    public List<HydroTest> getAllByInuse(String inuse) {
        return testRepo.findAllByInuse(inuse);
    }

    public HydroTest getTopByLotOrderByNumberDesc(String lot) {
        return testRepo.getTopByLotOrderByNumberDesc(lot);
    }
}
