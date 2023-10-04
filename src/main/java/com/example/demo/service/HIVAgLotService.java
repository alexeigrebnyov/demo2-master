package com.example.demo.service;

import com.example.demo.dao.HIVAbLotRepo;
import com.example.demo.dao.HIVAgLotRepo;
import com.example.demo.model.qc.hiv.HIVAbLot;
import com.example.demo.model.qc.hiv.HIVAgLot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HIVAgLotService {
   private HIVAgLotRepo lotRepo;

    @Autowired
    public HIVAgLotService(HIVAgLotRepo lotRepo) {
        this.lotRepo = lotRepo;
    }

//    @Transactional
    public void save(HIVAgLot lot) {
        lotRepo.save(lot);
    }
    public void saveAll(List<HIVAgLot> lots) {
        lotRepo.saveAll(lots);
    }
    public HIVAgLot getByUse(String in_use) {
        return lotRepo.findDistinctByInuse(in_use).orElseThrow();
    }
    public List<HIVAgLot> findByNameAndLot(String name, String lot) {
        return lotRepo.findByNameAndLot(name, lot);
    }
    public HIVAgLot findByNameAndLotAndNumber(String name, String lot, int number) {
       return lotRepo.findByNameAndLotAndNumber(name, lot, number);
    }

    public HIVAgLot getTopByLot(String lot) {
        return lotRepo.getTopByLotOrderByNumberDesc(lot);
    }
    public List<HIVAgLot> getLotsInuse(String inuse) {
        return lotRepo.findByInuse(inuse);
    }
}
