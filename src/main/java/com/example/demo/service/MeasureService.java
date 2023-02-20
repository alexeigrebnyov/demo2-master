package com.example.demo.service;

import com.example.demo.dao.MeasureRepo;
import com.example.demo.model.qc.Measure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasureService {
    MeasureRepo measureRepo;

    @Autowired
    public MeasureService(MeasureRepo measureRepo) {
        this.measureRepo = measureRepo;
    }

    public void save(Measure measure) {
        measureRepo.save(measure);
    }
    public List<Measure> findAll() {
      return  measureRepo.findAll();
    }
    public List<Measure> findByTest_Lot(String test_Lot) {
       return measureRepo.findByTest_Lot(test_Lot);
    }
    public List<Measure> findByLot_Lot(String lot_Lot) {
        return measureRepo.findByLot_Lot(lot_Lot);
    }

}
