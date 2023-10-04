package com.example.demo.dao;

import com.example.demo.model.qc.hiv.HIVAgLot;
import com.example.demo.model.qc.syph.SyphLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SyphLotRepo extends JpaRepository<SyphLot, Long> {
     List<SyphLot> findDistinctByDateFromAndDateTo(String df, String dt);
     List<SyphLot> findByInuse(String inuse);
     Optional<SyphLot> findDistinctByInuse(String inuse);
     List<SyphLot> findByNameAndLot(String name, String lot);
     SyphLot findByNameAndLotAndNumber(String name, String lot, int number);
     SyphLot getTopByLotOrderByNumberDesc(String lot);
}



