package com.example.demo.controller;

import com.example.demo.model.dto.MeasureDTO;
import com.example.demo.model.qc.Lot;
import com.example.demo.model.qc.Measure;
import com.example.demo.model.qc.Test;
import com.example.demo.service.LotService;
import com.example.demo.service.MeasureService;
import com.example.demo.service.TestService;
import com.example.demo.utils.MappingUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/qc")
public class QCController {

  private LotService lotService;
  private TestService testService;
  private MeasureService measureService;

    @Autowired
    public QCController(LotService lotService, TestService testService, MeasureService measureService) {
        this.lotService = lotService;
        this.testService = testService;
        this.measureService = measureService;
    }

    @PostMapping("/savelot")
    public void saveLots(@RequestBody() Lot lot) {
//        System.out.println(lot);
        lotService.save(lot);
    }
    @PostMapping("/savetest")
    public void saveTest(@RequestBody() Test test) {
//        System.out.println(lot);
          testService.save(test);
    }
    @PostMapping("/savemeasure")
    public void saveTest(@RequestBody()Measure measure) {
//        System.out.println(lot);
        measure.setTest(testService.getByUse("1", "IFAKit"));
        measure.setLot(lotService.getByUse("1", "BioTest"));
        measureService.save(measure);
    }
    @GetMapping("/getLotMeasure/{name}")
    public List<Measure> getLotMeasure(@PathVariable("name") String name) {
         return lotService.getByUse("1", name).getMeasureList();
    }

    @GetMapping("/getmeasuremap/date/{date}")
    public Map<String,List<Measure>> getMeasuresByDate(@PathVariable(name = "date") String date) {
        String[] dates =date.split(" ");
        Map<String, List<Measure>> testMap = new HashMap<>();
//      List<Measure> filteredMeasures =
        measureService.findAll()
                .stream()
                .filter(m-> dates.length>1? (m.getMeasure_date().isAfter(LocalDateTime.parse(dates[0]))
                        && m.getMeasure_date().isBefore(LocalDateTime.parse(dates[1]))):
                        m.getMeasure_date().isAfter(LocalDateTime.parse(date))
                )
//              .collect(Collectors.toList());
                .forEach(measure -> {
                    String testLot=measure.getTest().getLot();

                    if (testMap.containsKey(testLot)) {
                        testMap.get(testLot).add(measure);
                    } else {
                        List<Measure> measures = new ArrayList<>();
                        measures.add(measure);
                        testMap.put(testLot, measures);
                    }
//                          lotMap.put(measure.getLot().getNumber(), measure.getLot().getMeasureList());
//                    System.out.println(testLot);
//                    System.out.println(measure.getId());
//                    System.out.println(measure.getTest().getId());
//                    System.out.println(byTestmeasureList);
                });

//        measureService.findAll()
//                        .stream()
//                                .filter(m-> dates.length>1? (m.getMeasure_date().isAfter(LocalDateTime.parse(dates[0]))
//                                        && m.getMeasure_date().isBefore(LocalDateTime.parse(dates[1]))):
//                                        m.getMeasure_date().isAfter(LocalDateTime.parse(date)))
//
//                                        .forEach(System.out::println);
//        testMap.values()
//                .forEach(System.out::println);
        return testMap;
    }

    @GetMapping("/getmeasuremap/{lot}/{test}/{date}")
    public Map<Integer,List<Measure>> getMeasuresByAll(@PathVariable(name = "lot") String lot, @PathVariable(name = "test") String test,
    @PathVariable(name = "date") String date) {
        String[] dates =date.split(" ");
        Map<Integer, List<Measure>> testMap = new HashMap<>();
//      List<Measure> filteredMeasures =
        measureService.findAll()
                .stream()
                .filter(m-> m.getLot().getLot().equals(lot)&&m.getTest().getLot().equals(test)
                && dates.length>1? m.getMeasure_date().isAfter(LocalDateTime.parse(dates[0]))
                && m.getMeasure_date().isBefore(LocalDateTime.parse(dates[1])):
                    m.getMeasure_date().isAfter(LocalDateTime.parse(date))
                        )
//              .collect(Collectors.toList());
                .forEach(measure -> {
                    Integer testNumber=measure.getTest().getNumber();
                    if (testMap.containsKey(testNumber)){
                        testMap.get(testNumber).add(measure);
                    } else {
                        List<Measure> measures = new ArrayList<>();
                        measures.add(measure);
                        testMap.put(testNumber, measures);
                    }
//                          lotMap.put(measure.getLot().getNumber(), measure.getLot().getMeasureList());
                });

        return testMap;
    }

    @GetMapping("/getmeasuremap/{lot}/{test}")
    public Map<Integer,List<Measure>> getMeasures(@PathVariable(name = "lot") String lot, @PathVariable(name = "test") String test) {
        Map<Integer, List<Measure>> testMap = new HashMap<>();
//      List<Measure> filteredMeasures =
              measureService.findAll()
                .stream()
                .filter(m-> m.getLot().getLot().equals(lot)&&m.getTest().getLot().equals(test))
//              .collect(Collectors.toList());
                      .forEach(measure -> {
                          Integer testNumber=measure.getTest().getNumber();
                          if (testMap.containsKey(testNumber)){
                              testMap.get(testNumber).add(measure);
                          } else {
                              List<Measure> measures = new ArrayList<>();
                              measures.add(measure);
                              testMap.put(testNumber, measures);
                          }
//                          lotMap.put(measure.getLot().getNumber(), measure.getLot().getMeasureList());
                      });

      return testMap;
    }

    @GetMapping("/getmeasuremap/{lot}")
    public Map<String,List<Measure>> getMeasuresByLot(@PathVariable(name = "lot") String lot) {
        Map<String, List<Measure>> testMap = new HashMap<>();
//        Map<Integer, List<Measure>> lotMap = new HashMap<>();
//      List<Measure> filteredMeasures =
        measureService.findAll()
                .stream()
                .filter(m-> m.getLot().getLot().equals(lot))
//              .collect(Collectors.toList());
                .forEach(measure -> {
                    String testLot=measure.getTest().getLot();
                    if (testMap.containsKey(testLot)){
                        testMap.get(testLot).add(measure);
                    } else {
                        List<Measure> measures = new ArrayList<>();
                        measures.add(measure);
                        testMap.put(testLot, measures);
                    }
//                    testMap.put(testLot, measureByTestLot(testLot));
//                    lotMap.put(measure.getLot().getNumber(), measure.getLot().getMeasureList());
                });

        return testMap;
    }
    @GetMapping("/getmeasuremap/test/{test}")
    public Map<Integer,List<Measure>> getMeasuresByTest(@PathVariable(name = "test") String test) {
        Map<Integer, List<Measure>> testMap = new HashMap<>();
//        Map<Integer, List<Measure>> lotMap = new HashMap<>();
//      List<Measure> filteredMeasures =
        measureService.findAll()
                .stream()
                .filter(m-> m.getTest().getLot().equals(test))
//              .collect(Collectors.toList());
                .forEach(measure -> {
                    Integer testLot=measure.getTest().getNumber();
                    if (testMap.containsKey(testLot)){
                        testMap.get(testLot).add(measure);
                    } else {
                        List<Measure> measures = new ArrayList<>();
                        measures.add(measure);
                        testMap.put(testLot, measures);
                    }
//                    testMap.put(testLot, measureByTestLot(testLot));
//                    lotMap.put(measure.getLot().getNumber(), measure.getLot().getMeasureList());
                });

        return testMap;
    }
    @GetMapping("/getmeasurelistsByDateFrom/{date}")
    public List<MeasureDTO> findByMeasure_dateAfter(@PathVariable(name = "date") String dateAfter) {
        String[] dates =dateAfter.split(" ");
        return measureService.findAll()
                .stream()
                .filter(m->dates.length>1?m.getMeasure_date().isAfter(LocalDateTime.parse(dates[0]))
                        && m.getMeasure_date().isBefore(LocalDateTime.parse(dates[1]))
                        :m.getMeasure_date().isAfter(LocalDateTime.parse(dateAfter)))
                .map(MappingUtils::mapToMeasureDto)
                .collect(Collectors.toList());
    }
    @GetMapping("/getmeasurelistsByLot/{lot}")
    public List<MeasureDTO> findMeasureByLotlot(@PathVariable(name = "lot") String lot) {

        return measureService.findByLot_Lot(lot)
                .stream()
                .map(MappingUtils::mapToMeasureDto)
                .collect(Collectors.toList());
    }
    @GetMapping("/getmeasurelistsByTest/{test}")
    public List<MeasureDTO> findMeasureByTestlot(@PathVariable(name = "test") String test) {

        return measureService.findByTest_Lot(test)
                .stream()
                .map(MappingUtils::mapToMeasureDto)
                .collect(Collectors.toList());
    }
    public List<Measure> measureByTestLot(String testLot) {
        return measureService.findByTest_Lot(testLot);
    }

}
