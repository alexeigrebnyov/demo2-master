package com.example.demo.service;

import com.example.demo.dao.HBsAgTestRepo;
import com.example.demo.dao.TestRepo;
import com.example.demo.model.qc.hbs.HBsAgTest;
import com.example.demo.model.qc.hcv.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HBsAgTestService {
    HBsAgTestRepo testRepo;
    @Autowired
    public HBsAgTestService(HBsAgTestRepo testRepo) {
        this.testRepo = testRepo;
    }
    public void save(HBsAgTest test) {
        testRepo.save(test);
    }

    public HBsAgTest getByUse(String in_use, String name) {
       return testRepo.findDistinctByInuseAndName(in_use, name).orElseThrow();
    }

    public HBsAgTest findByNameAndLotAndNumber(String name, String lot, int number) {
       return testRepo.findByNameAndLotAndNumber(name, lot, number);
    }

    public HBsAgTest findByInuse(String inuse) {
        return testRepo.findByInuse(inuse);
    }
    public List<HBsAgTest> getAllByInuse(String inuse) {
        return testRepo.findAllByInuse(inuse);
    }

    public HBsAgTest getTopByLotOrderByNumberDesc(String lot) {
        return testRepo.getTopByLotOrderByNumberDesc(lot);
    }
}
