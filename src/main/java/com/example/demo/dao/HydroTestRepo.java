package com.example.demo.dao;

import com.example.demo.model.qc.amg.AMGTest;
import com.example.demo.model.qc.hydroxyprog.HydroTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HydroTestRepo extends JpaRepository<HydroTest, Long> {
    Optional<HydroTest> findDistinctByInuseAndName(String inuse, String name);
    HydroTest findByNameAndLotAndNumber(String name, String lot, int number);
    HydroTest findByInuse(String inuse);
    List<HydroTest> findAllByInuse(String inuse);
    HydroTest getTopByLotOrderByNumberDesc(String lot);
}
