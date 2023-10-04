package com.example.demo.service;

import com.example.demo.dao.HBsAgMeasureRepo;
import com.example.demo.dao.HIVAbMeasureRepo;
import com.example.demo.model.qc.hbs.HBsAgMeasure;
import com.example.demo.model.qc.hiv.HIVAbMeasure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HIVAbMeasureService {
    HIVAbMeasureRepo measureRepo;

    @Autowired
    public HIVAbMeasureService(HIVAbMeasureRepo measureRepo) {
        this.measureRepo = measureRepo;
    }

    public void save(HIVAbMeasure measure) {
        measureRepo.save(measure);
    }
    public List<HIVAbMeasure> findAll() {
      return  measureRepo.findAll();
    }
    public List<HIVAbMeasure> findByTest_Lot(String test_Lot) {
       return measureRepo.findByTest_Lot(test_Lot);
    }
    public List<HIVAbMeasure> findByLot_Lot(String lot_Lot) {
        return measureRepo.findByLot_Lot(lot_Lot);
    }
    public LocalDate findByMeasure_date() {
        return measureRepo.findMaxId();}

}
