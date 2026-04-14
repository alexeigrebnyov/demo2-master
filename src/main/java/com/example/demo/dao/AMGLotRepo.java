package com.example.demo.dao;

import com.example.demo.model.qc.amg.AMGLot;
import com.example.demo.model.qc.hiv.HIVAbLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AMGLotRepo extends JpaRepository<AMGLot, Long> {
     List<AMGLot> findDistinctByDateFromAndDateTo(String df, String dt);
     List<AMGLot> findByInuse(String inuse);
     Optional<AMGLot> findDistinctByInuse(String inuse);
     List<AMGLot> findByNameAndLot(String name, String lot);
     AMGLot findByNameAndLotAndNumber(String name, String lot, int number);
     AMGLot getTopByLotOrderByNumberDesc(String lot);
}



