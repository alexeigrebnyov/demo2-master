package com.example.demo.dao;

import com.example.demo.model.qc.hbs.HBsAgLot;
import com.example.demo.model.qc.hiv.HIVAbLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HIVAbLotRepo extends JpaRepository<HIVAbLot, Long> {
     List<HIVAbLot> findDistinctByDateFromAndDateTo(String df, String dt);
     List<HIVAbLot> findByInuse(String inuse);
     Optional<HIVAbLot> findDistinctByInuse(String inuse);
     List<HIVAbLot> findByNameAndLot(String name, String lot);
     HIVAbLot findByNameAndLotAndNumber(String name, String lot, int number);
     HIVAbLot getTopByLotOrderByNumberDesc(String lot);
}



