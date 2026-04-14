package com.example.demo.controller;

import com.example.demo.model.dto.AMGMeasureDTO;
import com.example.demo.model.json.CriteriaData;
import com.example.demo.model.qc.amg.AMGLot;
import com.example.demo.model.qc.amg.AMGMeasure;
import com.example.demo.model.qc.amg.AMGTest;
import com.example.demo.model.real.QCServiceAgregator;
import com.example.demo.service.*;
import com.example.demo.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/amgqc")
public class AMGQCController {

  private AMGLotService lotService;
  private AMGTestService testService;
  private AMGMeasureService measureService;
  private QCServiceAgregator agregator;

    @Autowired
    public AMGQCController(AMGLotService lotService, AMGTestService testService, AMGMeasureService measureService, QCServiceAgregator agregator) {
        this.lotService = lotService;
        this.testService = testService;
        this.measureService = measureService;
        this.agregator = agregator;
    }
    @GetMapping("/getLotinuse")
    public AMGLot getByInUse() {
        AMGLot lot = lotService.getByUse("1");
        lot.setDateFrom(LocalDate.now().atStartOfDay());
        return lot;
    }
    @GetMapping("/getTopLot/{lot}")
    public AMGLot getTopLot(@PathVariable("lot") String lot) {
        AMGLot lot1= new AMGLot(null, "ВЛК AMG",null, null, null, 0,null, "1");
        lot1.setDateFrom(LocalDate.now().atStartOfDay());
        AMGLot lot2 = lotService.getTopByLot(lot);
        if (lot2!=null) {
            lot2.setDateFrom(LocalDate.now().atStartOfDay());
            lot2.setInuse("1");
        }

        return lot2!=null?lot2:lot1;
    }

    @GetMapping("/getTestinuse")
    public AMGTest getTestByInUse() {
        AMGTest test = testService.findByInuse("1");
        test.setDateFrom(LocalDate.now().atStartOfDay());
        return test;
    }

    @GetMapping("/getTopTest/{test}")
    public AMGTest getTopTest(@PathVariable("test") String test) {
        AMGTest test1= new AMGTest(null, "AMG test",null, null, null, 0,null, "1");
        test1.setDateFrom(LocalDate.now().atStartOfDay());
        AMGTest test2 = testService.getTopByLotOrderByNumberDesc(test);
        if (test2!=null) {
            test2.setDateFrom(LocalDate.now().atStartOfDay());
            test2.setInuse("1");
        }

        return test2!=null?test2:test1;
    }

    @PostMapping("/savelot")
    public void saveLots(@RequestBody() AMGLot lot) {
        if (lot.getInuse().equals("1")) {
            for (AMGLot l: lotService.getLotsInuse("1")) {
                l.setInuse("0");
                lotService.save(l);
            }
        }
//        System.out.println(lot);
        lotService.save(lot);
    }
    @PostMapping("/savetest")
    public void saveTest(@RequestBody() AMGTest test) {
        if (test.getInuse().equals("1")) {
            for (AMGTest t : testService.getAllByInuse("1")) {
                t.setInuse("0");
                testService.save(t);
            }
//        System.out.println(lot);
            testService.save(test);
        }
    }
    @PostMapping("/savemeasure")
    public void saveTest(AMGMeasure measure) {
//        System.out.println(lot);
        measure.setTest(testService.findByInuse("1"));
        measure.setLot(lotService.getByUse("1"));
        measureService.save(measure);
//        System.out.println(measure);
    }
    @GetMapping("/getInUse")
    public String[] getInUse() {
        AMGLot l=lotService.getByUse("1");
        AMGTest t = testService.findByInuse("1");
        String[] uses = new String[4];
        uses[0]=l.getLot();
        uses[1]=t.getLot();
        uses[2]=l.getName();
        uses[3]=t.getName();
        return uses;
    }
    @GetMapping("/getLotMeasure/{name}")
    public List<AMGMeasure> getLotMeasure(@PathVariable("name") String name) {
         return lotService.getByUse("1").getMeasureList();
    }

    @GetMapping("/getmeasuremap/date/{date}")
    public Map<String,List<AMGMeasure>> getMeasuresByDate(@PathVariable(name = "date") String date) {
        String[] dates =date.split(" ");
        Map<String, List<AMGMeasure>> testMap = new HashMap<>();
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
                        List<AMGMeasure> measures = new ArrayList<>();
                        measures.add(measure);
                        testMap.put(testLot, measures);
                    }
                });

        return testMap;
    }

    @GetMapping("/getmeasuremap/{lot}/{test}/{date}")
    public Map<Integer,List<AMGMeasure>> getMeasuresByAll(@PathVariable(name = "lot") String lot, @PathVariable(name = "test") String test,
    @PathVariable(name = "date") String date) {
        String[] dates =date.split(" ");
        Map<Integer, List<AMGMeasure>> testMap = new HashMap<>();
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
                        List<AMGMeasure> measures = new ArrayList<>();
                        measures.add(measure);
                        testMap.put(testNumber, measures);
                    }
                });

        return testMap;
    }

    @PostMapping("/controlmap")
    public Map<String,AMGMeasure> getControlsByAll(@RequestBody CriteriaData data) {
        String[] dates =data.getDate().split(" ");
        Map<String, AMGMeasure> testMap = new HashMap<>();
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
    public Map<Integer,List<AMGMeasure>> getMeasures(@PathVariable(name = "lot") String lot, @PathVariable(name = "test") String test) {
        Map<Integer, List<AMGMeasure>> testMap = new HashMap<>();
              measureService.findAll()
                .stream()
                .filter(m-> m.getLot().getLot().equals(lot)&&m.getTest().getLot().equals(test))
                      .forEach(measure -> {
                          Integer testNumber=measure.getTest().getNumber();
                          if (testMap.containsKey(testNumber)){
                              testMap.get(testNumber).add(measure);
                          } else {
                              List<AMGMeasure> measures = new ArrayList<>();
                              measures.add(measure);
                              testMap.put(testNumber, measures);
                          }
//                          lotMap.put(measure.getLot().getNumber(), measure.getLot().getMeasureList());
                      });
      return testMap;
    }

    @GetMapping("/getmeasuremap/{lot}")
    public Map<String,List<AMGMeasure>> getMeasuresByLot(@PathVariable(name = "lot") String lot) {
        Map<String, List<AMGMeasure>> testMap = new HashMap<>();
        measureService.findAll()
                .stream()
                .filter(m-> m.getLot().getLot().equals(lot))
                .forEach(measure -> {
                    String testLot=measure.getTest().getLot();
                    if (testMap.containsKey(testLot)){
                        testMap.get(testLot).add(measure);
                    } else {
                        List<AMGMeasure> measures = new ArrayList<>();
                        measures.add(measure);
                        testMap.put(testLot, measures);
                    }
                });

        return testMap;
    }
    @GetMapping("/getmeasuremap/test/{test}")
    public Map<Integer,List<AMGMeasure>> getMeasuresByTest(@PathVariable(name = "test") String test) {
        Map<Integer, List<AMGMeasure>> testMap = new HashMap<>();
        measureService.findAll()
                .stream()
                .filter(m-> m.getTest().getLot().equals(test))
                .forEach(measure -> {
                    Integer testLot=measure.getTest().getNumber();
                    if (testMap.containsKey(testLot)){
                        testMap.get(testLot).add(measure);
                    } else {
                        List<AMGMeasure> measures = new ArrayList<>();
                        measures.add(measure);
                        testMap.put(testLot, measures);
                    }
                });

        return testMap;
    }
    @GetMapping("/getmeasurelistsByDateFrom/{date}")
    public List<AMGMeasureDTO> findByMeasure_dateAfter(@PathVariable(name = "date") String dateAfter) {
        String[] dates =dateAfter.split(" ");
        return measureService.findAll()
                .stream()
                .filter(m->dates.length>1?m.getMeasure_date().isAfter(LocalDateTime.parse(dates[0]))
                        && m.getMeasure_date().isBefore(LocalDateTime.parse(dates[1]))
                        :m.getMeasure_date().isAfter(LocalDateTime.parse(dateAfter)))
                .map(MappingUtils::mapToAMGMeasureDto)
                .collect(Collectors.toList());
    }
    @GetMapping("/getmeasurelistsByLot/{lot}")
    public List<AMGMeasureDTO> findMeasureByLotlot(@PathVariable(name = "lot") String lot) {

        return measureService.findByLot_Lot(lot)
                .stream()
                .map(MappingUtils::mapToAMGMeasureDto)
                .collect(Collectors.toList());
    }
    @GetMapping("/getmeasurelistsByTest/{test}")
    public List<AMGMeasureDTO> findMeasureByTestlot(@PathVariable(name = "test") String test) {

        return measureService.findByTest_Lot(test)
                .stream()
                .map(MappingUtils::mapToAMGMeasureDto)
                .collect(Collectors.toList());
    }
    public List<AMGMeasure> measureByTestLot(String testLot) {
        return measureService.findByTest_Lot(testLot);
    }

    @GetMapping("/saveLoadedMeasures")
    public void saveLoadedMeasures() throws IOException {

        agregator.realRParse();

//        File folderPSAt = new File("\\\\192.168.7.100\\ifa\\ВЛК\\AMG");
//        File[] listOfFilesPSAt = folderPSAt.listFiles();
//        for (int i = 0; i < (listOfFilesPSAt != null ? listOfFilesPSAt.length : 0); i++) {
//            if (listOfFilesPSAt[i].isFile()) {
//                Map<String, LocalDateTime> map = InputUtils.amgAnaliz(folderPSAt + "\\" + listOfFilesPSAt[i].getName());
//                map.entrySet().stream()
//                        .sorted(Map.Entry.comparingByValue())
//                        .forEach(mp-> {
//                            AMGMeasure m = new AMGMeasure();
//                            m.setMeasure_date(mp.getValue());
//                            m.setMeasure_val(mp.getKey());
//                            m.setMeasure_type("AMG");
//                            saveTest(m);
//                        });
//
//                /*Переносим файл в другую папку*/
//                File filePSAt = new File(folderPSAt + "\\" + listOfFilesPSAt[i].getName());
//                // Destination directory
//                File dirPSAt = new File("\\\\192.168.7.100\\ifa\\Backup\\ВЛК\\AMG");
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
        LocalDateTime ldt =LocalDateTime.parse("2023-12-16T00:00");
        LocalDateTime ldt1 =LocalDateTime.parse("2024-01-16T00:00");

        for (String s:series) {

            int count = 1;
            while (count < 11) {
                AMGTest t = new AMGTest(null,"AMG test",s, ldt.plusMonths(count),
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
                AMGLot l = new AMGLot(null,"ВЛК AMG",s, ldt.plusMonths(count),
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
                AMGLot l = lotService.findByNameAndLotAndNumber("ВЛК AMG", lot, lotBox);
                AMGTest t = testService.findByNameAndLotAndNumber("AMG test", test, box);
               AMGMeasure m = new AMGMeasure(null, "AMG", null,null,l,t);
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
