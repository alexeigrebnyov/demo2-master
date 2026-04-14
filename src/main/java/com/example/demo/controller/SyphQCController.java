package com.example.demo.controller;

import com.example.demo.model.dto.SyphMeasureDTO;
import com.example.demo.model.json.CriteriaData;
import com.example.demo.model.qc.syph.SyphLot;
import com.example.demo.model.qc.syph.SyphMeasure;
import com.example.demo.model.qc.syph.SyphTest;
import com.example.demo.model.real.QCServiceAgregator;
import com.example.demo.service.*;
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
@RequestMapping("/syphqc")
public class SyphQCController {

  private SyphLotService lotService;
  private SyphTestService testService;
  private SyphMeasureService measureService;
  private QCServiceAgregator agregator;

    @Autowired
    public SyphQCController(SyphLotService lotService, SyphTestService testService, SyphMeasureService measureService, QCServiceAgregator agregator) {
        this.lotService = lotService;
        this.testService = testService;
        this.measureService = measureService;
        this.agregator = agregator;
    }
    @GetMapping("/getLotinuse")
    public SyphLot getByInUse() {
        SyphLot lot = lotService.getByUse("1");
        lot.setDateFrom(LocalDate.now().atStartOfDay());
        return lot;
    }
    @GetMapping("/getTopLot/{lot}")
    public SyphLot getTopLot(@PathVariable("lot") String lot) {
        SyphLot lot1= new SyphLot(null, "ВЛК SyphAT",null, null, null, 0,null, "1");
        lot1.setDateFrom(LocalDate.now().atStartOfDay());
        SyphLot lot2 = lotService.getTopByLot(lot);
        if (lot2!=null) {
            lot2.setDateFrom(LocalDate.now().atStartOfDay());
            lot2.setInuse("1");
        }

        return lot2!=null?lot2:lot1;
    }

    @GetMapping("/getTestinuse")
    public SyphTest getTestByInUse() {
        SyphTest test = testService.findByInuse("1");
        test.setDateFrom(LocalDate.now().atStartOfDay());
        return test;
    }

    @GetMapping("/getTopTest/{test}")
    public SyphTest getTopTest(@PathVariable("test") String test) {
        SyphTest test1= new SyphTest(null, "T.PallidumATtotal",null, null, null, 0,null, "1");
        test1.setDateFrom(LocalDate.now().atStartOfDay());
        SyphTest test2 = testService.getTopByLotOrderByNumberDesc(test);
        if (test2!=null) {
            test2.setDateFrom(LocalDate.now().atStartOfDay());
            test2.setInuse("1");
        }

        return test2!=null?test2:test1;
    }

    @PostMapping("/savelot")
    public void saveLots(@RequestBody() SyphLot lot) {
        if (lot.getInuse().equals("1")) {
            for (SyphLot l: lotService.getLotsInuse("1")) {
                l.setInuse("0");
                lotService.save(l);
            }
        }
//        System.out.println(lot);
        lotService.save(lot);
    }
    @PostMapping("/savetest")
    public void saveTest(@RequestBody() SyphTest test) {
        if (test.getInuse().equals("1")) {
            for (SyphTest t : testService.getAllByInuse("1")) {
                t.setInuse("0");
                testService.save(t);
            }
//        System.out.println(lot);
            testService.save(test);
        }
    }
    @PostMapping("/savemeasure")
    public void saveTest(SyphMeasure measure) {
//        System.out.println(lot);
        measure.setTest(testService.findByInuse("1"));
        measure.setLot(lotService.getByUse("1"));
        measureService.save(measure);
//        System.out.println(measure);
    }
    @GetMapping("/getInUse")
    public String[] getInUse() {
        SyphLot l=lotService.getByUse("1");
        SyphTest t = testService.findByInuse("1");
        String[] uses = new String[4];
        uses[0]=l.getLot();
        uses[1]=t.getLot();
        uses[2]=l.getName();
        uses[3]=t.getName();
        return uses;
    }
    @GetMapping("/getLotMeasure/{name}")
    public List<SyphMeasure> getLotMeasure(@PathVariable("name") String name) {
         return lotService.getByUse("1").getMeasureList();
    }

    @GetMapping("/getmeasuremap/date/{date}")
    public Map<String,List<SyphMeasure>> getMeasuresByDate(@PathVariable(name = "date") String date) {
        String[] dates =date.split(" ");
        Map<String, List<SyphMeasure>> testMap = new HashMap<>();
        measureService.findAll()
                .stream()
                .filter(m-> dates.length>1? (m.getMeasure_date().isAfter(LocalDateTime.parse(dates[0]))
                        && m.getMeasure_date().isBefore(LocalDateTime.parse(dates[1]))):
                        m.getMeasure_date().isAfter(LocalDateTime.parse(date))
                )
                .forEach(measure -> {
                    String testLot=measure.getTest().getLot();

                    if (testMap.containsKey(testLot)) {
                        testMap.get(testLot).add(measure);
                    } else {
                        List<SyphMeasure> measures = new ArrayList<>();
                        measures.add(measure);
                        testMap.put(testLot, measures);
                    }
                });

        return testMap;
    }

    @GetMapping("/getmeasuremap/{lot}/{test}/{date}")
    public Map<Integer,List<SyphMeasure>> getMeasuresByAll(@PathVariable(name = "lot") String lot, @PathVariable(name = "test") String test,
    @PathVariable(name = "date") String date) {
        String[] dates =date.split(" ");
        Map<Integer, List<SyphMeasure>> testMap = new HashMap<>();
        measureService.findAll()
                .stream()
                .filter(m-> m.getLot().getLot().equals(lot)&&m.getTest().getLot().equals(test)
                && dates.length>1? m.getMeasure_date().isAfter(LocalDateTime.parse(dates[0]))
                && m.getMeasure_date().isBefore(LocalDateTime.parse(dates[1])):
                    m.getMeasure_date().isAfter(LocalDateTime.parse(date))
                        )
                .forEach(measure -> {
                    Integer testNumber=measure.getTest().getNumber();
                    if (testMap.containsKey(testNumber)){
                        testMap.get(testNumber).add(measure);
                    } else {
                        List<SyphMeasure> measures = new ArrayList<>();
                        measures.add(measure);
                        testMap.put(testNumber, measures);
                    }
                });

        return testMap;
    }

    @PostMapping("/controlmap")
    public Map<String,SyphMeasure> getControlsByAll(@RequestBody CriteriaData data) {
        String[] dates =data.getDate().split(" ");
        Map<String, SyphMeasure> testMap = new HashMap<>();
        measureService.findAll()
                .stream()
                .filter(m-> !data.getLot().equals("")?m.getLot().getLot().equals(data.getLot()):true)
                .filter(m-> !data.getTest().equals("")?m.getTest().getLot().equals(data.getTest()):true)
                .forEach(measure -> {
                    String lotLot=measure.getLot().getLot()+measure.getLot().getNumber();
                    testMap.put(lotLot, measure);
                });
        return testMap;
    }

    @GetMapping("/getmeasuremap/{lot}/{test}")
    public Map<Integer,List<SyphMeasure>> getMeasures(@PathVariable(name = "lot") String lot, @PathVariable(name = "test") String test) {
        Map<Integer, List<SyphMeasure>> testMap = new HashMap<>();
              measureService.findAll()
                .stream()
                .filter(m-> m.getLot().getLot().equals(lot)&&m.getTest().getLot().equals(test))
                      .forEach(measure -> {
                          Integer testNumber=measure.getTest().getNumber();
                          if (testMap.containsKey(testNumber)){
                              testMap.get(testNumber).add(measure);
                          } else {
                              List<SyphMeasure> measures = new ArrayList<>();
                              measures.add(measure);
                              testMap.put(testNumber, measures);
                          }
//                          lotMap.put(measure.getLot().getNumber(), measure.getLot().getMeasureList());
                      });
      return testMap;
    }

    @GetMapping("/getmeasuremap/{lot}")
    public Map<String,List<SyphMeasure>> getMeasuresByLot(@PathVariable(name = "lot") String lot) {
        Map<String, List<SyphMeasure>> testMap = new HashMap<>();
        measureService.findAll()
                .stream()
                .filter(m-> m.getLot().getLot().equals(lot))
                .forEach(measure -> {
                    String testLot=measure.getTest().getLot();
                    if (testMap.containsKey(testLot)){
                        testMap.get(testLot).add(measure);
                    } else {
                        List<SyphMeasure> measures = new ArrayList<>();
                        measures.add(measure);
                        testMap.put(testLot, measures);
                    }
                });

        return testMap;
    }
    @GetMapping("/getmeasuremap/test/{test}")
    public Map<Integer,List<SyphMeasure>> getMeasuresByTest(@PathVariable(name = "test") String test) {
        Map<Integer, List<SyphMeasure>> testMap = new HashMap<>();
        measureService.findAll()
                .stream()
                .filter(m-> m.getTest().getLot().equals(test))
                .forEach(measure -> {
                    Integer testLot=measure.getTest().getNumber();
                    if (testMap.containsKey(testLot)){
                        testMap.get(testLot).add(measure);
                    } else {
                        List<SyphMeasure> measures = new ArrayList<>();
                        measures.add(measure);
                        testMap.put(testLot, measures);
                    }
                });

        return testMap;
    }
    @GetMapping("/getmeasurelistsByDateFrom/{date}")
    public List<SyphMeasureDTO> findByMeasure_dateAfter(@PathVariable(name = "date") String dateAfter) {
        String[] dates =dateAfter.split(" ");
        return measureService.findAll()
                .stream()
                .filter(m->dates.length>1?m.getMeasure_date().isAfter(LocalDateTime.parse(dates[0]))
                        && m.getMeasure_date().isBefore(LocalDateTime.parse(dates[1]))
                        :m.getMeasure_date().isAfter(LocalDateTime.parse(dateAfter)))
                .map(MappingUtils::mapToSyphMeasureDto)
                .collect(Collectors.toList());
    }
    @GetMapping("/getmeasurelistsByLot/{lot}")
    public List<SyphMeasureDTO> findMeasureByLotlot(@PathVariable(name = "lot") String lot) {

        return measureService.findByLot_Lot(lot)
                .stream()
                .map(MappingUtils::mapToSyphMeasureDto)
                .collect(Collectors.toList());
    }
    @GetMapping("/getmeasurelistsByTest/{test}")
    public List<SyphMeasureDTO> findMeasureByTestlot(@PathVariable(name = "test") String test) {

        return measureService.findByTest_Lot(test)
                .stream()
                .map(MappingUtils::mapToSyphMeasureDto)
                .collect(Collectors.toList());
    }
    public List<SyphMeasure> measureByTestLot(String testLot) {
        return measureService.findByTest_Lot(testLot);
    }

    @GetMapping("/saveLoadedMeasures")
    public void saveLoadedMeasures() throws IOException {

            agregator.realRParse();
//        File folderPSAt = new File("\\\\192.168.7.100\\ifa\\ВЛК\\Syph");
//        File[] listOfFilesPSAt = folderPSAt.listFiles();
//        for (int i = 0; i < (listOfFilesPSAt != null ? listOfFilesPSAt.length : 0); i++) {
//            if (listOfFilesPSAt[i].isFile()) {
//                Map<String, LocalDateTime> map = InputUtils.analiz(folderPSAt + "\\" + listOfFilesPSAt[i].getName());
////                for (String val:map.keySet()) {
////                    SyphMeasure m = new SyphMeasure();
////                    m.setMeasure_date(map.get(val));
////                    m.setMeasure_val(val);
////                    m.setMeasure_type("SyphIFA");
////                    saveTest(m);
////                }
//
//                map.entrySet().stream()
//                        .sorted(Map.Entry.comparingByValue())
//                        .forEach(mp-> {
//                            SyphMeasure m = new SyphMeasure();
//                    m.setMeasure_date(mp.getValue());
//                    m.setMeasure_val(mp.getKey());
//                    m.setMeasure_type("SyphIFA");
//                    saveTest(m);
//                        });
//
//                /*Переносим файл в другую папку*/
//                File filePSAt = new File(folderPSAt + "\\" + listOfFilesPSAt[i].getName());
//                // Destination directory
//                File dirPSAt = new File("\\\\192.168.7.100\\ifa\\Backup\\ВЛК\\Syph");
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
        List<String> series = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        for (String s:series) {

            int count = 1;
            while (count < 11) {
                SyphTest t = new SyphTest(null,"T.PallidumATtotal",s, LocalDateTime.parse("2022-07-16T00:00"),
                        LocalDateTime.parse("2023-07-16T00:00"), count, null, "0");
                testService.save(t);

                count++;
            }
        }
    }


    @GetMapping("/writeLots")
    public void writeLotData()  {
        List<String> series = List.of("1", "2", "3", "4", "5");
        for (String s:series) {

            int count = 1;
            while (count < 21) {
                SyphLot l = new SyphLot(null,"ВЛК SyphAT",s, LocalDateTime.parse("2022-07-16T00:00"),
                        LocalDateTime.parse("2023-07-16T00:00"), count, null, "0");
                lotService.save(l);

                count++;
            }
        }
    }

    @GetMapping("/writeMeasures/{from}/{to}/{lot}/{test}/{begin}")
    public  void getScanData(@PathVariable("from") int from, @PathVariable("to") int to, @PathVariable("lot")String lot, @PathVariable("test") String test,
                             @PathVariable("begin") int begin) throws FileNotFoundException {
        File f = new File("c:/Users/Grebnev_A/Downloads/Syph.txt");
        Scanner sc = new Scanner(f);
        int count = 0;
        while (sc.hasNextLine()&& count<484) {
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
                SyphLot l = lotService.findByNameAndLotAndNumber("ВЛК SyphAT", lot, lotBox);
                SyphTest t = testService.findByNameAndLotAndNumber("T.PallidumATtotal", test, box);
               SyphMeasure m = new SyphMeasure(null, "SyphIFA", null,null,l,t);
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
