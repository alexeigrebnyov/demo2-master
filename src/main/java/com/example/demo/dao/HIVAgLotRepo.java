package com.example.demo.dao;

import com.example.demo.model.qc.hiv.HIVAbLot;
import com.example.demo.model.qc.hiv.HIVAgLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HIVAgLotRepo extends JpaRepository<HIVAgLot, Long> {
     List<HIVAgLot> findDistinctByDateFromAndDateTo(String df, String dt);
     List<HIVAgLot> findByInuse(String inuse);
     Optional<HIVAgLot> findDistinctByInuse(String inuse);
     List<HIVAgLot> findByNameAndLot(String name, String lot);
     HIVAgLot findByNameAndLotAndNumber(String name, String lot, int number);
     HIVAgLot getTopByLotOrderByNumberDesc(String lot);
}



