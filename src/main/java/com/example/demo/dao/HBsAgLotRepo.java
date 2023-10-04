package com.example.demo.dao;

import com.example.demo.model.qc.hbs.HBsAgLot;
import com.example.demo.model.qc.hcv.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HBsAgLotRepo extends JpaRepository<HBsAgLot, Long> {
     List<HBsAgLot> findDistinctByDateFromAndDateTo(String df, String dt);
     List<HBsAgLot> findByInuse(String inuse);
     Optional<HBsAgLot> findDistinctByInuse(String inuse);
     List<HBsAgLot> findByNameAndLot(String name, String lot);
     HBsAgLot findByNameAndLotAndNumber(String name, String lot, int number);
     HBsAgLot getTopByLotOrderByNumberDesc(String lot);
}



