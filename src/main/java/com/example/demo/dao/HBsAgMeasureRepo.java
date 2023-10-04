package com.example.demo.dao;

import com.example.demo.model.qc.hbs.HBsAgMeasure;
import com.example.demo.model.qc.hcv.Measure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HBsAgMeasureRepo extends JpaRepository<HBsAgMeasure, Long> {
    List<HBsAgMeasure> findByTest_Lot(String test_Lot);
    List<HBsAgMeasure> findByLot_Lot(String lot_Lot);
    @Query(value = "SELECT top 1 cast (measure_date as date)  FROM hbs_agmeasure ORDER BY id DESC", nativeQuery = true)
    LocalDate findMaxId();

}
