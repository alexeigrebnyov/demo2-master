package com.example.demo.service;

import com.example.demo.dao.HBsAgTestRepo;
import com.example.demo.dao.HIVAbTestRepo;
import com.example.demo.model.qc.hbs.HBsAgTest;
import com.example.demo.model.qc.hiv.HIVAbTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HIVAbTestService {
    HIVAbTestRepo testRepo;
    @Autowired
    public HIVAbTestService(HIVAbTestRepo testRepo) {
        this.testRepo = testRepo;
    }
    public void save(HIVAbTest test) {
        testRepo.save(test);
    }

    public HIVAbTest getByUse(String in_use, String name) {
       return testRepo.findDistinctByInuseAndName(in_use, name).orElseThrow();
    }

    public HIVAbTest findByNameAndLotAndNumber(String name, String lot, int number) {
       return testRepo.findByNameAndLotAndNumber(name, lot, number);
    }

    public HIVAbTest findByInuse(String inuse) {
        return testRepo.findByInuse(inuse);
    }
    public List<HIVAbTest> getAllByInuse(String inuse) {
        return testRepo.findAllByInuse(inuse);
    }

    public HIVAbTest getTopByLotOrderByNumberDesc(String lot) {
        return testRepo.getTopByLotOrderByNumberDesc(lot);
    }
}
