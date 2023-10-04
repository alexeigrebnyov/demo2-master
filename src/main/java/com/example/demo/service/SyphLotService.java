package com.example.demo.service;

import com.example.demo.dao.HIVAgLotRepo;
import com.example.demo.dao.SyphLotRepo;
import com.example.demo.model.qc.hiv.HIVAgLot;
import com.example.demo.model.qc.syph.SyphLot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyphLotService {
   private SyphLotRepo lotRepo;

    @Autowired
    public SyphLotService(SyphLotRepo lotRepo) {
        this.lotRepo = lotRepo;
    }

//    @Transactional
    public void save(SyphLot lot) {
        lotRepo.save(lot);
    }
    public void saveAll(List<SyphLot> lots) {
        lotRepo.saveAll(lots);
    }
    public SyphLot getByUse(String in_use) {
        return lotRepo.findDistinctByInuse(in_use).orElseThrow();
    }
    public List<SyphLot> findByNameAndLot(String name, String lot) {
        return lotRepo.findByNameAndLot(name, lot);
    }
    public SyphLot findByNameAndLotAndNumber(String name, String lot, int number) {
       return lotRepo.findByNameAndLotAndNumber(name, lot, number);
    }

    public SyphLot getTopByLot(String lot) {
        return lotRepo.getTopByLotOrderByNumberDesc(lot);
    }
    public List<SyphLot> getLotsInuse(String inuse) {
        return lotRepo.findByInuse(inuse);
    }
}
