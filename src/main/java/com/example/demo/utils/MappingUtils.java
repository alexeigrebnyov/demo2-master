package com.example.demo.utils;

import com.example.demo.model.dto.MeasureDTO;
import com.example.demo.model.qc.Measure;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    //из dto в entity
//    public Measure mapToMeasureEntity(MeasureDTO dto){
//        Measure entity = new Measure();
//        entity.setId(dto.getId());
//        entity.setLot(dto.getLot());
//        entity.setPurchasePrice(dto.getPurchasePrice());
//        return entity;
//    }
}
