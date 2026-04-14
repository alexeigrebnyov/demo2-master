package com.example.demo.service;

import com.example.demo.dao.UrineRepo;
import com.example.demo.model.qc.protein.Urine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrineService {

    private UrineRepo repo;

    @Autowired
    public UrineService(UrineRepo repo) {
        this.repo = repo;
    }

    public void saveUrine(Urine urine) {
        repo.save(urine);
    }

   public void saveAll(List<Urine> urines) {
        repo.saveAll(urines);
    }


   public void delAll(List<Long> ids) {
        repo.deleteAllByIdIn(ids);
    }

   public List<Urine> getAll() {
        return repo.findAll();
    }

}
