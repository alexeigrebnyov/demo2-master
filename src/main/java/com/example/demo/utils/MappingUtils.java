package com.example.demo.utils;

import com.example.demo.model.dto.*;
import com.example.demo.model.qc.amg.AMGMeasure;
import com.example.demo.model.qc.hbs.HBsAgMeasure;
import com.example.demo.model.qc.hcv.Measure;
import com.example.demo.model.qc.hiv.HIVAbMeasure;
import com.example.demo.model.qc.hiv.HIVAgMeasure;
import com.example.demo.model.qc.hydroxyprog.HydroMeasure;
import com.example.demo.model.qc.hydroxyprog.HydroTest;
import com.example.demo.model.qc.syph.SyphMeasure;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Service
public class MappingUtils {
    private static final SimpleDateFormat dateFormat
            = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static MeasureDTO mapToMeasureDto(Measure entity){
        MeasureDTO dto = new MeasureDTO();
        dto.setId(entity.getId());
        dto.setLot(entity.getLot().getLot());
        dto.setTest(entity.getTest().getLot());
        dto.setMeasure_type(entity.getMeasure_type());
        dto.setMeasure_date(dateFormat.format(Timestamp.valueOf(entity.getMeasure_date())));
        dto.setMeasure_val(entity.getMeasure_val());
        return dto;
    }
    public static HBsAgMeasureDTO mapToHBsAgMeasureDto(HBsAgMeasure entity){
        HBsAgMeasureDTO dto = new HBsAgMeasureDTO();
        dto.setId(entity.getId());
        dto.setLot(entity.getLot().getLot());
        dto.setTest(entity.getTest().getLot());
        dto.setMeasure_type(entity.getMeasure_type());
        dto.setMeasure_date(dateFormat.format(Timestamp.valueOf(entity.getMeasure_date())));
        dto.setMeasure_val(entity.getMeasure_val());
        return dto;
    }

    public static HIVAbMeasureDTO mapToHIVAbMeasureDto(HIVAbMeasure entity){
        HIVAbMeasureDTO dto = new HIVAbMeasureDTO();
        dto.setId(entity.getId());
        dto.setLot(entity.getLot().getLot());
        dto.setTest(entity.getTest().getLot());
        dto.setMeasure_type(entity.getMeasure_type());
        dto.setMeasure_date(dateFormat.format(Timestamp.valueOf(entity.getMeasure_date())));
        dto.setMeasure_val(entity.getMeasure_val());
        return dto;
    }

    public static HIVAgMeasureDTO mapToHIVAgMeasureDto(HIVAgMeasure entity){
        HIVAgMeasureDTO dto = new HIVAgMeasureDTO();
        dto.setId(entity.getId());
        dto.setLot(entity.getLot().getLot());
        dto.setTest(entity.getTest().getLot());
        dto.setMeasure_type(entity.getMeasure_type());
        dto.setMeasure_date(dateFormat.format(Timestamp.valueOf(entity.getMeasure_date())));
        dto.setMeasure_val(entity.getMeasure_val());
        return dto;
    }

    public static SyphMeasureDTO mapToSyphMeasureDto(SyphMeasure entity){
        SyphMeasureDTO dto = new SyphMeasureDTO();
        dto.setId(entity.getId());
        dto.setLot(entity.getLot().getLot());
        dto.setTest(entity.getTest().getLot());
        dto.setMeasure_type(entity.getMeasure_type());
        dto.setMeasure_date(dateFormat.format(Timestamp.valueOf(entity.getMeasure_date())));
        dto.setMeasure_val(entity.getMeasure_val());
        return dto;
    }

    public static AMGMeasureDTO mapToAMGMeasureDto(AMGMeasure entity){
        AMGMeasureDTO dto = new AMGMeasureDTO();
        dto.setId(entity.getId());
        dto.setLot(entity.getLot().getLot());
        dto.setTest(entity.getTest().getLot());
        dto.setMeasure_type(entity.getMeasure_type());
        dto.setMeasure_date(dateFormat.format(Timestamp.valueOf(entity.getMeasure_date())));
        dto.setMeasure_val(entity.getMeasure_val());
        return dto;
    }
    public static HydroMeasureDTO mapToHydroMeasureDto(HydroMeasure entity){
        HydroMeasureDTO dto = new HydroMeasureDTO();
        try {
            dto.setId(entity.getId());
            dto.setLot(entity.getLot().getLot());
            dto.setTest(entity.getTest().getLot());
            dto.setMeasure_type(entity.getMeasure_type());
            dto.setMeasure_date(dateFormat.format(Timestamp.valueOf(entity.getMeasure_date())));
            dto.setMeasure_val(entity.getMeasure_val());
        } catch (Exception e) {
            System.out.println(entity.getId());


        }


        return dto;
    }

    //из dto в entity
//    public Measure mapToMeasureEntity(MeasureDTO dto){
//        Measure entity = new Measure();
//        entity.setId(dto.getId());
//        entity.setLot(dto.getLot());
//        entity.setPurchasePrice(dto.getPurchasePrice());
//        return entity;
//    }
}
