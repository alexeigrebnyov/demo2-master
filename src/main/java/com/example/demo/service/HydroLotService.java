package com.example.demo.service;

import com.example.demo.dao.AMGLotRepo;
import com.example.demo.dao.HydroLotRepo;
import com.example.demo.model.qc.amg.AMGLot;
import com.example.demo.model.qc.hydroxyprog.HydroLot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HydroLotService {
   private HydroLotRepo lotRepo;

    @Autowired
    public HydroLotService(HydroLotRepo lotRepo) {
        this.lotRepo = lotRepo;
    }

//    @Transactional
    public void save(HydroLot lot) {
        lotRepo.save(lot);
    }
    public void saveAll(List<HydroLot> lots) {
        lotRepo.saveAll(lots);
    }
    public HydroLot getByUse(String in_use) {
        return lotRepo.findDistinctByInuse(in_use).orElseThrow();
    }
    public List<HydroLot> findByNameAndLot(String name, String lot) {
        return lotRepo.findByNameAndLot(name, lot);
    }
    public HydroLot findByNameAndLotAndNumber(String name, String lot, int number) {
       return lotRepo.findByNameAndLotAndNumber(name, lot, number);
    }

    public HydroLot getTopByLot(String lot) {
        return lotRepo.getTopByLotOrderByNumberDesc(lot);
    }
    public List<HydroLot> getLotsInuse(String inuse) {
        return lotRepo.findByInuse(inuse);
    }
}
