package com.example.demo.service;

import com.example.demo.dao.AMGMeasureRepo;
import com.example.demo.dao.HydroMeasureRepo;
import com.example.demo.model.qc.amg.AMGMeasure;
import com.example.demo.model.qc.hydroxyprog.HydroMeasure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HydroMeasureService {
    HydroMeasureRepo measureRepo;

    @Autowired
    public HydroMeasureService(HydroMeasureRepo measureRepo) {
        this.measureRepo = measureRepo;
    }

    public void save(HydroMeasure measure) {
        measureRepo.save(measure);
    }
    public List<HydroMeasure> findAll() {
      return  measureRepo.findAll();
    }
    public List<HydroMeasure> findByTest_Lot(String test_Lot) {
       return measureRepo.findByTest_Lot(test_Lot);
    }
    public List<HydroMeasure> findByLot_Lot(String lot_Lot) {
        return measureRepo.findByLot_Lot(lot_Lot);
    }
    public LocalDate findByMeasure_date() {
        return measureRepo.findMaxId();}

}
