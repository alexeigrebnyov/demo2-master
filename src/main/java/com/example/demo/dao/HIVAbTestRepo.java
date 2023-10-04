package com.example.demo.dao;

import com.example.demo.model.qc.hbs.HBsAgTest;
import com.example.demo.model.qc.hiv.HIVAbTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HIVAbTestRepo extends JpaRepository<HIVAbTest, Long> {
    Optional<HIVAbTest> findDistinctByInuseAndName(String inuse, String name);
    HIVAbTest findByNameAndLotAndNumber(String name, String lot, int number);
    HIVAbTest findByInuse(String inuse);
    List<HIVAbTest> findAllByInuse(String inuse);
    HIVAbTest getTopByLotOrderByNumberDesc(String lot);
}
