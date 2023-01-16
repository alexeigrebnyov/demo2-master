package com.example.demo.controller;

import com.example.demo.model.Assignment;
import com.example.demo.model.CommonAnalysis;
import com.example.demo.model.json.PeriodData;
import com.example.demo.model.json.RequestData;
import com.example.demo.service.UptakeService;
import com.example.demo.utils.Constants;
import com.example.demo.utils.XLConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import ru.curs.xylophone.XML2SpreadSheetError;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/common")
public class CommonController {

    UptakeService uptakeService;
    RestTemplate template = new RestTemplate();
    List<CommonAnalysis> cmaList = new ArrayList<>();
    Map<String, String> getLabels() {
        Map<String, String> labels = new HashMap<>();
        labels.put("localhost:8099", "Бр.Касимовых");
        labels.put("localhost:8084", "Ижевск");
        labels.put(Constants.SERVERENDPOINT, "Киров");
        return labels;
    }


    @Autowired
    public CommonController(UptakeService uptakeService) {
        this.uptakeService=uptakeService;
    }

    @GetMapping("/")
    public void hello(){
        System.out.println("Hi!");
    }

    @GetMapping("/code/{server}")
    public ResponseEntity<List<String>> getCode(@PathVariable("server") String server) {

        RequestEntity request = RequestEntity
                .get("http://"+ server+" /update/code").build();
        ResponseEntity<String> response = new RestTemplate().exchange(request, String.class);
        List<String> codes =new ArrayList<>();
//        Test.getScan();
        codes.add(response.getBody());
        return ResponseEntity.ok(codes);

    }

//    @GetMapping("/{code}/{done}/{GPRM}/{server}")
//    public List<CommonAnalysis> getAssignment(@PathVariable("code") String bio_code, @PathVariable("done") String done, @PathVariable("GPRM") String GPRM,
//                                              @PathVariable("server") String server)
    @PostMapping("/")
    public List<CommonAnalysis> getAssignment(@RequestBody()RequestData requestData)
    throws SQLException {
        Set<Assignment> assignment = new HashSet<>();


//         new CommonAnalysis();
       List<String[]> objects= uptakeService.getDataGormonu(requestData.code, requestData.done, requestData.gprm);
        RequestEntity request = RequestEntity
                .get("http://"+ requestData.server+"/update/setcode").build();
        ResponseEntity<String> response = template.exchange(request, String.class);
//        Set<Assignment> finalAssignment = assignment;
    try {


        CommonAnalysis cma =  objects.stream()
                .findFirst()
                .map(o-> new CommonAnalysis(o[0].toString(), o[1].toString(), o[6].toString(), o[4].toString(),  o[3].toString(),assignment, false))
                .orElseThrow();

        objects.stream()
                .distinct()
                .map(o-> assignment.add(new Assignment(o[5].toString(), o[4].toString(), o[0].toString(), cma)))
                .collect(Collectors.toList());

        cma.setAssignments(assignment);
        uptakeService.saveCommon(cma);
        cmaList.add(cma);
    } catch (Exception ignored) {}




//       System.out.println(cmaList);
        return requestData.status.equals("")?cmaList.stream().distinct().collect(Collectors.toList()):
                uptakeService.getByLabel(getLabels().get(requestData.server), requestData.status.equals("отправленные") )
                        .stream().distinct().collect(Collectors.toList());

    }

    public List<CommonAnalysis> getCommons(List<String> codes, String gprm)
            throws SQLException {

        for (String code:codes ) {


            Set<Assignment> assignment = new HashSet<>();
            List<String[]> objects = uptakeService.getDataGormonu(code, "0", gprm);
            try {


                CommonAnalysis cma = objects.stream()
                        .findFirst()
                        .map(o -> new CommonAnalysis(o[0].toString(), o[1].toString(), o[6].toString(), o[4].toString(), o[3].toString(), assignment, false))
                        .orElseThrow();

                objects.stream()
                        .distinct()
                        .map(o -> assignment.add(new Assignment(o[5].toString(), o[4].toString(), o[0].toString(), cma)))
                        .collect(Collectors.toList());

                cma.setAssignments(assignment);
                uptakeService.saveCommon(cma);
                cmaList.add(cma);
            } catch (Exception ignored) {
            }

        }
        return cmaList.stream().distinct().collect(Collectors.toList());

    }

    public List<CommonAnalysis> getCytoCommons(List<String> codes, String from, String to)
            throws SQLException {
        if (cmaList.size()>0) {
            cmaList.clear();
        }
        for (String code:codes ) {


            Set<Assignment> assignment = new HashSet<>();
            List<Object[]> objects = uptakeService.getOncoCytology(code, from, to);
            try {


                CommonAnalysis cma = objects.stream()
                        .findFirst()
                        .map(o -> new CommonAnalysis(o[0].toString(), o[1].toString(), o[2].toString(), o[3].toString(), o[4].toString(), assignment, false))
                        .orElseThrow();

                objects.stream()
                        .distinct()
                        .map(o -> assignment.add(new Assignment(o[5].toString(), o[3].toString(), o[0].toString(), cma)))
                        .collect(Collectors.toList());

                cma.setAssignments(assignment);
                uptakeService.saveCommon(cma);
                cmaList.add(cma);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return cmaList.stream().distinct().collect(Collectors.toList());

    }

    @GetMapping("/commonList/{filial}/{from}/{to}")
    public List<CommonAnalysis> sbor(@PathVariable("filial") int filial, @PathVariable("from") String from,
                                     @PathVariable("to") String to) throws SQLException {
        List<CommonAnalysis> allAnalysis = new ArrayList<>();
//        List<String> strings = List.of("1059","1069", "1103");
        List<String> strings = uptakeService.getCommonData(filial,from+":00.000",
                        to+":00.000")
                .stream()
                .map(a ->a[6].toString())
                .distinct().collect(Collectors.toList());

//        System.out.println(uptakeService.getOncoCytologyCodes(filial,from, to));
//        System.out.println(getCytoCommons(uptakeService.getOncoCytologyCodes(filial,from, to)));
        allAnalysis.addAll(getCytoCommons(uptakeService.getOncoCytologyCodes(filial,from+":00.000", to+":00.000"), from+":00.000", to+":00.000"));
        allAnalysis.addAll(getCommons(strings, ""));

        return  allAnalysis;
    }

    @GetMapping("/getAll")
    public List<CommonAnalysis> getAll() {
      return   uptakeService.getCommon();
    }

//    @Transactional
    @GetMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        try {
            uptakeService.deleteCommon(id);
            cmaList.removeIf(c-> c.getId().equals(id));
        } catch (Exception e) {
//            cmaList.removeIf(c-> Objects.equals(c.getId(), id));
//           CommonAnalysis ca = uptakeService.getCommon().stream()
//                    .filter(a -> a.getEmc().equals(emc)&&a.getCode().equals(code))
//                    .findFirst().orElseThrow();
//            System.out.println("in catch "+ca);
//           uptakeService.deleteCommon(ca.getId());


        }


    }

    @GetMapping("/deleteAll")
    public void deleteAll() {
        for (CommonAnalysis ca: uptakeService.getCommon()) {
            uptakeService.deleteCommon(ca.getId());
        }
    }

    @GetMapping("/update/{label}")
    public void update(@PathVariable("label") String label) {
        uptakeService.updateByLabel(label);
    }

    @GetMapping("/getDocs")
    public List<Document> getDocument() throws XML2SpreadSheetError, IOException {
        Set<String> indets = new HashSet<>(uptakeService.getBCAsignments("351"));
        Set<String> torch = new HashSet<>(uptakeService.getBCAsignments("1"));
        Set<String> vich = new HashSet<>(uptakeService.getBCAsignments("350"));
        Set<String> bc = new HashSet<>(uptakeService.getBCAsignments( "1793, 337, 340"));
        Set<String> ifaGorm = new HashSet<>(uptakeService.getBCAsignments( "1836"));
        Set<String> coaguloGramma = new HashSet<>(uptakeService.getBCAsignments( "338"));
        Set<String> erAg = new HashSet<>(uptakeService.getBCAsignments( "349"));
        Set<String> cytolog = new HashSet<>(Set.of("онкоцитология", "bla"));
        Set<String> kario = new HashSet<>(Set.of("bla", "кариотип"));
//        System.out.println(bc);

//        indets.add("AMG");
//        indets.add("CA-125");
//        indets.add("RTH");
//        indets.add("FBC");
//        indets.add("PAA");
//        indets.add("17-OH");
//        indets.add("LH");
//        indets.add("FSH");
//
//
//        torch.add("E2");
//        torch.add("Rub-G");
//        torch.add("Chlam-G");
//        torch.add("Clam-A");
//        torch.add("Rub- M");
//        torch.add("cHSP60-Ig G(белок тепл.шока и");




        DocumentBuilderFactory dbf = null;
        DocumentBuilder db  = null;
//        Document docHiv = null;
//        Document docTorch = null;
//        Document docGorm = null;

        List<Document> documents = new ArrayList<>();
        List<CommonAnalysis> commonAnalyses = uptakeService.getCommonByStatus().stream().distinct().collect(Collectors.toList());
//        Set<CommonAnalysis> analysisSet = new HashSet<>(commonAnalyses);
//        analysisSet.forEach(System.out::println);
        List<Set<String>> commonList = List.of(indets,torch, vich, bc, ifaGorm, coaguloGramma, erAg, cytolog, kario);
        List<String> paths = List.of(
                "//192.168.7.100/ifa/ifaList/reportCommon.xlsx"
                ,"//192.168.7.100/ifa/ifaList/reportCommonTorch.xlsx"
                ,"//192.168.7.100/ifa/ifaList/reportCommonVich.xlsx"
                ,"//192.168.7.100/ifa/ifaList/reportCommonBc.xlsx"
                ,"//192.168.7.100/ifa/ifaList/reportCommonIfaGorm.xlsx"
                ,"//192.168.7.100/ifa/ifaList/reportCommonCoagulogramma.xlsx"
                ,"//192.168.7.100/ifa/ifaList/reportCommonErAg.xlsx"
                ,"//192.168.7.100/ifa/ifaList/reportCommonCytolog.xlsx"
                ,"//192.168.7.100/ifa/ifaList/reportCommonKario.xlsx"
        );


        dbf = DocumentBuilderFactory.newInstance();
        try {
            db  = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
//        docHiv = db.newDocument();
//        docTorch = db.newDocument();
//        docGorm = db.newDocument();
        for (int i = 0; i < commonList.size(); i++) {

            XLConstructor.writeDocument(uptakeService.getDoc(commonAnalyses, commonList.get(i), db.newDocument()));
            XLConstructor.xml2XLSX(paths.get(i));
        }

//        documents.add(uptakeService.getDoc(commonAnalyses, indets, docGorm));
//        for (Document d:documents) {
//            XLConstructor.writeDocument(d);
//            XLConstructor.xml2XLSX("//192.168.7.100/ifa/ifaList/reportCommon.xlsx");
//        }


        return documents;
    }

    @GetMapping("/period")
    public List<PeriodData> getPeriods() {

        int from20;
        int to20;
        int from42;
        int to42;
        int from43;
        int to43;
        DayOfWeek day = DayOfWeek.from(LocalDate.now());
        int value = day.getValue();
        switch (value) {
            case 1 : from20=3; to20=1; from42=0; to42=0; from43=3; to43=1;
                break;
            case 2 : from20=0; to20=0; from42=2; to42=1; from43=3;  to43=1;
                break;
            case 3 : from20=0; to20=0; from42=0; to42=0; from43=2;  to43=1;
                break;
            case 4 : from20=3; to20=1; from42=3; to42=1; from43=0;  to43=0;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }
        List<PeriodData> periods = new ArrayList<>(List.of(
                new PeriodData(20, from20, to20),
                new PeriodData(42, from42, to42),
                new PeriodData(43, from43, to43)

        ));
        return periods;

    }





}
