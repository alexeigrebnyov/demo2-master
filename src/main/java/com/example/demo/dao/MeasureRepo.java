package com.example.demo.dao;

import com.example.demo.model.qc.Measure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasureRepo extends JpaRepository<Measure, Long> {
    List<Measure> findByTest_Lot(String test_Lot);
    List<Measure> findByLot_Lot(String lot_Lot);

}
