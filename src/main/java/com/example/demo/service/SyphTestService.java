package com.example.demo.service;

import com.example.demo.dao.HIVAgTestRepo;
import com.example.demo.dao.SyphTestRepo;
import com.example.demo.model.qc.hiv.HIVAgTest;
import com.example.demo.model.qc.syph.SyphTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyphTestService {
    SyphTestRepo testRepo;
    @Autowired
    public SyphTestService(SyphTestRepo testRepo) {
        this.testRepo = testRepo;
    }
    public void save(SyphTest test) {
        testRepo.save(test);
    }

    public SyphTest getByUse(String in_use, String name) {
       return testRepo.findDistinctByInuseAndName(in_use, name).orElseThrow();
    }

    public SyphTest findByNameAndLotAndNumber(String name, String lot, int number) {
       return testRepo.findByNameAndLotAndNumber(name, lot, number);
    }

    public SyphTest findByInuse(String inuse) {
        return testRepo.findByInuse(inuse);
    }
    public List<SyphTest> getAllByInuse(String inuse) {
        return testRepo.findAllByInuse(inuse);
    }

    public SyphTest getTopByLotOrderByNumberDesc(String lot) {
        return testRepo.getTopByLotOrderByNumberDesc(lot);
    }
}
