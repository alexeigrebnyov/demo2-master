package com.example.demo.service;

import com.example.demo.dao.TestRepo;
import com.example.demo.model.qc.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    TestRepo testRepo;
    @Autowired
    public TestService(TestRepo testRepo) {
        this.testRepo = testRepo;
    }
    public void save(Test test) {
        testRepo.save(test);
    }

    public Test getByUse(String in_use, String name) {
       return testRepo.findDistinctByInuseAndName(in_use, name).orElseThrow();
    }
}
