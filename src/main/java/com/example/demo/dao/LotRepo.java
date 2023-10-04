package com.example.demo.dao;

import com.example.demo.model.qc.hcv.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LotRepo extends JpaRepository<Lot, Long> {
     List<Lot> findDistinctByDateFromAndDateTo(String df, String dt);
     List<Lot> findByInuse(String inuse);
     Optional<Lot> findDistinctByInuseAndName(String inuse, String name);
     List<Lot> findByNameAndLot(String name, String lot);
     Lot findByNameAndLotAndNumber(String name, String lot, int number);
     Lot getTopByLotOrderByNumberDesc(String lot);

//     List<Optional<Lot>> findDistinctByName()



}



