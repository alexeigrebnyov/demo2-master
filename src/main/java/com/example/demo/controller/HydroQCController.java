package com.example.demo.controller;

import com.example.demo.model.dto.HydroMeasureDTO;
import com.example.demo.model.json.CriteriaData;
import com.example.demo.model.qc.hydroxyprog.HydroLot;
import com.example.demo.model.qc.hydroxyprog.HydroMeasure;
import com.example.demo.model.qc.hydroxyprog.HydroTest;
import com.example.demo.model.real.QCServiceAgregator;
import com.example.demo.service.*;
import com.example.demo.utils.InputUtils;
import com.example.demo.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hydroqc")
public class HydroQCController {

  private HydroLotService lotService;
  private HydroTestService testService;
  private HydroMeasureService measureService;
  private QCServiceAgregator agregator;

    @Autowired
    public HydroQCController(HydroLotService lotService, HydroTestService testService, HydroMeasureService measureService, QCServiceAgregator agregator) {
        this.lotService = lotService;
        this.testService = testService;
        this.measureService = measureService;
        this.agregator = agregator;
    }
    @GetMapping("/getLotinuse")
    public HydroLot getByInUse() {
        HydroLot lot = lotService.getByUse("1");
        lot.setDateFrom(LocalDate.now().atStartOfDay());
        return lot;
    }
    @GetMapping("/getTopLot/{lot}")
    public HydroLot getTopLot(@PathVariable("lot") String lot) {
        HydroLot lot1= new HydroLot(null, "ВЛК 17OH",null, null, null, 0,null, "1");
        lot1.setDateFrom(LocalDate.now().atStartOfDay());
        HydroLot lot2 = lotService.getTopByLot(lot);
        if (lot2!=null) {
            lot2.setDateFrom(LocalDate.now().atStartOfDay());
            lot2.setInuse("1");
        }

        return lot2!=null?lot2:lot1;
    }

    @GetMapping("/getTestinuse")
    public HydroTest getTestByInUse() {
        HydroTest test = testService.findByInuse("1");
        test.setDateFrom(LocalDate.now().atStartOfDay());
        return test;
    }

    @GetMapping("/getTopTest/{test}")
    public HydroTest getTopTest(@PathVariable("test") String test) {
        HydroTest test1= new HydroTest(null, "17OH test",null, null, null, 0,null, "1");
        test1.setDateFrom(LocalDate.now().atStartOfDay());
        HydroTest test2 = testService.getTopByLotOrderByNumberDesc(test);
        if (test2!=null) {
            test2.setDateFrom(LocalDate.now().atStartOfDay());
            test2.setInuse("1");
        }

        return test2!=null?test2:test1;
    }

    @PostMapping("/savelot")
    public void saveLots(@RequestBody() HydroLot lot) {
        if (lot.getInuse().equals("1")) {
            for (HydroLot l: lotService.getLotsInuse("1")) {
                l.setInuse("0");
                lotService.save(l);
            }
        }
//        System.out.println(lot);
        lotService.save(lot);
    }
    @PostMapping("/savetest")
    public void saveTest(@RequestBody() HydroTest test) {
        if (test.getInuse().equals("1")) {
            for (HydroTest t : testService.getAllByInuse("1")) {
                t.setInuse("0");
                testService.save(t);
            }
//        System.out.println(lot);
            testService.save(test);
        }
    }
    @PostMapping("/savemeasure")
    public void saveTest(HydroMeasure measure) {
//        System.out.println(lot);
        measure.setTest(testService.findByInuse("1"));
        measure.setLot(lotService.getByUse("1"));
        measureService.save(measure);
//        System.out.println(measure);
    }
    @GetMapping("/getInUse")
    public String[] getInUse() {
        HydroLot l=lotService.getByUse("1");
        HydroTest t = testService.findByInuse("1");
        String[] uses = new String[4];
        uses[0]=l.getLot();
        uses[1]=t.getLot();
        uses[2]=l.getName();
        uses[3]=t.getName();
        return uses;
    }
    @GetMapping("/getLotMeasure/{name}")
    public List<HydroMeasure> getLotMeasure(@PathVariable("name") String name) {
         return lotService.getByUse("1").getMeasureList();
    }

    @GetMapping("/getmeasuremap/date/{date}")
    public Map<String,List<HydroMeasure>> getMeasuresByDate(@PathVariable(name = "date") String date) {
        String[] dates =date.split(" ");
        Map<String, List<HydroMeasure>> testMap = new HashMap<>();
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
                        List<HydroMeasure> measures = new ArrayList<>();
                        measures.add(measure);
                        testMap.put(testLot, measures);
                    }
                });

        return testMap;
    }

    @GetMapping("/getmeasuremap/{lot}/{test}/{date}")
    public Map<Integer,List<HydroMeasure>> getMeasuresByAll(@PathVariable(name = "lot") String lot, @PathVariable(name = "test") String test,
    @PathVariable(name = "date") String date) {
        String[] dates =date.split(" ");
        Map<Integer, List<HydroMeasure>> testMap = new HashMap<>();
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
                        List<HydroMeasure> measures = new ArrayList<>();
                        measures.add(measure);
                        testMap.put(testNumber, measures);
                    }
                });

        return testMap;
    }

    @PostMapping("/controlmap")
    public Map<String,HydroMeasure> getControlsByAll(@RequestBody CriteriaData data) {
        String[] dates =data.getDate().split(" ");
        Map<String, HydroMeasure> testMap = new HashMap<>();
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
    public Map<Integer,List<HydroMeasure>> getMeasures(@PathVariable(name = "lot") String lot, @PathVariable(name = "test") String test) {
        Map<Integer, List<HydroMeasure>> testMap = new HashMap<>();
              measureService.findAll()
                .stream()
                .filter(m-> m.getLot().getLot().equals(lot)&&m.getTest().getLot().equals(test))
                      .forEach(measure -> {
                          Integer testNumber=measure.getTest().getNumber();
                          if (testMap.containsKey(testNumber)){
                              testMap.get(testNumber).add(measure);
                          } else {
                              List<HydroMeasure> measures = new ArrayList<>();
                              measures.add(measure);
                              testMap.put(testNumber, measures);
                          }
//                          lotMap.put(measure.getLot().getNumber(), measure.getLot().getMeasureList());
                      });
      return testMap;
    }

    @GetMapping("/getmeasuremap/{lot}")
    public Map<String,List<HydroMeasure>> getMeasuresByLot(@PathVariable(name = "lot") String lot) {
        Map<String, List<HydroMeasure>> testMap = new HashMap<>();
        measureService.findAll()
                .stream()
                .filter(m-> m.getLot().getLot().equals(lot))
                .forEach(measure -> {
                    String testLot=measure.getTest().getLot();
                    if (testMap.containsKey(testLot)){
                        testMap.get(testLot).add(measure);
                    } else {
                        List<HydroMeasure> measures = new ArrayList<>();
                        measures.add(measure);
                        testMap.put(testLot, measures);
                    }
                });

        return testMap;
    }
    @GetMapping("/getmeasuremap/test/{test}")
    public Map<Integer,List<HydroMeasure>> getMeasuresByTest(@PathVariable(name = "test") String test) {
        Map<Integer, List<HydroMeasure>> testMap = new HashMap<>();
        measureService.findAll()
                .stream()
                .filter(m-> m.getTest().getLot().equals(test))
                .forEach(measure -> {
                    Integer testLot=measure.getTest().getNumber();
                    if (testMap.containsKey(testLot)){
                        testMap.get(testLot).add(measure);
                    } else {
                        List<HydroMeasure> measures = new ArrayList<>();
                        measures.add(measure);
                        testMap.put(testLot, measures);
                    }
                });

        return testMap;
    }
    @GetMapping("/getmeasurelistsByDateFrom/{date}")
    public List<HydroMeasureDTO> findByMeasure_dateAfter(@PathVariable(name = "date") String dateAfter) {
        String[] dates =dateAfter.split(" ");

//       return testService.findByInuse("1")
//                .getMeasureList()
//                .stream()
//                .map(MappingUtils::mapToHydroMeasureDto)
//                .collect(Collectors.toList());
        return measureService.findAll()
                .stream()
                .filter(m->dates.length>1?m.getMeasure_date().isAfter(LocalDateTime.parse(dates[0]))
                        && m.getMeasure_date().isBefore(LocalDateTime.parse(dates[1]))
                        :m.getMeasure_date().isAfter(LocalDateTime.parse(dateAfter)))
                .map(MappingUtils::mapToHydroMeasureDto)
                .collect(Collectors.toList());
    }
    @GetMapping("/getmeasurelistsByLot/{lot}")
    public List<HydroMeasureDTO> findMeasureByLotlot(@PathVariable(name = "lot") String lot) {

        return measureService.findByLot_Lot(lot)
                .stream()
                .map(MappingUtils::mapToHydroMeasureDto)
                .collect(Collectors.toList());
    }
    @GetMapping("/getmeasurelistsByTest/{test}")
    public List<HydroMeasureDTO> findMeasureByTestlot(@PathVariable(name = "test") String test) {

        return measureService.findByTest_Lot(test)
                .stream()
                .map(MappingUtils::mapToHydroMeasureDto)
                .collect(Collectors.toList());
    }
    public List<HydroMeasure> measureByTestLot(String testLot) {
        return measureService.findByTest_Lot(testLot);
    }

    @GetMapping("/saveLoadedMeasures")
    public void saveLoadedMeasures() throws IOException {
//        File folderPSAt = new File("\\\\192.168.7.100\\ifa\\ВЛК\\17-OH");
//        File[] listOfFilesPSAt = folderPSAt.listFiles();
//        for (int i = 0; i < (listOfFilesPSAt != null ? listOfFilesPSAt.length : 0); i++) {
//            if (listOfFilesPSAt[i].isFile()) {
//                Map<String, LocalDateTime> map = InputUtils.hydroxyAnaliz(folderPSAt + "\\" + listOfFilesPSAt[i].getName());
//                map.entrySet().stream()
//                        .sorted(Map.Entry.comparingByValue())
//                        .forEach(mp-> {
//                            HydroMeasure m = new HydroMeasure();
//                            m.setMeasure_date(mp.getValue());
//                            m.setMeasure_val(mp.getKey());
//                            m.setMeasure_type("17-OH");
//                            saveTest(m);
//                        });
//
//                /*Переносим файл в другую папку*/
//                File filePSAt = new File(folderPSAt + "\\" + listOfFilesPSAt[i].getName());
//                // Destination directory
//                File dirPSAt = new File("\\\\192.168.7.100\\ifa\\Backup\\ВЛК\\17-OH");
//                // Move file to new directory
//                boolean success = filePSAt.renameTo(new File(dirPSAt, filePSAt.getName()
//                        .replaceAll(".txt","_")+LocalDate.now()+".txt"));
//                if (!success) {
//                    System.out.print("not good");
//                }
//            }
//        }
            agregator.realRParse();
    }

    @GetMapping("/writeTests")
    public void writeTestData()  {
        List<String> series = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        LocalDateTime ldt =LocalDateTime.parse("2023-12-16T00:00");
        LocalDateTime ldt1 =LocalDateTime.parse("2024-01-16T00:00");

        for (String s:series) {

            int count = 1;
            while (count < 11) {
                HydroTest t = new HydroTest(null,"17OH test",s, ldt.plusMonths(count),
                        ldt1.plusMonths(count), count, null, "0");
                testService.save(t);

                count++;
            }
        }
    }


    @GetMapping("/writeLots")
    public void writeLotData()  {
        List<String> series = List.of("1", "2", "3", "4", "5");
        LocalDateTime ldt =LocalDateTime.parse("2023-12-16T00:00");
        LocalDateTime ldt1 =LocalDateTime.parse("2024-01-16T00:00");
        for (String s:series) {

            int count = 1;
            while (count < 21) {
                HydroLot l = new HydroLot(null,"ВЛК 17OH",s, ldt.plusMonths(count),
                        ldt1.plusMonths(count), count, null, "0");
                lotService.save(l);

                count++;
            }
        }
    }

    @GetMapping("/writeMeasures/{from}/{to}/{lot}/{test}/{begin}")
    public  void getScanData(@PathVariable("from") int from, @PathVariable("to") int to, @PathVariable("lot")String lot, @PathVariable("test") String test,
                             @PathVariable("begin") int begin) throws FileNotFoundException {
        LocalDateTime ldt =LocalDateTime.parse("2023-12-16T00:00");
        LocalDateTime ldt1 =LocalDateTime.parse("2024-01-16T00:00");

//        File f = new File("c:/Users/Grebnev_A/Downloads/HIVAb.txt");
//        Scanner sc = new Scanner(f);
        int count = 0;
        while ( count<484) {
            int lotBox=0;
            int box=0;
//            Scanner scanner = new Scanner(sc.nextLine());
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
                HydroLot l = lotService.findByNameAndLotAndNumber("ВЛК 17OH", lot, lotBox);
                HydroTest t = testService.findByNameAndLotAndNumber("17OH test", test, box);
               HydroMeasure m = new HydroMeasure(null, "17-OH", null,null,l,t);
                try {
//                    DateTimeFormatter dTF = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//                    LocalDate ldt = LocalDate.parse(scanner.next(), dTF);
//                    String d = scanner.next().replaceAll(",", ".");
//                    System.out.println(count + " " + ldt + " " + d +" "+test+box+" "+lot+lotBox);
                    m.setMeasure_date(ldt.plusDays(count));
                    m.setMeasure_val(String.valueOf(Math.random() * 6 + 1));
                } catch (NumberFormatException ignored) {
                }
                measureService.save(m);
            }
            count++;
        }
//        sc.close();
    }
    @GetMapping("/date")
    public LocalDate getMeasureByDate() {

        return measureService.findByMeasure_date();
    }




}
