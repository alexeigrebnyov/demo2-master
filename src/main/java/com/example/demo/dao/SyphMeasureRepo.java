package com.example.demo.dao;

import com.example.demo.model.qc.hiv.HIVAgMeasure;
import com.example.demo.model.qc.syph.SyphMeasure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SyphMeasureRepo extends JpaRepository<SyphMeasure, Long> {
    List<SyphMeasure> findByTest_Lot(String test_Lot);
    List<SyphMeasure> findByLot_Lot(String lot_Lot);
    @Query(value = "SELECT top 1 cast (measure_date as date)  FROM syphmeasure ORDER BY id DESC", nativeQuery = true)
    LocalDate findMaxId();

}
