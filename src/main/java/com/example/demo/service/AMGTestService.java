package com.example.demo.service;

import com.example.demo.dao.AMGTestRepo;
import com.example.demo.dao.HIVAbTestRepo;
import com.example.demo.model.qc.amg.AMGTest;
import com.example.demo.model.qc.hiv.HIVAbTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AMGTestService {
    AMGTestRepo testRepo;
    @Autowired
    public AMGTestService(AMGTestRepo testRepo) {
        this.testRepo = testRepo;
    }
    public void save(AMGTest test) {
        testRepo.save(test);
    }

    public AMGTest getByUse(String in_use, String name) {
       return testRepo.findDistinctByInuseAndName(in_use, name).orElseThrow();
    }

    public AMGTest findByNameAndLotAndNumber(String name, String lot, int number) {
       return testRepo.findByNameAndLotAndNumber(name, lot, number);
    }

    public AMGTest findByInuse(String inuse) {
        return testRepo.findByInuse(inuse);
    }
    public List<AMGTest> getAllByInuse(String inuse) {
        return testRepo.findAllByInuse(inuse);
    }

    public AMGTest getTopByLotOrderByNumberDesc(String lot) {
        return testRepo.getTopByLotOrderByNumberDesc(lot);
    }
}
