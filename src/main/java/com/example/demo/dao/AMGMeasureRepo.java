package com.example.demo.dao;

import com.example.demo.model.qc.amg.AMGMeasure;
import com.example.demo.model.qc.hiv.HIVAbMeasure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AMGMeasureRepo extends JpaRepository<AMGMeasure, Long> {
    List<AMGMeasure> findByTest_Lot(String test_Lot);
    List<AMGMeasure> findByLot_Lot(String lot_Lot);
    @Query(value = "SELECT top 1 cast (measure_date as date)  FROM amgmeasure ORDER BY id DESC", nativeQuery = true)
    LocalDate findMaxId();

}
