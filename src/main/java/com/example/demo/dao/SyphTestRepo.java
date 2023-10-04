package com.example.demo.dao;

import com.example.demo.model.qc.hiv.HIVAgTest;
import com.example.demo.model.qc.syph.SyphTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SyphTestRepo extends JpaRepository<SyphTest, Long> {
    Optional<SyphTest> findDistinctByInuseAndName(String inuse, String name);
    SyphTest findByNameAndLotAndNumber(String name, String lot, int number);
    SyphTest findByInuse(String inuse);
    List<SyphTest> findAllByInuse(String inuse);
    SyphTest getTopByLotOrderByNumberDesc(String lot);
}
