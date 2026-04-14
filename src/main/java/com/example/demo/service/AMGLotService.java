package com.example.demo.service;

import com.example.demo.dao.AMGLotRepo;
import com.example.demo.dao.HIVAbLotRepo;
import com.example.demo.model.qc.amg.AMGLot;
import com.example.demo.model.qc.hiv.HIVAbLot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AMGLotService {
   private AMGLotRepo lotRepo;

    @Autowired
    public AMGLotService(AMGLotRepo lotRepo) {
        this.lotRepo = lotRepo;
    }

//    @Transactional
    public void save(AMGLot lot) {
        lotRepo.save(lot);
    }
    public void saveAll(List<AMGLot> lots) {
        lotRepo.saveAll(lots);
    }
    public AMGLot getByUse(String in_use) {
        return lotRepo.findDistinctByInuse(in_use).orElseThrow();
    }
    public List<AMGLot> findByNameAndLot(String name, String lot) {
        return lotRepo.findByNameAndLot(name, lot);
    }
    public AMGLot findByNameAndLotAndNumber(String name, String lot, int number) {
       return lotRepo.findByNameAndLotAndNumber(name, lot, number);
    }

    public AMGLot getTopByLot(String lot) {
        return lotRepo.getTopByLotOrderByNumberDesc(lot);
    }
    public List<AMGLot> getLotsInuse(String inuse) {
        return lotRepo.findByInuse(inuse);
    }
}
