package com.example.demo.dao;

import com.example.demo.model.qc.hiv.HIVAbMeasure;
import com.example.demo.model.qc.hiv.HIVAgMeasure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HIVAgMeasureRepo extends JpaRepository<HIVAgMeasure, Long> {
    List<HIVAgMeasure> findByTest_Lot(String test_Lot);
    List<HIVAgMeasure> findByLot_Lot(String lot_Lot);
    @Query(value = "SELECT top 1 cast (measure_date as date)  FROM hiv_agmeasure ORDER BY id DESC", nativeQuery = true)
    LocalDate findMaxId();

}
