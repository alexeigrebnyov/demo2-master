package com.example.demo.service;

import com.example.demo.dao.HBsAgLotRepo;
import com.example.demo.dao.HIVAbLotRepo;
import com.example.demo.model.qc.hbs.HBsAgLot;
import com.example.demo.model.qc.hiv.HIVAbLot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HIVAbLotService {
   private HIVAbLotRepo lotRepo;

    @Autowired
    public HIVAbLotService(HIVAbLotRepo lotRepo) {
        this.lotRepo = lotRepo;
    }

//    @Transactional
    public void save(HIVAbLot lot) {
        lotRepo.save(lot);
    }
    public void saveAll(List<HIVAbLot> lots) {
        lotRepo.saveAll(lots);
    }
    public HIVAbLot getByUse(String in_use) {
        return lotRepo.findDistinctByInuse(in_use).orElseThrow();
    }
    public List<HIVAbLot> findByNameAndLot(String name, String lot) {
        return lotRepo.findByNameAndLot(name, lot);
    }
    public HIVAbLot findByNameAndLotAndNumber(String name, String lot, int number) {
       return lotRepo.findByNameAndLotAndNumber(name, lot, number);
    }

    public HIVAbLot getTopByLot(String lot) {
        return lotRepo.getTopByLotOrderByNumberDesc(lot);
    }
    public List<HIVAbLot> getLotsInuse(String inuse) {
        return lotRepo.findByInuse(inuse);
    }
}
