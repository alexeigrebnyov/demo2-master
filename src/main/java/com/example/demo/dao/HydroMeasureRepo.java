package com.example.demo.dao;

import com.example.demo.model.qc.amg.AMGMeasure;
import com.example.demo.model.qc.hydroxyprog.HydroMeasure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HydroMeasureRepo extends JpaRepository<HydroMeasure, Long> {
    List<HydroMeasure> findByTest_Lot(String test_Lot);
    List<HydroMeasure> findByLot_Lot(String lot_Lot);
    @Query(value = "SELECT top 1 cast (measure_date as date)  FROM hydromeasure ORDER BY id DESC", nativeQuery = true)
    LocalDate findMaxId();

}
