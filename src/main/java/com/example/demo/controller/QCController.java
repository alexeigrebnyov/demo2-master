package com.example.demo.controller;

import com.example.demo.model.dto.MeasureDTO;
import com.example.demo.model.json.CriteriaData;
import com.example.demo.model.qc.hcv.Lot;
import com.example.demo.model.qc.hcv.Measure;
import com.example.demo.model.qc.hcv.Test;
import com.example.demo.model.real.QCServiceAgregator;
import com.example.demo.service.LotService;
import com.example.demo.service.MeasureService;
import com.example.demo.service.TestService;
import com.example.demo.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/qc")
public class QCController {

  private LotService lotService;
  private TestService testService;
  private MeasureService measureService;
  private QCServiceAgregator agregator;

    @Autowired
    public QCController(LotService lotService, TestService testService, MeasureService measureService, QCServiceAgregator agregator) {
        this.lotService = lotService;
        this.testService = testService;
        this.measureService = measureService;
        this.agregator = agregator;
    }
    @GetMapping("/getLotinuse")
    public Lot getByInUse() {
        Lot lot = lotService.getByUse("1", "ВЛК ГЕП С");
        lot.setDateFrom(LocalDate.now().atStartOfDay());
        return lot;
    }
    @GetMapping("/getTopLot/{lot}")
    public Lot getTopLot(@PathVariable("lot") String lot) {
        Lot lot1= new Lot(null, "ВЛК ГЕП С",null, null, null, 0,null, "1");
        lot1.setDateFrom(LocalDate.now().atStartOfDay());
        Lot lot2 = lotService.getTopByLot(lot);
        if (lot2!=null) {
            lot2.setDateFrom(LocalDate.now().atStartOfDay());
            lot2.setInuse("1");
        }

        return lot2!=null?lot2:lot1;
    }

    @GetMapping("/getTestinuse")
    public Test getTestByInUse() {
        Test test = testService.findByInuse("1");
        test.setDateFrom(LocalDate.now().atStartOfDay());
        return test;
    }

    @GetMapping("/getTopTest/{test}")
    public Test getTopTest(@PathVariable("test") String test) {
        Test test1= new Test(null, "ВекторБест AT_HCV",null, null, null, 0,null, "1");
        test1.setDateFrom(LocalDate.now().atStartOfDay());
        Test test2 = testService.getTopByLotOrderByNumberDesc(test);
        if (test2!=null) {
            test2.setDateFrom(LocalDate.now().atStartOfDay());
            test2.setInuse("1");
        }

        return test2!=null?test2:test1;
    }

    @PostMapping("/savelot")
    public void saveLots(@RequestBody() Lot lot) {
        if (lot.getInuse().equals("1")) {
            for (Lot l: lotService.getLotsInuse("1")) {
                l.setInuse("0");
                lotService.save(l);
            }
        }
//        System.out.println(lot);
        lotService.save(lot);
    }
    @PostMapping("/savetest")
    public void saveTest(@RequestBody() Test test) {
        if (test.getInuse().equals("1")) {
            for (Test t : testService.getAllByInuse("1")) {
                t.setInuse("0");
                testService.save(t);
            }
//        System.out.println(lot);
            testService.save(test);
        }
    }
    @PostMapping("/savemeasure")
    public void saveTest(Measure measure) {
//        System.out.println(lot);
        measure.setTest(testService.findByInuse("1"));
        measure.setLot(lotService.getByUse("1", "ВЛК ГЕП С"));
        measureService.save(measure);
//        System.out.println(measure);
    }
    @GetMapping("/getInUse")
    public String[] getInUse() {
        Lot l=lotService.getByUse("1", "ВЛК ГЕП С");
        Test t = testService.findByInuse("1");
        String[] uses = new String[4];
        uses[0]=l.getLot();
        uses[1]=t.getLot();
        uses[2]=l.getName();
        uses[3]=t.getName();
        return uses;
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

    @PostMapping("/controlmap")
    public Map<String,Measure> getControlsByAll(@RequestBody CriteriaData data) {
        System.out.println(data);
        String[] dates =data.getDate().split(" ");
        Map<String, Measure> testMap = new HashMap<>();
//      List<Measure> filteredMeasures =
        measureService.findAll()
                .stream()
//                .filter(m->
//                        dates.length>1? m.getMeasure_date().isAfter(LocalDateTime.parse(dates[0]))
//                        && m.getMeasure_date().isBefore(LocalDateTime.parse(dates[1])):
//                        m.getMeasure_date().isAfter(LocalDateTime.parse(data.getDate()))
//                )
                .filter(m-> !data.getLot().equals("")?m.getLot().getLot().equals(data.getLot()):true)
                .filter(m-> !data.getTest().equals("")?m.getTest().getLot().equals(data.getTest()):true)
//              .collect(Collectors.toList());
                .forEach(measure -> {
                    String lotLot=measure.getLot().getLot()+measure.getLot().getNumber();
//                    String testNumber=measure.getTest().getNumber();
//                    if (testMap.containsKey(lotLot)){
//                        testMap.get(lotLot).add(measure);
//                    } else {
//                        List<Measure> measures = new ArrayList<>();
//                        measures.add(measure);
//                        testMap.put(testNumber, measures);
//                    }
                    testMap.put(lotLot, measure);
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

    @GetMapping("/saveLoadedMeasures")
    public void saveLoadedMeasures() throws IOException {
        agregator.realRParse();
//        File folderPSAt = new File("\\\\192.168.7.100\\ifa\\ВЛК\\antiHCV");
//        File[] listOfFilesPSAt = folderPSAt.listFiles();
//        for (int i = 0; i < (listOfFilesPSAt != null ? listOfFilesPSAt.length : 0); i++) {
//            if (listOfFilesPSAt[i].isFile()) {
//                Map<String, LocalDateTime> map = InputUtils.analiz(folderPSAt + "\\" + listOfFilesPSAt[i].getName());
////                for (String val:map.keySet()) {
////                    Measure m = new Measure();
////                    m.setMeasure_date(map.get(val));
////                    m.setMeasure_val(val);
////                    m.setMeasure_type("anti_HCV");
////                    saveTest(m);
////                }
//
//                map.entrySet().stream()
//                        .sorted(Map.Entry.comparingByValue())
//                        .forEach(mp-> {
//                            Measure m = new  Measure();
//                            m.setMeasure_date(mp.getValue());
//                            m.setMeasure_val(mp.getKey());
//                            m.setMeasure_type("anti_HCV");
//                            saveTest(m);
//                        });
//                /*Переносим файл в другую папку*/
//                File filePSAt = new File(folderPSAt + "\\" + listOfFilesPSAt[i].getName());
//                // Destination directory
//                File dirPSAt = new File("\\\\192.168.7.100\\ifa\\Backup\\ВЛК\\antiHCV");
//                // Move file to new directory
//                boolean success = filePSAt.renameTo(new File(dirPSAt, filePSAt.getName()
//                        .replaceAll(".txt","_")+LocalDate.now()+".txt"));
//                if (!success) {
//                    System.out.print("not good");
//                }
//            }
//        }
    }

    @GetMapping("/writeTests")
    public void writeTestData()  {
        List<String> series = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9");
        for (String s:series) {

            int count = 1;
            while (count < 11) {
                Test t = new Test(null,"ВекторБест AT_HCV",s, LocalDateTime.parse("2022-07-16T00:00"),
                        LocalDateTime.parse("2023-07-16T00:00"), count, null, "0");
                testService.save(t);

                count++;
            }
        }
    }


    @GetMapping("/writeLots")
    public void writeLotData()  {
        List<String> series = List.of("1", "2", "3", "4");
        for (String s:series) {

            int count = 1;
            while (count < 21) {
                Lot l = new Lot(null,"ВЛК ГЕП С",s, LocalDateTime.parse("2022-07-16T00:00"),
                        LocalDateTime.parse("2023-07-16T00:00"), count, null, "0");
                lotService.save(l);

                count++;
            }
        }
    }

    @GetMapping("/writeMeasures/{from}/{to}/{lot}/{test}/{begin}")
    public  void getScanData(@PathVariable("from") int from, @PathVariable("to") int to, @PathVariable("lot")String lot, @PathVariable("test") String test,
                             @PathVariable("begin") int begin) throws FileNotFoundException {
        File f = new File("c:/Users/Grebnev_A/Downloads/aHCVContr.txt");
        Scanner sc = new Scanner(f);
        int count = 0;
        while (sc.hasNextLine()&& count<450) {
            int lotBox=0;
            int box=0;
            Scanner scanner = new Scanner(sc.nextLine());
            if (count > from&& count<to) {
                if (count<from+6) {box=1; lotBox=begin+1;}
                if (count>from+5&&count<from+11) {box=2; lotBox=begin+2;}
                if (count>from+10&&count<from+16) {box=3; lotBox=begin+3;}
                if (count>from+15&&count<from+21) {box=4; lotBox=begin+4;}
                if (count>from+20&&count<from+26) {box=5; lotBox=begin+5;}
                if (count>from+25&&count<from+31) {box=6; lotBox=begin+6;}
                if (count>from+30&&count<from+36) {box=7; lotBox=begin+7;}
                if (count>from+35&&count<from+41) {box=8; lotBox=begin+8;}
                if (count>from+40&&count<from+46) {box=9; lotBox=begin+9;}
                if (count>from+45&&count<from+51) {box=10; lotBox=begin+10;}
                Lot l = lotService.findByNameAndLotAndNumber("ВЛК ГЕП С", lot, lotBox);
                Test t = testService.findByNameAndLotAndNumber("ВекторБест AT_HCV", test, box);
                Measure m = new Measure(null, "anti_HCV", null,null,l,t);
                try {
                    DateTimeFormatter dTF = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    LocalDate ldt = LocalDate.parse(scanner.next(), dTF);
                    String d = scanner.next().replaceAll(",", ".");
//                    System.out.println(count + " " + ldt + " " + d +" "+test+box+" "+lot+lotBox);
                    m.setMeasure_date(ldt.atStartOfDay());
                    m.setMeasure_val(d);
                } catch (NumberFormatException ignored) {
                }
                measureService.save(m);
            }
            count++;
        }
        sc.close();
    }
    @GetMapping("/date")
    public LocalDate getMeasureByDate() {

        return measureService.findByMeasure_date();
    }



}
