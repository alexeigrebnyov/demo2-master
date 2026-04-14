package com.example.demo.dao;

import com.example.demo.model.qc.amg.AMGTest;
import com.example.demo.model.qc.hiv.HIVAbTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AMGTestRepo extends JpaRepository<AMGTest, Long> {
    Optional<AMGTest> findDistinctByInuseAndName(String inuse, String name);
    AMGTest findByNameAndLotAndNumber(String name, String lot, int number);
    AMGTest findByInuse(String inuse);
    List<AMGTest> findAllByInuse(String inuse);
    AMGTest getTopByLotOrderByNumberDesc(String lot);
}
