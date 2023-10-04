package com.example.demo.service;

import com.example.demo.dao.HIVAgMeasureRepo;
import com.example.demo.dao.SyphMeasureRepo;
import com.example.demo.model.qc.hiv.HIVAgMeasure;
import com.example.demo.model.qc.syph.SyphMeasure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SyphMeasureService {
    SyphMeasureRepo measureRepo;

    @Autowired
    public SyphMeasureService(SyphMeasureRepo measureRepo) {
        this.measureRepo = measureRepo;
    }

    public void save(SyphMeasure measure) {
        measureRepo.save(measure);
    }
    public List<SyphMeasure> findAll() {
      return  measureRepo.findAll();
    }
    public List<SyphMeasure> findByTest_Lot(String test_Lot) {
       return measureRepo.findByTest_Lot(test_Lot);
    }
    public List<SyphMeasure> findByLot_Lot(String lot_Lot) {
        return measureRepo.findByLot_Lot(lot_Lot);
    }
    public LocalDate findByMeasure_date() {
        return measureRepo.findMaxId();}

}
