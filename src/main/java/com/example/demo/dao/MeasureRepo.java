package com.example.demo.dao;

import com.example.demo.model.qc.hcv.Measure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MeasureRepo extends JpaRepository<Measure, Long> {
    List<Measure> findByTest_Lot(String test_Lot);
    List<Measure> findByLot_Lot(String lot_Lot);
    @Query(value = "SELECT top 1 cast (measure_date as date)  FROM measure ORDER BY id DESC", nativeQuery = true)
    LocalDate findMaxId();

}
