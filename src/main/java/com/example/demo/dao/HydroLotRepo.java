package com.example.demo.dao;

import com.example.demo.model.qc.amg.AMGLot;
import com.example.demo.model.qc.hydroxyprog.HydroLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HydroLotRepo extends JpaRepository<HydroLot, Long> {
     List<HydroLot> findDistinctByDateFromAndDateTo(String df, String dt);
     List<HydroLot> findByInuse(String inuse);
     Optional<HydroLot> findDistinctByInuse(String inuse);
     List<HydroLot> findByNameAndLot(String name, String lot);
     HydroLot findByNameAndLotAndNumber(String name, String lot, int number);
     HydroLot getTopByLotOrderByNumberDesc(String lot);
}



