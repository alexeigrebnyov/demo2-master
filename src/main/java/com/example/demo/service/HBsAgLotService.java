package com.example.demo.service;

import com.example.demo.dao.HBsAgLotRepo;
import com.example.demo.dao.LotRepo;
import com.example.demo.model.qc.hbs.HBsAgLot;
import com.example.demo.model.qc.hcv.Lot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HBsAgLotService {
   private HBsAgLotRepo lotRepo;

    @Autowired
    public HBsAgLotService(HBsAgLotRepo lotRepo) {
        this.lotRepo = lotRepo;
    }

//    @Transactional
    public void save(HBsAgLot lot) {
        lotRepo.save(lot);
    }
    public void saveAll(List<HBsAgLot> lots) {
        lotRepo.saveAll(lots);
    }
    public HBsAgLot getByUse(String in_use) {
        return lotRepo.findDistinctByInuse(in_use).orElseThrow();
    }
    public List<HBsAgLot> findByNameAndLot(String name, String lot) {
        return lotRepo.findByNameAndLot(name, lot);
    }
    public HBsAgLot findByNameAndLotAndNumber(String name, String lot, int number) {
       return lotRepo.findByNameAndLotAndNumber(name, lot, number);
    }

    public HBsAgLot getTopByLot(String lot) {
        return lotRepo.getTopByLotOrderByNumberDesc(lot);
    }
    public List<HBsAgLot> getLotsInuse(String inuse) {
        return lotRepo.findByInuse(inuse);
    }
}
