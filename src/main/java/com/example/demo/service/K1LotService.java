package com.example.demo.service;

import com.example.demo.dao.K1LotRepo;
import com.example.demo.model.qc.protein.K1Lot;
import com.example.demo.model.qc.protein.Urine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class K1LotService {
    private K1LotRepo repo;

    @Autowired
    public K1LotService(K1LotRepo repo) {
        this.repo = repo;
    }

   public void save(K1Lot lot) {
        repo.save(lot);
    }

   public void saveAll(List<K1Lot> lots) {
        repo.saveAll(lots);
    }
    public List<K1Lot> getAll(){
        return repo.findAll();
    }
   public List<K1Lot> getByInUse(String inuse){
        return repo.findByInuse(inuse);
    }
   public void delById(List<Long> ids) {
        repo.deleteByIdIn(ids);
    }

    public List<K1Lot> getMesuresBetween(LocalDateTime after, LocalDateTime before, int org) {
        return repo.findByMeasureListAfterAndMeasureListBefore(after, before, org);
    }
}
