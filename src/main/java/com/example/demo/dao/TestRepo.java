package com.example.demo.dao;

import com.example.demo.model.qc.hcv.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepo extends JpaRepository<Test, Long> {
    Optional<Test> findDistinctByInuseAndName(String inuse, String name);
    Test findByNameAndLotAndNumber(String name, String lot, int number);
    Test findByInuse(String inuse);
    List<Test> findAllByInuse(String inuse);
    Test getTopByLotOrderByNumberDesc(String lot);
}
