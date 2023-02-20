package com.example.demo.service;

import com.example.demo.dao.LotRepo;
import com.example.demo.model.qc.Lot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LotService {
   private LotRepo lotRepo;

    @Autowired
    public LotService(LotRepo lotRepo) {
        this.lotRepo = lotRepo;
    }

//    @Transactional
    public void save(Lot lot) {
        lotRepo.save(lot);
    }
    public void saveAll(List<Lot> lots) {
        lotRepo.saveAll(lots);
    }
    public Lot getByUse(String in_use, String name) {
        return lotRepo.findDistinctByInuseAndName(in_use, name).orElseThrow();
    }
    public List<Lot> findByNameAndLot(String name, String lot) {
        return lotRepo.findByNameAndLot(name, lot);
    }
}
