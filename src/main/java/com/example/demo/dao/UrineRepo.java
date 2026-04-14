package com.example.demo.dao;

import com.example.demo.model.qc.hbs.HBsAgLot;
import com.example.demo.model.qc.protein.Urine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrineRepo extends JpaRepository<Urine, Long> {
    List<Urine> findAll();
//    List<Urine> findByMeasure_dateBetween(String after, String before);
    List<Urine> findByLot_Name(String lotName);
    Urine findById(long id);
//    void deleteAllById(List<Long> ids);
    void deleteAllByIdIn(List<Long> ids);


}
