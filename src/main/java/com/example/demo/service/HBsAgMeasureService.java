package com.example.demo.service;

import com.example.demo.dao.HBsAgMeasureRepo;
import com.example.demo.dao.MeasureRepo;
import com.example.demo.model.qc.hbs.HBsAgMeasure;
import com.example.demo.model.qc.hcv.Measure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HBsAgMeasureService {
    HBsAgMeasureRepo measureRepo;

    @Autowired
    public HBsAgMeasureService(HBsAgMeasureRepo measureRepo) {
        this.measureRepo = measureRepo;
    }

    public void save(HBsAgMeasure measure) {
        measureRepo.save(measure);
    }
    public List<HBsAgMeasure> findAll() {
      return  measureRepo.findAll();
    }
    public List<HBsAgMeasure> findByTest_Lot(String test_Lot) {
       return measureRepo.findByTest_Lot(test_Lot);
    }
    public List<HBsAgMeasure> findByLot_Lot(String lot_Lot) {
        return measureRepo.findByLot_Lot(lot_Lot);
    }
    public LocalDate findByMeasure_date() {
        return measureRepo.findMaxId();}

}
