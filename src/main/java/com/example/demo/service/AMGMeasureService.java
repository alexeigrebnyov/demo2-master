package com.example.demo.service;

import com.example.demo.dao.AMGMeasureRepo;
import com.example.demo.dao.HIVAbMeasureRepo;
import com.example.demo.model.qc.amg.AMGMeasure;
import com.example.demo.model.qc.hiv.HIVAbMeasure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AMGMeasureService {
    AMGMeasureRepo measureRepo;

    @Autowired
    public AMGMeasureService(AMGMeasureRepo measureRepo) {
        this.measureRepo = measureRepo;
    }

    public void save(AMGMeasure measure) {
        measureRepo.save(measure);
    }
    public List<AMGMeasure> findAll() {
      return  measureRepo.findAll();
    }
    public List<AMGMeasure> findByTest_Lot(String test_Lot) {
       return measureRepo.findByTest_Lot(test_Lot);
    }
    public List<AMGMeasure> findByLot_Lot(String lot_Lot) {
        return measureRepo.findByLot_Lot(lot_Lot);
    }
    public LocalDate findByMeasure_date() {
        return measureRepo.findMaxId();}

}
