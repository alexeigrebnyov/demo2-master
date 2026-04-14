package com.example.demo.controller;

import com.example.demo.model.qc.protein.K1Lot;
import com.example.demo.model.qc.protein.Urine;
import com.example.demo.service.K1LotService;
import com.example.demo.service.UrineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/urine")
public class UrineController {
    private UrineService urineService;
    private K1LotService k1LotService;

    @Autowired
    public UrineController(UrineService urineService, K1LotService k1LotService) {
        this.urineService = urineService;
        this.k1LotService = k1LotService;
    }

    @GetMapping("/testSet")
    public void testSeter() {
//        K1Lot k1Lot1 = new K1Lot("Контроль №1", "5234", LocalDateTime.now(), LocalDateTime.parse("2025-12-30T00:00"), 1, null, "1");
//        K1Lot k1Lot2 = new K1Lot("Контроль №1", "5234", LocalDateTime.now(), LocalDateTime.parse("2025-10-30T00:00"), 2, null, "0");
//        k1LotService.saveAll(List.of(k1Lot1, k1Lot2));

        Urine urine1 = new Urine(LocalDateTime.now(), 0.24d, k1LotService.getByInUse("1").get(0),53);
        Urine urine2 = new Urine(LocalDateTime.parse("2025-02-04T00:00"), 0.58d, k1LotService.getByInUse("1").get(0),53);
        Urine urine3 = new Urine(LocalDateTime.parse("2025-02-05T00:00"), 0.35d, k1LotService.getByInUse("1").get(0),53);
        Urine urine4 = new Urine(LocalDateTime.parse("2025-02-06T00:00"), 0.48d, k1LotService.getByInUse("1").get(0),53);

        urineService.saveAll(List.of(urine1,urine2,urine3,urine4));
    }

    @GetMapping("/getk1all")
    public List<K1Lot> getK1Lots() {
        return k1LotService.getAll();
    }

    @GetMapping("/getk1between/{date1}/{date2}/{org}")
    public List<K1Lot> getK1LotsByMeasuresBetween(@PathVariable("date1") String date1, @PathVariable("date2") String date2, @PathVariable("org") int org) {
        System.out.println(date1);
        System.out.println(date2);
        return k1LotService.getMesuresBetween(LocalDateTime.parse(date1), LocalDateTime.parse(date2), org);
    }
    @GetMapping("/getUrineall")
    public List<Urine> getUrines() {
      return urineService.getAll();
    }

//    @GetMapping("/getLotinuse")

}
