package com.example.demo.service;

import com.example.demo.dao.HIVAbTestRepo;
import com.example.demo.dao.HIVAgTestRepo;
import com.example.demo.model.qc.hiv.HIVAbTest;
import com.example.demo.model.qc.hiv.HIVAgTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HIVAgTestService {
    HIVAgTestRepo testRepo;
    @Autowired
    public HIVAgTestService(HIVAgTestRepo testRepo) {
        this.testRepo = testRepo;
    }
    public void save(HIVAgTest test) {
        testRepo.save(test);
    }

    public HIVAgTest getByUse(String in_use, String name) {
       return testRepo.findDistinctByInuseAndName(in_use, name).orElseThrow();
    }

    public HIVAgTest findByNameAndLotAndNumber(String name, String lot, int number) {
       return testRepo.findByNameAndLotAndNumber(name, lot, number);
    }

    public HIVAgTest findByInuse(String inuse) {
        return testRepo.findByInuse(inuse);
    }
    public List<HIVAgTest> getAllByInuse(String inuse) {
        return testRepo.findAllByInuse(inuse);
    }

    public HIVAgTest getTopByLotOrderByNumberDesc(String lot) {
        return testRepo.getTopByLotOrderByNumberDesc(lot);
    }
}
