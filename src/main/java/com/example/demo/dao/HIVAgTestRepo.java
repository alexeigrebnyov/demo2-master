package com.example.demo.dao;

import com.example.demo.model.qc.hiv.HIVAbTest;
import com.example.demo.model.qc.hiv.HIVAgTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HIVAgTestRepo extends JpaRepository<HIVAgTest, Long> {
    Optional<HIVAgTest> findDistinctByInuseAndName(String inuse, String name);
    HIVAgTest findByNameAndLotAndNumber(String name, String lot, int number);
    HIVAgTest findByInuse(String inuse);
    List<HIVAgTest> findAllByInuse(String inuse);
    HIVAgTest getTopByLotOrderByNumberDesc(String lot);
}
