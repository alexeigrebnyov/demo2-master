package com.example.demo.dao;

import com.example.demo.model.qc.hbs.HBsAgTest;
import com.example.demo.model.qc.hcv.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HBsAgTestRepo extends JpaRepository<HBsAgTest, Long> {
    Optional<HBsAgTest> findDistinctByInuseAndName(String inuse, String name);
    HBsAgTest findByNameAndLotAndNumber(String name, String lot, int number);
    HBsAgTest findByInuse(String inuse);
    List<HBsAgTest> findAllByInuse(String inuse);
    HBsAgTest getTopByLotOrderByNumberDesc(String lot);
}
