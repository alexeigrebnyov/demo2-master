package com.example.demo.service;

import com.example.demo.dao.HIVAbMeasureRepo;
import com.example.demo.dao.HIVAgMeasureRepo;
import com.example.demo.model.qc.hiv.HIVAbMeasure;
import com.example.demo.model.qc.hiv.HIVAgMeasure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HIVAgMeasureService {
    HIVAgMeasureRepo measureRepo;

    @Autowired
    public HIVAgMeasureService(HIVAgMeasureRepo measureRepo) {
        this.measureRepo = measureRepo;
    }

    public void save(HIVAgMeasure measure) {
        measureRepo.save(measure);
    }
    public List<HIVAgMeasure> findAll() {
      return  measureRepo.findAll();
    }
    public List<HIVAgMeasure> findByTest_Lot(String test_Lot) {
       return measureRepo.findByTest_Lot(test_Lot);
    }
    public List<HIVAgMeasure> findByLot_Lot(String lot_Lot) {
        return measureRepo.findByLot_Lot(lot_Lot);
    }
    public LocalDate findByMeasure_date() {
        return measureRepo.findMaxId();}

}
